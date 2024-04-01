package com.example.prodteamfinal.data.api

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.prodteamfinal.apiUrl
import com.example.prodteamfinal.domain.model.ExecutorModel
import com.example.prodteamfinal.domain.model.FormModel
import com.example.prodteamfinal.domain.model.ParticipantModel
import com.example.prodteamfinal.domain.state.FormState
import com.example.prodteamfinal.domain.state.FormType
import com.example.prodteamfinal.token
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject

class FormsApi {
    fun addForm(
        context: Context,
        time: String,
        agentId: String,
        locationName: String,
        lat: String,
        lon: String,
        participants: MutableState<ArrayList<ParticipantModel>>
    ) {
        val url = "$apiUrl/meetings/"

        val requestQueue = Volley.newRequestQueue(context)

        val stringRequest = object : StringRequest(
            Method.POST,
            url,
            {},
            {}
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = token
                headers["content-type"] = "application/json"
                headers["accept"] = "application/json"
                return headers
            }

            override fun getBody(): ByteArray {

                val body = JSONObject()

                body.put("date", time)
                body.put("agent_id", agentId)

                // Place
                val place = JSONObject()
                place.put("name", locationName)
                place.put("longitude", lat)
                place.put("latitude", lon)
                body.put("place", place)

                // Participants
                val participantsArray = JSONArray()

                for (i in 0..<participants.value.size) {
                    val participant = JSONObject()
                    participant.put("name", participants.value[i].name)
                    participant.put("position", "")
                    participant.put("phone_number", participants.value[i].phoneNum)
                    participantsArray.put(participant)
                }
                body.put("participants", participantsArray)

                return body.toString().toByteArray()
            }
        }

        requestQueue.add(stringRequest)
    }

    fun getForms(
        context: Context,
        onFinish: (result: ArrayList<FormModel>) -> Unit,
        onError: () -> Unit
    ) {
        // URL запроса
        val url = "$apiUrl/meetings/"

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
            // Добавляем хедеры к запросу
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = token
                headers["Accept"] = "application/json"
                headers["Content-Type"] = "application/json"
                headers["Accept-Charset"] = "utf-8"
                headers["Accept-Encoding"] = "gzip, deflate, br"
                return headers
            }

            // Указываем кодировку UTF-8
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }
        }

        requestQueue.add(stringRequest)
    }

    private fun handleResponse(response: String, onFinish: (result: ArrayList<FormModel>) -> Unit) {
        val str = response.toByteArray(Charsets.UTF_8).toString(Charsets.UTF_8)
        val list = ArrayList<FormModel>()
        val jsonArray = JSONArray(str)
        for (i in 0..<jsonArray.length()) {
            val form = jsonArray.getJSONObject(i)
            val executor = form.getJSONObject("agent")
            val type = form.getString("type")
            val participants = ArrayList<ParticipantModel>()
            val participantsJson = JSONArray(form.getString("participants"))
            for (j in 0..<participantsJson.length()) {
                val participant = participantsJson.getJSONObject(j)
                participants.add(ParticipantModel(
                    participant.getString("name"),
                    participant.getString("phone_number"),
                ))
            }
            list.add(
                FormModel(
                    form.getString("id"),
                    FormState.ACTIVE,
                    when (type) {
                        "ООО" -> FormType.ООО
                        else -> FormType.ИП
                    },
                    form.getJSONObject("place").getString("name"),
                    ExecutorModel(
                        executor.getString("name"),
                        executor.getString("description"),
                        executor.getString("photo"),
                        executor.getString("phone_number"),
                    ),
                    form.getString("date"),
                    ArrayList(),
                    participants
                )
            )
        }

        Log.d("RESULT___", list.toString())
        onFinish(list)
    }
}