package com.gft.example.observablesession.ui.loggedin

import android.os.Bundle
import android.view.View
import com.gft.example.observablesession.R
import com.gft.example.observablesession.databinding.FragmentLoggedInBinding
import com.gft.example.observablesession.ui.base.BaseFragment
import com.gft.example.observablesession.ui.loggedin.LoggedInNavigationEffect.NavigateOut
import com.gft.example.observablesession.ui.loggedin.LoggedInViewEvent.OnNavigateBackClicked
import com.gft.example.observablesession.ui.uitls.handleBackButton
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoggedInFragment : BaseFragment<LoggedInViewState, LoggedInViewEvent, Unit, LoggedInNavigationEffect>(R.layout.fragment_logged_in) {
    override val viewModel: LoggedInViewModel by viewModel()
    private lateinit var binding: FragmentLoggedInBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoggedInBinding.bind(view)
        handleBackButton {
            viewModel.onEvent(OnNavigateBackClicked)
        }
    }

    override fun onNavigationEffect(navigationEffect: LoggedInNavigationEffect) {
        when (navigationEffect) {
            NavigateOut -> requireActivity().finish()
        }
    }

    override fun onViewEffect(viewEffect: Unit) {}

    override fun onViewState(viewState: LoggedInViewState) {
        binding.accessToken.text = "You are logged in!\n${viewState.accessToken}"
    }
}