package be.mbolle.mealplanner.data

import be.mbolle.mealplanner.BuildConfig
import be.mbolle.mealplanner.data.entities.Menu
import io.ktor.client.HttpClient
import io.ktor.client.request.get

const val ENDPOINT = BuildConfig.BUILD_SERVICE // only development endpoint

open class RecipeApi(private val client: HttpClient)

class Menu(private val client: HttpClient): RecipeApi(client) {
    suspend fun getMenu(): List<Menu> = client.get("${ENDPOINT}/menu")
}