package com.katocoding.munrolibrarychallenge.daggerhilt

import android.content.Context
import com.katocoding.munrolibrarychallenge.data.errors.DataErrorHandler
import com.katocoding.munrolibrarychallenge.data.munrolist.MunroListExtractor
import com.katocoding.munrolibrarychallenge.data.munrolist.MunroListRepository
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
    fun providesDataErrorHandler(
        @ApplicationContext context: Context
    ): DataErrorHandler = DataErrorHandler(context)

    @Singleton
    @Provides
    fun providesMunroListExtractor(): MunroListExtractor = MunroListExtractor()

    @Singleton
    @Provides
    fun providesMunroListRepository(
        @ApplicationContext context: Context,
        munroListExtractor: MunroListExtractor,
        dataErrorHandler: DataErrorHandler
    ) = MunroListRepository(context, munroListExtractor, dataErrorHandler)
}