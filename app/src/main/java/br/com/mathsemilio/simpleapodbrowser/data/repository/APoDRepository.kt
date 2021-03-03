package br.com.mathsemilio.simpleapodbrowser.data.repository

import br.com.mathsemilio.simpleapodbrowser.networking.APoDApi

class APoDRepository(private val aPoDApi: APoDApi) {
    suspend fun getAPoD() = aPoDApi.getAPoD()
}