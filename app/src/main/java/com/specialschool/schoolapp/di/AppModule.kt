package com.specialschool.schoolapp.di

import android.content.Context
import com.specialschool.schoolapp.data.db.AppDatabase
import com.specialschool.schoolapp.data.search.DefaultSchoolRepository
import com.specialschool.schoolapp.data.search.FakeSchoolDataSource
import com.specialschool.schoolapp.data.search.SchoolDataSource
import com.specialschool.schoolapp.data.search.SchoolRepository
import com.specialschool.schoolapp.domain.search.FtsTextMatchStrategy
import com.specialschool.schoolapp.domain.search.TextMatchStrategy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.buildDatabase(context)
    }

    @Singleton
    @Provides
    fun provideTextMatchStrategy(appDatabase: AppDatabase): TextMatchStrategy {
        return FtsTextMatchStrategy(appDatabase)
    }

    @Singleton
    @Provides
    fun provideSchoolRepository(dataSource: SchoolDataSource): SchoolRepository {
        return DefaultSchoolRepository(dataSource)
    }

    @Singleton
    @Provides
    fun provideSchoolDataSource(): SchoolDataSource {
        return FakeSchoolDataSource
    }
}
