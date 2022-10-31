package com.bikcodeh.mubi.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bikcodeh.mubi.domain.di.IoDispatcher
import com.bikcodeh.mubi.domain.model.TVShow
import com.bikcodeh.mubi.domain.repository.TvRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val tvRepository: TvRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _favoritesTvShows = MutableStateFlow<List<TVShow>>(emptyList())
    val favoritesTvShows: StateFlow<List<TVShow>>
        get() = _favoritesTvShows.asStateFlow()

    fun getFavorites() {
        viewModelScope.launch(dispatcher) {
            _favoritesTvShows.value = tvRepository.getFavoritesTvShows()
        }
    }
}