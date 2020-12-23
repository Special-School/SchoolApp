package com.specialschool.schoolapp.domain

import com.specialschool.schoolapp.util.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.lang.RuntimeException
import kotlin.jvm.Throws

/**
 * Domain layer를 표현한 추상 클래스, 비즈니스 로직을 코루틴을 이용해 실행한다.
 */
abstract class UseCase<in P, R>(private val dispatcher: CoroutineDispatcher) {

    /**
     * Use case를 비동기로 실행하고 [Result]를 반환한다.
     */
    suspend operator fun invoke(parameters: P): Result<R> {
        return try {
            withContext(dispatcher) {
                execute(parameters).let { Result.Success(it) }
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    /**
     * 이 메서드를 재정의해서 실행할 코드를 작성한다.
     */
    @Throws(RuntimeException::class)
    protected abstract fun execute(parameters: P): R
}
