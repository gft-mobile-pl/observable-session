package com.gft.example.observablesession.di

import com.gft.example.observablesession.domain.loggedin.GetAccessTokenUseCase
import com.gft.example.observablesession.domain.loggedin.LoggedInSession
import com.gft.example.observablesession.domain.loggedin.LogoutUseCase
import com.gft.example.observablesession.domain.login.AbortLoginProcessUseCase
import com.gft.example.observablesession.domain.login.LoginSession
import com.gft.example.observablesession.domain.login.BeginLoginProcessUseCase
import com.gft.example.observablesession.domain.login.GetCollectedPasswordUseCase
import com.gft.example.observablesession.domain.login.GetCollectedUsernameUseCase
import com.gft.example.observablesession.domain.login.IsCollectedPasswordFormatValidUseCase
import com.gft.example.observablesession.domain.login.IsCollectedUsernameFormatValidUseCase
import com.gft.example.observablesession.domain.login.IsUserLoggedInUseCase
import com.gft.example.observablesession.domain.login.LoginUseCase
import com.gft.example.observablesession.domain.login.UpdateCollectedPasswordUseCase
import com.gft.example.observablesession.domain.login.UpdateCollectedUsernameUseCase
import com.gft.example.observablesession.ui.enterpassword.EnterPasswordViewModel
import com.gft.example.observablesession.ui.enterusername.EnterUsernameViewModel
import com.gft.example.observablesession.ui.loggedin.LoggedInViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val domainModule = module {
    singleOf(::LoginSession)
    singleOf(::LoggedInSession)
    factoryOf(::BeginLoginProcessUseCase)
    factoryOf(::AbortLoginProcessUseCase)
    factoryOf(::GetCollectedUsernameUseCase)
    factoryOf(::GetCollectedPasswordUseCase)
    factoryOf(::IsCollectedUsernameFormatValidUseCase)
    factoryOf(::IsCollectedPasswordFormatValidUseCase)
    factoryOf(::UpdateCollectedUsernameUseCase)
    factoryOf(::UpdateCollectedPasswordUseCase)
    factoryOf(::LoginUseCase)
    factoryOf(::GetAccessTokenUseCase)
    factoryOf(::IsUserLoggedInUseCase)
    factoryOf(::LogoutUseCase)
}

val uiModule = module {
    viewModelOf(::EnterUsernameViewModel)
    viewModelOf(::EnterPasswordViewModel)
    viewModelOf(::LoggedInViewModel)
}