package com.specialschool.schoolapp.domain

import com.specialschool.schoolapp.util.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

/**
 * [Flow]를 사용하는 use case 추상 클래스
 *
 * @see [UseCase]
 */
abstract class FlowUseCase<in P, R>(private val dispatcher: CoroutineDispatcher) {

    /**
     * Use case를 비동기로 실행하고 [Flow]로 [Result]를 반환한다.
     */
    operator fun invoke(parameters: P): Flow<Result<R>> = execute(parameters)
        .catch { e -> emit(Result.Error(Exception(e))) }
        .flowOn(dispatcher)

    protected abstract fun execute(parameters: P): Flow<Result<R>>
}
