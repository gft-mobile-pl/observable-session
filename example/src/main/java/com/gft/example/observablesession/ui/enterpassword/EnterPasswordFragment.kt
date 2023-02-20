package com.gft.example.observablesession.ui.enterpassword

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.gft.example.observablesession.R
import com.gft.example.observablesession.databinding.FragmentEnterPasswordBinding
import com.gft.example.observablesession.ui.base.BaseFragment
import com.gft.example.observablesession.ui.enterpassword.EnterPasswordFragmentDirections.Companion.toLoggedIn
import com.gft.example.observablesession.ui.enterpassword.EnterPasswordNavigationEffect.NavigateBack
import com.gft.example.observablesession.ui.enterpassword.EnterPasswordNavigationEffect.NavigateToLoggedInScreen
import com.gft.example.observablesession.ui.enterpassword.EnterPasswordViewEffect.FillWithSensitiveData
import com.gft.example.observablesession.ui.enterpassword.EnterPasswordViewEffect.ShowInvalidCredentialsToast
import com.gft.example.observablesession.ui.enterpassword.EnterPasswordViewEvent.OnBackButtonClicked
import com.gft.example.observablesession.ui.enterpassword.EnterPasswordViewEvent.OnLoginButtonClicked
import com.gft.example.observablesession.ui.enterpassword.EnterPasswordViewEvent.OnPasswordEntered
import com.gft.example.observablesession.ui.enterpassword.EnterPasswordViewEvent.OnScreenLoaded
import com.gft.example.observablesession.ui.uitls.handleBackButton
import com.gft.example.observablesession.ui.uitls.hideKeyboardFrom
import org.koin.androidx.viewmodel.ext.android.viewModel

class EnterPasswordFragment :
    BaseFragment<EnterPasswordViewState, EnterPasswordViewEvent, EnterPasswordViewEffect, EnterPasswordNavigationEffect>(R.layout.fragment_enter_password) {
    override val viewModel: EnterPasswordViewModel by viewModel()
    private lateinit var binding: FragmentEnterPasswordBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentEnterPasswordBinding.bind(view)
        with(binding) {
            passwordInput.doOnTextChanged { text, _, _, _ -> viewUpdate.whenNotUpdating { viewModel.onEvent(OnPasswordEntered(text.toString())) } }
            actionButton.setOnClickListener {
                hideKeyboardFrom(view)
                viewModel.onEvent(OnLoginButtonClicked)
            }
            handleBackButton { viewModel.onEvent(OnBackButtonClicked) }
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.onEvent(OnScreenLoaded)
    }

    override fun onNavigationEffect(navigationEffect: EnterPasswordNavigationEffect) {
        when (navigationEffect) {
            NavigateBack -> findNavController().popBackStack()
            NavigateToLoggedInScreen -> findNavController().navigate(toLoggedIn())
        }
    }

    override fun onViewEffect(viewEffect: EnterPasswordViewEffect) = viewUpdate.update {
        when (viewEffect) {
            is FillWithSensitiveData -> {
                binding.passwordInput.setText(viewEffect.password)
            }
            ShowInvalidCredentialsToast -> {
                Toast.makeText(requireContext(), "Invalid username or password!", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onViewState(viewState: EnterPasswordViewState) = viewUpdate.update {
        binding.actionButton.isEnabled = viewState.loginButtonEnabled
        binding.loadingIndicator.isVisible = viewState.isLoadingIndicatorVisible
        binding.passwordInput.error = viewState.inputErrorMessage

    }
}