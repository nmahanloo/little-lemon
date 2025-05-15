// @ May 11, 2025 Nima Mahanloo
package com.example.littlelemon

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

object DishRepository {
    var dishes = emptyList<Dish>()

    suspend fun fetchMenuFromNetwork() {
        val client = HttpClient(Android) {
            install(ContentNegotiation) {
                val json = Json { ignoreUnknownKeys = true }
                register(ContentType.Application.Json, KotlinxSerializationConverter(json))
                register(ContentType.Text.Plain, KotlinxSerializationConverter(json))
            }
        }
        val response: MenuResponse = client.get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json") {
            contentType(ContentType.Application.Json)
        }.body()

        dishes = response.menu.map {
            val fixedImageUrl = it.image
                .replace("github.com", "raw.githubusercontent.com")
                .replace("blob/", "")
                .replace("?raw=true", "")

            Dish(
                it.id,
                it.title,
                it.description,
                it.price.toDoubleOrNull() ?: 0.0,
                fixedImageUrl,
                it.category
            )
        }
    }

    fun getDish(id: Int) = dishes.firstOrNull { it.id == id }
}

@Serializable
data class MenuResponse(val menu: List<DishNetwork>)

@Serializable
data class DishNetwork(
    val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val image: String,
    val category: String
)

data class Dish(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val category: String
)
