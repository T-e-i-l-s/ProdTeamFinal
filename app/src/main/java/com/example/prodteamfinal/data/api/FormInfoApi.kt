package com.example.prodteamfinal.data.api

import android.content.Context
import android.util.Log
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.prodteamfinal.apiUrl
import com.example.prodteamfinal.domain.model.ExecutorModel
import com.example.prodteamfinal.domain.model.FormModel
import com.example.prodteamfinal.domain.model.LocationModel
import com.example.prodteamfinal.domain.model.ParticipantModel
import com.example.prodteamfinal.domain.state.FormState
import com.example.prodteamfinal.domain.state.FormType
import com.example.prodteamfinal.token
import org.json.JSONArray
import org.json.JSONObject

class FormInfoApi {
    fun deleteForm(
        context: Context,
        id: String
    ) {
        val url = "$apiUrl/meetings/$id"

        val requestQueue = Volley.newRequestQueue(context)

        val stringRequest = object : StringRequest(
            Method.DELETE,
            url,
            {},
            {}
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = token
                return headers
            }
        }

        requestQueue.add(stringRequest)
    }
    fun getFormInfo(
        context: Context,
        id: String,
        onFinish: (result: FormModel) -> Unit,
        onError: () -> Unit
    ) {
        // URL запроса
        val url = "$apiUrl/meetings/$id"

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
                return headers
            }
        }

        requestQueue.add(stringRequest)
    }

    private fun handleResponse(response: String, onFinish: (result: FormModel) -> Unit) {
        val json = JSONObject(response)

        val documentsJson = json.getJSONObject("documents").getJSONArray("documents")
        val documents = ArrayList<String>()
        for(i in 0..<documentsJson.length()) {
            documents.add(documentsJson.getString(i))
        }

        val executor = json.getJSONObject("agent")
        val type = json.getString("type")

        val participants = ArrayList<ParticipantModel>()
        val participantsJson = JSONArray(json.getString("participants"))
        for (j in 0..<participantsJson.length()) {
            val participant = participantsJson.getJSONObject(j)
            participants.add(ParticipantModel(
                participant.getString("name"),
                participant.getString("phone_number"),
            ))
        }

        onFinish(
            FormModel(
                json.getString("id"),
                FormState.ACTIVE,
                when (type) {
                    "ООО" -> FormType.ООО
                    else -> FormType.ИП
                },
                json.getJSONObject("place").getString("name"),
                ExecutorModel(
                    executor.getString("name"),
                    executor.getString("description"),
                    executor.getString("photo"),
                    executor.getString("phone_number"),
                ),
                json.getString("date"),
                documents,
                participants
            )
        )
    }
}