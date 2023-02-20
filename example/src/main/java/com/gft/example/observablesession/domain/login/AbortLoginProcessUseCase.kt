package com.gft.example.observablesession.domain.login

class AbortLoginProcessUseCase internal constructor(
    private val loginSession: LoginSession
) {
    operator fun invoke() {
        if (loginSession.isStarted) {
            loginSession.end()
        }
    }
}