package com.gft.example.observablesession.domain.login

import com.gft.example.observablesession.domain.loggedin.LoggedInSession
import com.gft.example.observablesession.domain.loggedin.LoggedInSessionData
import kotlinx.coroutines.delay
import java.util.UUID

class LoginUseCase internal constructor(
    private val loginSession: LoginSession,
    private val loggedInSession: LoggedInSession
) {
    suspend operator fun invoke() {
        val sessionData = loginSession.requireData()
        delay(2000)
        if (sessionData.username == "username" && sessionData.password == "password") {
            loginSession.end()
            loggedInSession.start(
                LoggedInSessionData(
                    accessToken = UUID.randomUUID().toString()
                )
            )
        } else {
            throw IllegalArgumentException("Invalid username or password!")
        }
    }
}