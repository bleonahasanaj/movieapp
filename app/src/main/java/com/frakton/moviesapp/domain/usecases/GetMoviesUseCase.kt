package com.frakton.moviesapp.domain.usecases

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.frakton.moviesapp.data.retrofit.models.response.MovieDataModel
import com.frakton.moviesapp.domain.MovieParams
import com.frakton.moviesapp.domain.UseCases
import com.frakton.moviesapp.domain.base.io
import com.frakton.moviesapp.domain.repositories.MoviesRepository
import javax.inject.Inject


class GetMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) : UseCases.GetMovies {
    override suspend fun invoke(input: MovieParams.GetMoviesParams): LiveData<PagingData<MovieDataModel>> {
        return io {
            moviesRepository.getMoviesFromApi()
        }
    }
}