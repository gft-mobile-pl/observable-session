package com.gft.example.observablesession.domain.login

class BeginLoginProcessUseCase internal constructor(
    private val loginSession: LoginSession
) {
    operator fun invoke() {
        if (!loginSession.isStarted) {
            loginSession.start(LoginSessionData("", ""))
        }
    }
}