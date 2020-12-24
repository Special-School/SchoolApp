package com.specialschool.schoolapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application 시작 지점, Dagger-Hilt 사용 선언
 */
@HiltAndroidApp
class MainApplication : Application()
