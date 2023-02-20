package com.gft.example.observablesession.ui.uitls

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment

fun Fragment.handleBackButton(callback: () -> Unit) {
    requireActivity().onBackPressedDispatcher
        .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() = callback()
        })
}