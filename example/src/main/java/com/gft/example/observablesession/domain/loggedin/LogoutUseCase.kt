package com.gft.example.observablesession.domain.loggedin

class LogoutUseCase internal constructor(
    private val loggedInSession: LoggedInSession
) {
    operator fun invoke() {
        if (loggedInSession.isStarted) loggedInSession.end()
    }

}