package com.abbasi.data.utils

internal interface Transformable<DATA_MODEL, DOMAIN_MODEL> {
    fun fromDomain(domainObj: DOMAIN_MODEL): DATA_MODEL
}