package com.specialschool.schoolapp.domain.schooldata

import android.util.Log
import com.specialschool.schoolapp.data.SchoolRepository
import com.specialschool.schoolapp.di.IoDispatcher
import com.specialschool.schoolapp.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/**
 * 네트워크를 통해 학교 데이터를 새로 불러와서 캐시 데이터를 업데이트하는 use case
 * 
 * TODO: Unused use case
 */
class RefreshSchoolDataUseCase @Inject constructor(
    private val repository: SchoolRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Any, Boolean>(dispatcher) {

    override fun execute(parameters: Any): Boolean {
        try {
            repository.refreshCacheWithRemoteSchoolData()
        } catch (e: Exception) {
            Log.e("", "School data load failed", e)
            throw e
        }
        return true
    }
}
