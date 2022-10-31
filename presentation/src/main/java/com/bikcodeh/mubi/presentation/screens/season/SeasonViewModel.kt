package com.bikcodeh.mubi.presentation.screens.season

import androidx.lifecycle.viewModelScope
import com.bikcodeh.mubi.domain.common.fold
import com.bikcodeh.mubi.domain.common.toError
import com.bikcodeh.mubi.domain.common.validateHttpErrorCode
import com.bikcodeh.mubi.domain.di.IoDispatcher
import com.bikcodeh.mubi.domain.repository.TvRepository
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
class SeasonViewModel @Inject constructor(
    private val tvRepository: TvRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseViewModel() {

    private val _seasonUIState = MutableStateFlow(SeasonState())
    val seasonUIState: StateFlow<SeasonState>
        get() = _seasonUIState.asStateFlow()

    fun getSeasonDetail(tvShowId: String, seasonNumber: Int) {
        _seasonUIState.update { it.copy(isLoading = true, season = null, error = null) }
        viewModelScope.launch(dispatcher) {
            tvRepository.getDetailSeason(tvShowId, seasonNumber)
                .fold(
                    onSuccess = { season ->
                        _seasonUIState.update {
                            it.copy(
                                isLoading = false,
                                season = season,
                                error = null
                            )
                        }
                    },
                    onError = { code, _ ->
                        val error = handleError(code.validateHttpErrorCode())
                        _seasonUIState.update {
                            it.copy(
                                isLoading = false,
                                season = null,
                                error = error
                            )
                        }
                    },
                    onException = { exception ->
                        val error = handleError(exception.toError())
                        _seasonUIState.update {
                            it.copy(
                                isLoading = false,
                                season = null,
                                error = error
                            )
                        }
                    }
                )
        }
    }
}