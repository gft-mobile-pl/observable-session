package com.gft.example.observablesession.domain.login

import com.gft.example.observablesession.domain.login.PasswordValidationResult.NOT_AVAILABLE
import com.gft.example.observablesession.domain.login.PasswordValidationResult.TOO_SHORT
import com.gft.example.observablesession.domain.login.PasswordValidationResult.VALID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class IsCollectedPasswordFormatValidUseCase internal constructor(
    private val loginSession: LoginSession
) {
    operator fun invoke(): Flow<PasswordValidationResult> = loginSession
        .data
        .map { sessionData ->
            sessionData?.password?.let { password ->
                when {
                    password.isEmpty() -> NOT_AVAILABLE
                    password.length >= 8 -> VALID
                    else -> TOO_SHORT
                }

            } ?: NOT_AVAILABLE
        }
}

enum class PasswordValidationResult {
    NOT_AVAILABLE,
    TOO_SHORT,
    VALID
}