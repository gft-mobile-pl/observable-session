package com.gft.example.observablesession.domain.login

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCollectedUsernameUseCase internal constructor(
    private val loginSession: LoginSession
) {
    operator fun invoke(): Flow<String> = loginSession.data.map { data -> data!!.username }
}