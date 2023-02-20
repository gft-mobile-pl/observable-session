package com.gft.example.observablesession.ui.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gft.example.observablesession.ui.uitls.ViewUpdate

abstract class BaseFragment<ViewState : Any, ViewEvent : Any, ViewEffect : Any, NavigationEffect : Any> (@LayoutRes contentLayoutId: Int)
: Fragment(contentLayoutId) {
    protected abstract val viewModel: BaseViewModel<ViewState, ViewEvent, ViewEffect, NavigationEffect>
    protected val viewUpdate = ViewUpdate()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.viewEffects.observe(viewLifecycleOwner) { event -> event?.consume(::onViewEffect) }
        viewModel.navigationEffects.observe(viewLifecycleOwner) { event -> event?.consume(::onNavigationEffect) }
        viewModel.viewStates.observe(viewLifecycleOwner) { state -> state?.apply(::onViewState) }
    }

    abstract fun onViewState(viewState: ViewState)
    abstract fun onViewEffect(viewEffect: ViewEffect)
    abstract fun onNavigationEffect(navigationEffect: NavigationEffect)
}