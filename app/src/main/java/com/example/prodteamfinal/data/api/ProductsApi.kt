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
import com.example.prodteamfinal.domain.model.FullExecutorModel
import com.example.prodteamfinal.domain.model.ParticipantModel
import com.example.prodteamfinal.domain.model.ProductModel
import com.example.prodteamfinal.domain.state.FormState
import com.example.prodteamfinal.domain.state.FormType
import com.example.prodteamfinal.token
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject

class ProductsApi {
    fun getProducts(
        context: Context,
        onFinish: (result: ArrayList<ProductModel>) -> Unit,
        onError: () -> Unit
    ) {
        // URL запроса
        val url = "$apiUrl/products"

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
                return Response.success(
                    response.data.toString(Charsets.UTF_8),
                    HttpHeaderParser.parseCacheHeaders(response)
                )
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

    private fun handleResponse(
        response: String,
        onFinish: (result: ArrayList<ProductModel>) -> Unit
    ) {
        val productJson = JSONArray(response)
        val list = ArrayList<ProductModel>()
        for (i in 0..<productJson.length()) {
            val product = productJson.getJSONObject(i)
            list.add(
                ProductModel(
                    product.getString("id"),
                    product.getString("name"),
                    product.getString("description"),
                    product.getString("image"),
                    product.getString("url"),
                )
            )
        }
        onFinish(list)
    }
}