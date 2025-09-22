package fr.eseo.ld.ts.movieposters.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import fr.eseo.ld.ts.movieposters.models.Movie
import fr.eseo.ld.ts.movieposters.repositories.OmdbRepository
import kotlinx.coroutines.launch

class MovieViewModel(val omdbRepository: OmdbRepository) : ViewModel(){
    private val _movie: MutableState<Movie?> = mutableStateOf(null)
    val movie: State<Movie?> = _movie

    fun fetchMovie(title : String){
        viewModelScope.launch{
            try{
                val omdbMovie = omdbRepository.fetchMovie(title, "1dd589a1")
                _movie.value = omdbMovie
            }
            catch(e : Exception) {
                Log.d("Film","Error fetching movie",e)
                _movie.value = null
            }
        }
    }


}

class MovieViewModelFactory(
    private val repository : OmdbRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MovieViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return MovieViewModel(repository) as T
        }
        throw IllegalArgumentException("Incorrect ViewModel")
    }
}