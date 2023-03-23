package com.frakton.moviesapp.domain.repositories

import com.frakton.moviesapp.db.dao.GenresDao
import com.frakton.moviesapp.db.tables.Genres
import com.frakton.moviesapp.domain.interactors.GetGenresInteractor
import com.frakton.moviesapp.domain.mappers.GenresDBModelMapper
import com.frakton.moviesapp.domain.mappers.GenresMapper
import com.frakton.moviesapp.domain.models.GenresModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GenresRepository @Inject constructor(
    private val getGenresInteractor: GetGenresInteractor,
    private val genresMapper: GenresMapper,
    private val genresDBModelMapper: GenresDBModelMapper,
    private val genresDao: GenresDao?
) {
    fun getGenres(): Flow<List<GenresModel>> = flow {
        emit(genresMapper.map(getGenresInteractor.invoke(null)))
    }

    suspend fun getGenresFromDB(): Flow<Genres?> = flow {
        emit(genresDao?.getGenres())
    }

    suspend fun updateGenres(newGenresData: List<GenresModel>) {
        getGenresFromDB().collect{ oldGenresData ->
            if(oldGenresData != genresDBModelMapper.map(newGenresData)) {
                genresDao?.deleteAll()
                genresDao?.insertGenres(genresDBModelMapper.map(newGenresData))
            }
        }
    }
}