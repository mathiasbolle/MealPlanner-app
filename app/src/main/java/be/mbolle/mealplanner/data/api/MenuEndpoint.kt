package be.mbolle.mealplanner.data.api

import be.mbolle.mealplanner.BuildConfig
import be.mbolle.mealplanner.data.entities.Menu
import io.ktor.client.HttpClient
import io.ktor.client.request.get

const val ENDPOINT = BuildConfig.BUILD_SERVICE // only development endpoint

open class RecipeApi(private val client: HttpClient)

interface MenuService {
    suspend fun getMenu(): List<Menu>
}

class Menu(private val client: HttpClient): RecipeApi(client), MenuService {
    override suspend fun getMenu(): List<Menu> = client.get("${ENDPOINT}menu")
}
