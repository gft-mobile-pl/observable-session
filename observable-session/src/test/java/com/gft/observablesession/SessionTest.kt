package com.gft.observablesession

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

private const val VALUE_1 = "value1"
private const val VALUE_2 = "value2"

class SessionTest {

    private lateinit var session: Session<String>

    @Before
    fun setup() {
        session = Session()
    }

    @Test(expected = Session.SessionNotStartedException::class)
    fun `given session is stopped throw an exception when trying to end it`() {
        session.end()
    }

    @Test(expected = Session.SessionStartedAlreadyException::class)
    fun `given session is started throw an exception when trying to start it again`() {
        session.start(VALUE_1)

        session.start(VALUE_2)
    }

    @Test
    fun `given session is stopped return null as data`() {
        assertNull(session.data.value)
    }

    @Test
    fun `given session is stopped return false as isStarted`() {
        assertFalse(session.isStarted)
    }

    @Test
    fun `given session is started and not updated return data passed during start as data`() {
        session.start(VALUE_1)

        assertEquals(VALUE_1, session.data.value)
    }

    @Test
    fun `given session is started return true as isStarted`() {
        session.start(VALUE_1)

        assertTrue(session.isStarted)
    }

    @Test
    fun `given session is started when session is updated pass current data to transformed`() {
        session.start(VALUE_1)

        session.update { currentData ->
            assertEquals(VALUE_1, currentData)
            VALUE_2
        }
    }

    @Test
    fun `given session is started when session is updated return updated value as data`() {
        session.start(VALUE_1)

        session.update { VALUE_2 }

        assertEquals(VALUE_2, session.data.value)
    }

    @Test
    fun `given session is started and the ended return null as data`() {
        session.start(VALUE_1)

        session.end()

        assertNull(session.data.value)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `given session is started when session is ended dispatch null as data`() = runBlockingTest {
        val collectedValues = mutableListOf<String?>()
        session.start(VALUE_1)

        val job = launch(Dispatchers.Unconfined, start = CoroutineStart.UNDISPATCHED) {
            session.data.collect { value -> collectedValues.add(value) }
        }
        session.end()

        assertEquals(mutableListOf(VALUE_1, null), collectedValues)

        job.cancelAndJoin()
    }
}