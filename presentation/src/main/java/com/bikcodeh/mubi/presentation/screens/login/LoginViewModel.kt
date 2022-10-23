package com.bikcodeh.mubi.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bikcodeh.mubi.domain.di.IoDispatcher
import com.bikcodeh.mubi.domain.repository.LoginDataStoreOperations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginDataStoreOperations: LoginDataStoreOperations,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _isLoggedIn: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean>
        get() = _isLoggedIn.asStateFlow()

    fun saveLogin(isLoggedIn: Boolean) {
        viewModelScope.launch(dispatcher) {
            loginDataStoreOperations.saveLogin(isLoggedIn)
        }
    }

    fun getIsLoggedIn() {
        viewModelScope.launch(dispatcher) {
            loginDataStoreOperations.getIsLogged().collect {
                _isLoggedIn.value = it
            }
        }
    }
}