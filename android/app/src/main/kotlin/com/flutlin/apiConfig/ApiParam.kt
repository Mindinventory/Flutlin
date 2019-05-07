package com.flutlin.apiConfig

import java.util.*

object ApiParam {
    var BASE_URL = "https://randomuser.me"

    val HTTP_SUCCESS_STATUS = 200
    val HTTP_VALIDATION_ERROR = 400
    val HTTP_UNAUTHORIZED_USER = 401
    val HTTP_UNVERIFIED_USER = 402
    val HTTP_DEACTIVED_USER = 403
    val HTTP_DELETED_USER = 405
    val HTTP_MAINTAINANCE_MODE = 503

    val META_STATUS_SUCCESS = 0
    val META_STATUS_FAIL = 1
    val META_STATUS_UNVERIFIED = 4
    val META_STATUS_CHANGE_PASSOWRD = 10

    fun getParam(): HashMap<String, String> {
        return HashMap()
    }

    // Check App Version Api
    object AppVersion {
        const val API_TAG = "force-update"
    }

    // Country Api
    object Country {
        const val API_TAG = "country"
    }
}