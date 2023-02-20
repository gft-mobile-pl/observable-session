package com.gft.example.observablesession.domain.login

import com.gft.example.observablesession.domain.login.UsernameValidationResult.NOT_AVAILABLE
import com.gft.example.observablesession.domain.login.UsernameValidationResult.TOO_LONG
import com.gft.example.observablesession.domain.login.UsernameValidationResult.TOO_SHORT
import com.gft.example.observablesession.domain.login.UsernameValidationResult.VALID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class IsCollectedUsernameFormatValidUseCase internal constructor(
    private val loginSession: LoginSession
) {
    operator fun invoke(): Flow<UsernameValidationResult> = loginSession
        .data
        .map { sessionData ->
            sessionData?.username?.let { username ->
                val length = username.length
                when {
                    length == 0 -> NOT_AVAILABLE
                    length < 6 -> TOO_SHORT
                    length > 12 -> TOO_LONG
                    else -> VALID
                }
            } ?: NOT_AVAILABLE
        }
}

enum class UsernameValidationResult {
    NOT_AVAILABLE,
    TOO_SHORT,
    TOO_LONG,
    VALID
}