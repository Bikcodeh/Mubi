package com.bikcodeh.mubi.presentation.screens.login

import com.bikcodeh.mubi.core_test.util.CoroutineRule
import com.bikcodeh.mubi.domain.repository.LoginDataStoreOperations
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import io.mockk.just
import io.mockk.runs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @get:Rule
    val coroutineRule = CoroutineRule()

    @get:Rule
    val mockkRule = MockKRule(this)

    @RelaxedMockK
    private lateinit var loginDataStoreOperations: LoginDataStoreOperations

    private lateinit var loginViewModel: LoginViewModel

    @Before
    fun setUp() {
        loginViewModel = LoginViewModel(
            loginDataStoreOperations,
            UnconfinedTestDispatcher()
        )
    }

    @Test
    fun isLoggedIn() {
        assertThat(loginViewModel.isLoggedIn.value).isFalse()
    }

    @Test
    fun saveLogin() {
        /** Given */
        coEvery { loginDataStoreOperations.saveLogin(true) } just runs

        /** When */
        loginViewModel.saveLogin(true)

        /** Then */
        coVerify { loginDataStoreOperations.saveLogin(true) }
    }

    @Test
    fun getIsLoggedIn() {
        /** Given */
        coEvery { loginDataStoreOperations.getIsLogged() } returns flowOf(true)

        /** When */
        loginViewModel.getIsLoggedIn()

        /** Then */
        assertThat(loginViewModel.isLoggedIn.value).isTrue()
        coVerify { loginDataStoreOperations.getIsLogged() }
    }
}