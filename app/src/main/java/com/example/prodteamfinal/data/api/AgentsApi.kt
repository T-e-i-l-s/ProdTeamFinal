package com.example.prodteamfinal.data.api

import android.content.Context
import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.prodteamfinal.apiUrl
import com.example.prodteamfinal.domain.model.FullExecutorModel
import com.example.prodteamfinal.token
import org.json.JSONArray

class AgentsApi {
    fun getAgents(
        context: Context,
        lat: String,
        lon: String,
        time: String,
        onFinish: (result: ArrayList<FullExecutorModel>) -> Unit,
        onError: () -> Unit
    ) {
        // URL запроса
        val url = "$apiUrl/agents?longitude=$lon&latitude=$lat&date_time=$time"

        val requestQueue = Volley.newRequestQueue(context)

        val stringRequest = object : StringRequest(
            Method.GET,
            url,
            { response ->
                handleResponse(response, onFinish)
            },
            {
                onError()
            }
        ) {
            override fun parseNetworkResponse(response: NetworkResponse): Response<String> {
                return Response.success(response.data.toString(Charsets.UTF_8), HttpHeaderParser.parseCacheHeaders(response))
            }
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = token
                headers["Accept"] = "application/json"
                headers["Content-Type"] = "application/json"
                headers["Accept-Charset"] = "utf-8"
                headers["Accept-Encoding"] = "gzip, deflate, br"
                return headers
            }
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }
        }

        requestQueue.add(stringRequest)
    }

    private fun handleResponse(response: String, onFinish: (result: ArrayList<FullExecutorModel>) -> Unit) {
        val executors = JSONArray(response)
        val list = ArrayList<FullExecutorModel>()
        for(i in 0..<executors.length()) {
            val executor = executors.getJSONObject(i)
            list.add(
                FullExecutorModel(
                    executor.getString("id"),
                    executor.getString("name"),
                    executor.getString("description"),
                    executor.getString("photo"),
                    executor.getString("phone_number"),
                )
            )
        }
        onFinish(list)
    }
}