package com.example.prodteamfinal.data.api

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.prodteamfinal.domain.model.LocationModel
import org.json.JSONArray

class NominatimApi {
    fun getValidAddress(
        context: Context,
        request: String,
        onFinish: (result: LocationModel) -> Unit
    ) {
        // URL запроса
        val url = "https://nominatim.openstreetmap.org/search?q=$request&format=json&limit=1"

        val requestQueue = Volley.newRequestQueue(context)

        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                val locations = JSONArray(response)
                if (locations.length() > 0) {
                    val location = locations.getJSONObject(0)
                    onFinish(
                        LocationModel(
                            location.getString("display_name"),
                            location.getString("lat"),
                            location.getString("lon")
                        )
                    )
                } else {
                    onFinish(
                        LocationModel(
                            "",
                            "",
                            ""
                        )
                    )
                }
            },

            {}
        )

        requestQueue.add(stringRequest)
    }
}