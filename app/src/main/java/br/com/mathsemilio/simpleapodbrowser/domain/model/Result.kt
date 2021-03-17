package br.com.mathsemilio.simpleapodbrowser.domain.model

sealed class Result<out T>(
    val data: T? = null,
    val errorMessage: String? = null
) {
    class Completed<T>(data: T?) : Result<T>(data, null)
    class Failed(errorMessage: String) : Result<Nothing>(errorMessage = errorMessage)
}