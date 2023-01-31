package com.example.baseapp.data.di

import android.content.Context
import androidx.room.Room
import com.example.baseapp.data.local.model.MovieDb
import com.example.baseapp.data.remote.MovieDataSourceImpl
import com.example.baseapp.data.repository.MovieRepositoryImpl
import com.example.baseapp.domain.MovieDataSource
import com.example.baseapp.domain.MovieRepository
import com.example.baseapp.domain.usecase.FavoriteUseCase
import com.example.baseapp.domain.usecase.FavoriteUseCaseImpl
import com.example.baseapp.domain.usecase.MovieUseCase
import com.example.baseapp.domain.usecase.MoviesUseCaseImpl
import com.example.baseapp.domain.usecase.SearchUseCase
import com.example.baseapp.domain.usecase.SearchUseCaseImpl
import com.example.baseapp.domain.usecase.UpdateMovieUseCase
import com.example.baseapp.domain.usecase.UpdateMovieUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideMovieDataSource(): MovieDataSource = MovieDataSourceImpl()

    @Provides
    @Singleton
    fun provideDispatchers(): DispatchersProvider = Dispatchers()

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext appContext: Context): MovieDb = Room
        .databaseBuilder(appContext, MovieDb::class.java, MovieDb.DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideMovieRepository(
        db: MovieDb,
        movieDataSource: MovieDataSource,
    ): MovieRepository = MovieRepositoryImpl(
        db = db.moviesDao(),
        movieDataSource = movieDataSource
    )

    @Provides
    fun provideMovieUseCase(
        movieRepositoryImpl: MovieRepository,
        dispatchers: DispatchersProvider
    ): MovieUseCase = MoviesUseCaseImpl(
        movieRepositoryImpl,
        dispatchers
    )

    @Provides
    fun provideFavoriteUseCase(
        movieRepositoryImpl: MovieRepository,
        dispatchers: DispatchersProvider
    ): FavoriteUseCase = FavoriteUseCaseImpl(
        movieRepositoryImpl,
        dispatchers
    )

    @Provides
    fun provideSearchUseCase(
        movieRepositoryImpl: MovieRepository,
        dispatchers: DispatchersProvider
    ): SearchUseCase = SearchUseCaseImpl(
        movieRepositoryImpl,
        dispatchers
    )

    @Provides
    fun provideUpdateFavoriteMovie(
        movieRepositoryImpl: MovieRepository,
    ): UpdateMovieUseCase = UpdateMovieUseCaseImpl(
        movieRepositoryImpl,
    )
}

interface DispatchersProvider {
    val main: CoroutineDispatcher
    val default: CoroutineDispatcher
    val io: CoroutineDispatcher
}

class Dispatchers : DispatchersProvider {
    override val main: CoroutineDispatcher
        get() = Dispatchers.Main
    override val default: CoroutineDispatcher
        get() = Dispatchers.Default
    override val io: CoroutineDispatcher
        get() = Dispatchers.IO
}