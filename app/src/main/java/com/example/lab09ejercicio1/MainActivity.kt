package com.example.lab09ejercicio1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lab09ejercicio1.ui.theme.Lab09Ejercicio1Theme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab09Ejercicio1Theme {
                AppTienda()
            }
        }
    }
}

@Composable
fun AppTienda() {
    val servicio = remember {
        Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductApiService::class.java)
    }
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "productos") {
        composable("productos") { ScreenProductos(navController, servicio) }
        composable(
            "producto/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            ScreenProducto(navController, servicio, it.arguments!!.getInt("id"))
        }
    }
}
