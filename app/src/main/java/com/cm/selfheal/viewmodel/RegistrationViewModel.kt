package com.cm.selfheal.viewmodel

import android.util.Patterns
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegistrationViewModel : ViewModel() {
    val emailAddress = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val formValidMediator = MediatorLiveData<Boolean>()

    init {
        formValidMediator.value=false
        formValidMediator.addSource(emailAddress) { validateForm() }
        formValidMediator.addSource(password) { validateForm() }

    }

    private fun validateForm() {
        // put your validation logic here, and update the following value
        // as `true` or `false` based on validation result
        formValidMediator.value =
            isFormValid(
                emailAddress.value.toString(),
                password.value.toString()
            )
    }

    override fun onCleared() {
        // DO NOT forget to remove sources from mediator
        formValidMediator.removeSource(emailAddress)
        formValidMediator.removeSource(password)
    }
}

fun isFormValid(emailAddress: String, password: String): Boolean {
    if (isEmailValid(emailAddress) && isValidPassword(
            password
        )
    ) {
        return true
    }
    return false
}

private fun isEmailValid(emailAddress: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()
}

private fun isValidPassword(password: String): Boolean {
    password?.let {
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
        val passwordMatcher = Regex(passwordPattern)

        return passwordMatcher.find(password) != null
    } ?: return false
}
