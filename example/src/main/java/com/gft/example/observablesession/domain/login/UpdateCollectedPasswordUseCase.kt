package com.gft.example.observablesession.domain.login

class UpdateCollectedPasswordUseCase internal constructor(
    private val loginSession: LoginSession
) {
    operator fun invoke(password: String) {
        loginSession.update { sessionData ->
            sessionData.copy(password = password)
        }
    }
}