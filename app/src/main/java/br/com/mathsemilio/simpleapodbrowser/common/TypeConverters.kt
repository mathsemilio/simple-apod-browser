package br.com.mathsemilio.simpleapodbrowser.common

import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoDSchema
import br.com.mathsemilio.simpleapodbrowser.domain.model.CachedAPoD

fun APoDSchema.toAPoD(): APoD {
    return APoD(
        this.title,
        this.url,
        this.date,
        this.mediaType,
        this.explanation,
        this.thumbnailUrl
    )
}

fun List<APoDSchema>.schemaToAPoDList(): List<APoD> {
    val apodList = mutableListOf<APoD>()
    this.forEach { aPoDSchema ->
        apodList.add(aPoDSchema.toAPoD())
    }
    return apodList.toList()
}

fun CachedAPoD.toAPoD(): APoD {
    return APoD(
        this.title,
        this.url,
        this.date,
        this.mediaType,
        this.explanation,
        this.thumbnailUrl
    )
}

fun APoD.toCachedAPoD(): CachedAPoD {
    return CachedAPoD(
        this.title,
        this.url,
        this.date,
        this.mediaType,
        this.explanation,
        this.thumbnailUrl
    )
}

fun List<CachedAPoD>.cacheToAPoDList(): List<APoD> {
    val apodList = mutableListOf<APoD>()
    this.forEach { cachedAPoD ->
        apodList.add(cachedAPoD.toAPoD())
    }
    return apodList.toList()
}

fun List<APoD>.toCachedAPoDList(): List<CachedAPoD> {
    val cachedApodList = mutableListOf<CachedAPoD>()
    this.forEach { apod ->
        cachedApodList.add(apod.toCachedAPoD())
    }
    return cachedApodList
}