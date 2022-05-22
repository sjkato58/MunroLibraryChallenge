package com.katocoding.munrolibrarychallenge.daggerhilt

import android.content.Context
import com.katocoding.munrolibrarychallenge.data.errors.DataErrorHandler
import com.katocoding.munrolibrarychallenge.data.munrolist.MunroListExtractor
import com.katocoding.munrolibrarychallenge.data.munrolist.MunroListRepository
import com.katocoding.munrolibrarychallenge.data.munrolist.filter.MunroListFilter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesDataErrorHandler(): DataErrorHandler = DataErrorHandler()

    @Singleton
    @Provides
    fun providesMunroListFilter(): MunroListFilter = MunroListFilter()

    @Singleton
    @Provides
    fun providesMunroListExtractor(): MunroListExtractor = MunroListExtractor()

    @Singleton
    @Provides
    fun providesMunroListRepository(
        @ApplicationContext context: Context,
        munroListExtractor: MunroListExtractor
    ) = MunroListRepository(context, munroListExtractor)
}