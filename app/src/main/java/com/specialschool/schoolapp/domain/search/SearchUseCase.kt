package com.specialschool.schoolapp.domain.search

import com.specialschool.schoolapp.data.search.SchoolRepository
import com.specialschool.schoolapp.di.IoDispatcher
import com.specialschool.schoolapp.model.School
import com.specialschool.schoolapp.util.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val repository: SchoolRepository,
    private val textMatchStrategy: TextMatchStrategy,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    operator fun invoke(parameter: String/* query */): Flow<Result<List<School>>> = execute(parameter)
        .catch { e -> emit(Result.Error(Exception(e))) }
        .flowOn(dispatcher)

    fun execute(parameter: String/* query */): Flow<Result<List<School>>> {
        TODO("implementation")
    }
}
