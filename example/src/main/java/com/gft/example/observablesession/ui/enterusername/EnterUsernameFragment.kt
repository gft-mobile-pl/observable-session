package com.gft.example.observablesession.ui.enterusername

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.gft.example.observablesession.R
import com.gft.example.observablesession.databinding.FragmentEnterUsernameBinding
import com.gft.example.observablesession.ui.base.BaseFragment
import com.gft.example.observablesession.ui.enterusername.EnterUsernameFragmentDirections.Companion.toPasswordScreen
import com.gft.example.observablesession.ui.enterusername.EnterUsernameNavigationEffect.NavigateBack
import com.gft.example.observablesession.ui.enterusername.EnterUsernameNavigationEffect.NavigateToEnterPassword
import com.gft.example.observablesession.ui.enterusername.EnterUsernameViewEffect.FillWithSensitiveData
import com.gft.example.observablesession.ui.enterusername.EnterUsernameViewEvent.OnBackButtonClicked
import com.gft.example.observablesession.ui.enterusername.EnterUsernameViewEvent.OnNextButtonClicked
import com.gft.example.observablesession.ui.enterusername.EnterUsernameViewEvent.OnScreenLoaded
import com.gft.example.observablesession.ui.enterusername.EnterUsernameViewEvent.OnUsernameEntered
import com.gft.example.observablesession.ui.uitls.handleBackButton
import org.koin.androidx.viewmodel.ext.android.viewModel

class EnterUsernameFragment :
    BaseFragment<EnterUsernameViewState, EnterUsernameViewEvent, EnterUsernameViewEffect, EnterUsernameNavigationEffect>(R.layout.fragment_enter_username) {
    override val viewModel: EnterUsernameViewModel by viewModel()
    private lateinit var binding: FragmentEnterUsernameBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentEnterUsernameBinding.bind(view)
        with(binding) {
            usernameInput.doOnTextChanged { text, _, _, _ -> viewUpdate.whenNotUpdating { viewModel.onEvent(OnUsernameEntered(text.toString())) } }
            actionButton.setOnClickListener { viewModel.onEvent(OnNextButtonClicked) }
            handleBackButton { viewModel.onEvent(OnBackButtonClicked) }
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.onEvent(OnScreenLoaded)
    }

    override fun onNavigationEffect(navigationEffect: EnterUsernameNavigationEffect) {
        when (navigationEffect) {
            NavigateBack -> requireActivity().finish()
            NavigateToEnterPassword -> findNavController().navigate(toPasswordScreen())
        }
    }

    override fun onViewEffect(viewEffect: EnterUsernameViewEffect) = viewUpdate.update {
        when (viewEffect) {
            is FillWithSensitiveData -> {
                binding.usernameInput.setText(viewEffect.username)
            }
        }
    }

    override fun onViewState(viewState: EnterUsernameViewState) = viewUpdate.update {
        binding.usernameInput.error = viewState.inputErrorMessage
        binding.actionButton.isEnabled = viewState.nextButtonEnabled
    }
}