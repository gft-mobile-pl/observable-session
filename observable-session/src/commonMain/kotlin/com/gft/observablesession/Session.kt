package com.gft.observablesession

import com.gft.concurrency.Lock
import com.gft.concurrency.withLock
import com.gft.coroutines.flow.StrictEqualityMutableStateFlow
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow

/**
 * Represents a concept of a session:
 * - session can be started and ended multiple times,
 * - started session always holds some data,
 * - held data can be atomically updated,
 * - data updates are only possible when session is started,
 * - whenever held data is updated the new data is emitted by [data] flow,
 * - whenever a new collector is subscribed it will receive the current data held in the session,
 * - any held data is disposed when session ends.
 *
 * As a rule of thumb session scope should be as limited as possible: one should start
 * a session when it is really required and stop it once that held data is obsolete.
 *
 * Be aware that [data] flow is conflated. If it is important that no item is missed the flow should
 * be subscribed using [Dispatchers.Unconfined] dispatcher. It is also a good idea to start
 * the subscription using [CoroutineStart.UNDISPATCHED] start method - otherwise the subscription
 * may not be synchronous.
 */
open class Session<DATA> {
    private var _isStarted: Boolean = false
    private var _data = StrictEqualityMutableStateFlow<DATA?>(null)
    val data: StateFlow<DATA?> = _data
    private val lock = Lock()

    fun start(data: DATA) = lock.withLock {
        if (_isStarted) throw SessionStartedAlreadyException(this)
        _isStarted = true;
        _data.value = data
    }

    fun end() = lock.withLock {
        if (_isStarted.not()) throw SessionNotStartedException(this)
        _isStarted = false
        _data.value = null
    }

    fun update(transformer: (currentData: DATA) -> DATA): DATA = lock.withLock {
        if (_isStarted.not()) throw SessionNotStartedException(this)
        val currentData = _data.value
        _data.value = transformer(currentData!!)
        _data.value!!
    }

    val isStarted: Boolean
        get() = lock.withLock { _isStarted }

    fun requireData(): DATA = lock.withLock {
        if (_isStarted.not()) throw SessionNotStartedException(this)
        data.value!!
    }

    class SessionNotStartedException(session: Session<*>) : RuntimeException("Session $session is not started")
    class SessionStartedAlreadyException(session: Session<*>) : RuntimeException("Session $session is started already")
}
