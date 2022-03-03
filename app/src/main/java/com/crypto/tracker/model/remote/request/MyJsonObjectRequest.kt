package com.crypto.tracker.model.remote.request

import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.crypto.tracker.model.local.AlertType
import com.crypto.tracker.model.remote.response.VolleyJsonObjectResponse
import org.json.JSONObject

class MyJsonObjectRequest(
    i: Int,
    ids: String?,
    jSONObject: JSONObject?,
    currencies: String,
    alertType: AlertType,
    volleyJsonObjectResponse: VolleyJsonObjectResponse
) :
    Response.Listener<JSONObject?>, Response.ErrorListener {
    private val alertItem: AlertType
    private val jsonObjectRequest: JsonObjectRequest
    private val tag: String = currencies
    private val volleyResponse: VolleyJsonObjectResponse = volleyJsonObjectResponse
    override fun onResponse(jSONObject: JSONObject?) {
        volleyResponse.onResponse(jSONObject, tag, alertItem)
    }

    override fun onErrorResponse(volleyError: VolleyError) {
        volleyResponse.onError(volleyError, tag)
    }

    fun getAlertItem(): AlertType {
        return alertItem
    }

    fun getJsonObjectRequest(): JsonObjectRequest? {
        return jsonObjectRequest
    }

    init {
        alertItem = alertType
        jsonObjectRequest = JsonObjectRequest(i, ids, jSONObject, this, this)
    }
}
