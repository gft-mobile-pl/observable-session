package com.gft.example.observablesession.domain.loggedin

class GetAccessTokenUseCase internal constructor(
    private val loggedInSession: LoggedInSession
) {
    operator fun invoke(): String = loggedInSession.requireData().accessToken
}