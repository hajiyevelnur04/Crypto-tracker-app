package com.crypto.tracker.network.handler

import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

open class ResponseHandler {
    fun <T> handleSuccess(data: T): Resource<T> {
        return Resource.success(data)
    }

    fun <T : Any> handleException(e: Exception): Resource<T> {
        e.printStackTrace()

        return when (e) {
            is HttpException -> {

                var message = ""
                val errorJsonString = e.response()?.errorBody()?.string()
                // extra resources
                /*var _status = JSONObject(errorJsonString).getString("status")
                var _statusCode = JSONObject(errorJsonString).getString("statusCode")
                var _timestamp = JSONObject(errorJsonString).getString("timestamp")*/
                message = try {
                    JSONObject(errorJsonString).getString("message")
                } catch (e:Exception){
                    "Error"
                }

                Resource.error(message, null)
            }
            is IOException -> Resource.networkError("No internet connection", null)
            else -> Resource.error(getErrorMessage(Int.MAX_VALUE), null)
        }
    }

    private fun getErrorMessage(code: Int): String {
        return when (code) {
            401 -> "Unauthorized"
            404 -> "Not found"
            500 -> "Internal server error"
            503 -> "Müvəqqəti narahatlıq daimi rahatlıq)"
            else -> "Something went wrong"
        }
    }

}
