package com.example.prodteamfinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.prodteamfinal.navigation.StackNavigator

const val username = "Андрей Андреевич"
const val phoneNumber = "+79674654425"
const val token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxfQ.DHnI9P0eM-2IV9c-_2rCDUX-RobJi0om_ygItKG7R34"
const val apiUrl = "http://158.160.117.217:8000"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StackNavigator()
        }
    }
}