package com.example.prodteamfinal.data.api

import android.content.Context
import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.prodteamfinal.apiUrl
import com.example.prodteamfinal.domain.model.ProductModel
import com.example.prodteamfinal.token
import org.json.JSONArray

class ProductsApi {
    fun post(
        context: Context,
        id: String
    ) {
        val url = "$apiUrl/products/$id"

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
        }

        requestQueue.add(stringRequest)
    }

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