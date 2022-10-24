package com.bikcodeh.mubi.presentation.screens.detail

import androidx.lifecycle.viewModelScope
import com.bikcodeh.mubi.domain.common.fold
import com.bikcodeh.mubi.domain.common.toError
import com.bikcodeh.mubi.domain.common.validateHttpErrorCode
import com.bikcodeh.mubi.domain.di.IoDispatcher
import com.bikcodeh.mubi.domain.usecase.GetDetailTvShowUC
import com.bikcodeh.mubi.presentation.util.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getDetailTvShowUC: GetDetailTvShowUC,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseViewModel() {

    private val _detailState = MutableStateFlow(DetailState())
    val detailState: StateFlow<DetailState>
        get() = _detailState.asStateFlow()

    fun getDetailById(tvShowId: String) {
        _detailState.update { it.copy(isLoading = true, tvShow = null, error = null) }
        viewModelScope.launch(dispatcher) {
            getDetailTvShowUC(tvShowId).fold(
                onSuccess = { tvShow ->
                    _detailState.update {
                        it.copy(
                            isLoading = false,
                            tvShow = tvShow,
                            error = null
                        )
                    }
                },
                onError = { code, _ ->
                    val error = handleError(code.validateHttpErrorCode())
                    _detailState.update {
                        it.copy(
                            isLoading = false,
                            tvShow = null,
                            error = error
                        )
                    }
                },
                onException = { exception ->
                    val error = handleError(exception.toError())
                    _detailState.update {
                        it.copy(
                            isLoading = false,
                            tvShow = null,
                            error = error
                        )
                    }
                }
            )
        }
    }
}