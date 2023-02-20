package com.gft.example.observablesession.domain.login

class UpdateCollectedUsernameUseCase internal constructor(
    private val loginSession: LoginSession
) {
    operator fun invoke(username: String) {
        loginSession.update { sessionData ->
            sessionData.copy(username = username)
        }
    }
}