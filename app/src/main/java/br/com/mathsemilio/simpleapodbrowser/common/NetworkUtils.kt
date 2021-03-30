package br.com.mathsemilio.simpleapodbrowser.common

import br.com.mathsemilio.simpleapodbrowser.domain.model.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> performAPICall(dispatcher: CoroutineDispatcher, block: suspend () -> T): Result<T> {
    return withContext(dispatcher) {
        try {
            Result.Completed(data = block.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> Result.Failed(error = throwable.message)
                is HttpException -> {
                    Result.Failed(
                        error = formatHTTPErrorBody(
                            throwable.code(),
                            throwable.response()?.errorBody().toString()
                        )
                    )
                }
                else -> Result.Failed(error = throwable.message)
            }
        }
    }
}

fun formatHTTPErrorBody(code: Int, errorBody: String): String {
    return "HTTP error $code:\n $errorBody."
}