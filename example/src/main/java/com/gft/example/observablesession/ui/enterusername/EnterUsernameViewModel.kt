package com.gft.example.observablesession.ui.enterusername

import androidx.lifecycle.viewModelScope
import com.gft.example.observablesession.domain.login.AbortLoginProcessUseCase
import com.gft.example.observablesession.domain.login.BeginLoginProcessUseCase
import com.gft.example.observablesession.domain.login.GetCollectedUsernameUseCase
import com.gft.example.observablesession.domain.login.IsCollectedUsernameFormatValidUseCase
import com.gft.example.observablesession.domain.login.UpdateCollectedUsernameUseCase
import com.gft.example.observablesession.domain.login.UsernameValidationResult
import com.gft.example.observablesession.ui.base.BaseViewModel
import com.gft.example.observablesession.ui.enterusername.EnterUsernameNavigationEffect.NavigateBack
import com.gft.example.observablesession.ui.enterusername.EnterUsernameNavigationEffect.NavigateToEnterPassword
import com.gft.example.observablesession.ui.enterusername.EnterUsernameViewEffect.FillWithSensitiveData
import com.gft.example.observablesession.ui.enterusername.EnterUsernameViewEvent.OnBackButtonClicked
import com.gft.example.observablesession.ui.enterusername.EnterUsernameViewEvent.OnNextButtonClicked
import com.gft.example.observablesession.ui.enterusername.EnterUsernameViewEvent.OnScreenLoaded
import com.gft.example.observablesession.ui.enterusername.EnterUsernameViewEvent.OnUsernameEntered
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EnterUsernameViewModel internal constructor(
    beginLoginProcess: BeginLoginProcessUseCase,
    private val abortLoginProcess: AbortLoginProcessUseCase,
    private val updateCollectedUsername: UpdateCollectedUsernameUseCase,
    private val isCollectedUsernameFormatValid: IsCollectedUsernameFormatValidUseCase,
    private val getCollectedUsername: GetCollectedUsernameUseCase
) : BaseViewModel<EnterUsernameViewState, EnterUsernameViewEvent, EnterUsernameViewEffect, EnterUsernameNavigationEffect>() {
    init {
        beginLoginProcess()

        startViewStateUpdates()
    }

    private fun startViewStateUpdates() {
        viewState = EnterUsernameViewState(
            nextButtonEnabled = false,
            inputErrorMessage = null
        )

        viewModelScope.launch(start = CoroutineStart.UNDISPATCHED) {
            isCollectedUsernameFormatValid()
                .collect { usernameFormatValidationResult ->
                    viewState = viewState.copy(
                        nextButtonEnabled = usernameFormatValidationResult == UsernameValidationResult.VALID,
                        inputErrorMessage = when (usernameFormatValidationResult) {
                            UsernameValidationResult.NOT_AVAILABLE,
                            UsernameValidationResult.VALID -> null
                            UsernameValidationResult.TOO_SHORT -> "Username is too short"
                            UsernameValidationResult.TOO_LONG -> "Username it too long"
                        }
                    )
                }
        }
    }

    override fun onEvent(viewEvent: EnterUsernameViewEvent) {
        when (viewEvent) {
            OnBackButtonClicked -> {
                abortLoginProcess()
                dispatchNavigationEffect(NavigateBack)
            }
            OnNextButtonClicked -> {
                dispatchNavigationEffect(NavigateToEnterPassword)
            }
            OnScreenLoaded -> viewModelScope.launch(start = CoroutineStart.UNDISPATCHED) {
                dispatchViewEffect(FillWithSensitiveData(getCollectedUsername().first()))
            }
            is OnUsernameEntered -> {
                updateCollectedUsername(viewEvent.username)
            }
        }
    }
}