package com.gft.example.observablesession.domain.login

import com.gft.observablesession.Session

internal class LoginSession : Session<LoginSessionData>()

internal data class LoginSessionData(
    val username: String,
    val password: String
)