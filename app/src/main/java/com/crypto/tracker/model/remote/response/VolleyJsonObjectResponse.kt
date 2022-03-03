package com.crypto.tracker.model.remote.response

import com.android.volley.VolleyError
import com.crypto.tracker.model.local.AlertType
import org.json.JSONObject

interface VolleyJsonObjectResponse {
    fun onError(volleyError: VolleyError?, str: String?)

    fun onResponse(jSONObject: JSONObject?, str: String?, alertItem: AlertType?)
}