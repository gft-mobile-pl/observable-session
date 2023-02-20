package com.gft.example.observablesession.domain.loggedin

import com.gft.observablesession.Session

internal class LoggedInSession : Session<LoggedInSessionData>()

internal data class LoggedInSessionData(
    val accessToken: String
)