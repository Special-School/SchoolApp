package com.specialschool.schoolapp.di

import android.content.Context
import com.specialschool.schoolapp.MainApplication
import com.specialschool.schoolapp.data.db.AppDatabase
import com.specialschool.schoolapp.data.SchoolRepository
import com.specialschool.schoolapp.data.FakeSchoolDataSource
import com.specialschool.schoolapp.data.SchoolDataSource
import com.specialschool.schoolapp.data.remote.RemoteSchoolDataSource
import com.specialschool.schoolapp.domain.search.FtsQueryMatchStrategy
import com.specialschool.schoolapp.domain.search.QueryMatchStrategy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Named
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
    fun provideQueryMatchStrategy(appDatabase: AppDatabase): QueryMatchStrategy {
        return FtsQueryMatchStrategy(appDatabase)
    }

    @Singleton
    @Provides
    fun provideSchoolRepository(dataSource: SchoolDataSource, database: AppDatabase): SchoolRepository {
        return SchoolRepository(dataSource, database)
    }

    @Singleton
    @Provides
    @Named("remoteSchoolDataSource")
    fun provideRemoteSchoolDataSource(
        @ApplicationContext context: Context
    ): SchoolDataSource {
        return RemoteSchoolDataSource(context)
    }

    @Provides
    fun provideContext(application: MainApplication): Context {
        return application.applicationContext
    }

    @ApplicationScope
    @Singleton
    @Provides
    fun providesApplicationScope(
        @DefaultDispatcher dispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(SupervisorJob() + dispatcher)
}
