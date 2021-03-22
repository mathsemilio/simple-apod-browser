package br.com.mathsemilio.simpleapodbrowser.domain.model

sealed class Result<out T>(
    val data: T? = null,
    val error: String? = null
) {
    class Completed<T>(data: T?) : Result<T>(data, null)
    class Failed(error: String) : Result<Nothing>(error = error)
}