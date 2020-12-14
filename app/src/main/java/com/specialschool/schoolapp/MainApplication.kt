package com.specialschool.schoolapp

import android.app.Application
import com.specialschool.schoolapp.domain.schooldata.RefreshSchoolDataUseCase
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application() {



    override fun onCreate() {
        super.onCreate()
    }
}
