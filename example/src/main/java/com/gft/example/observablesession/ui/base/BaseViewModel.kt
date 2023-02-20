package com.gft.example.observablesession.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gft.example.observablesession.ui.uitls.ConsumableEvent

abstract class BaseViewModel<ViewState : Any, ViewEvent : Any, ViewEffect : Any, NavigationEffect : Any> : ViewModel() {
    private val _viewStates = MutableLiveData<ViewState>(null)
    val viewStates: LiveData<ViewState> = _viewStates
    var viewState: ViewState
        get() = _viewStates.value!!
        set(value) {
            _viewStates.value = value
        }

    private val _viewEffects = MutableLiveData<ConsumableEvent<ViewEffect>>(null)
    val viewEffects: LiveData<ConsumableEvent<ViewEffect>> = _viewEffects
    protected fun dispatchViewEffect(viewEffect: ViewEffect) {
        _viewEffects.value = ConsumableEvent(viewEffect)
    }

    private val _navigationEffects = MutableLiveData<ConsumableEvent<NavigationEffect>?>(null)
    val navigationEffects: LiveData<ConsumableEvent<NavigationEffect>?> = _navigationEffects
    protected fun dispatchNavigationEffect(navigationEffect: NavigationEffect) {
        _navigationEffects.value = ConsumableEvent(navigationEffect)
    }
    protected fun clearNavigationEffect() {
        _navigationEffects.value = null
    }

    abstract fun onEvent(viewEvent: ViewEvent)
}