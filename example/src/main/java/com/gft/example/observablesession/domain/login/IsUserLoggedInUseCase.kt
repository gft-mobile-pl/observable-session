package com.gft.example.observablesession.domain.login

import com.gft.example.observablesession.domain.loggedin.LoggedInSession
import kotlinx.coroutines.flow.map

class IsUserLoggedInUseCase internal constructor(
    private val loggedInSession: LoggedInSession
) {
    operator fun invoke() = loggedInSession.data.map { data -> data != null }
}