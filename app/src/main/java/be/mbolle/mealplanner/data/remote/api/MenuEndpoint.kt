package be.mbolle.mealplanner.data.remote.api

import be.mbolle.mealplanner.BuildConfig
import be.mbolle.mealplanner.data.dto.MenuDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get

const val ENDPOINT = BuildConfig.BUILD_SERVICE // only development endpoint

interface MenuService {
    suspend fun getMenu(): List<MenuDto>
}

class MenuServiceImpl(private val client: HttpClient) : MenuService {
    override suspend fun getMenu(): List<MenuDto> = client.get("${ENDPOINT}menu")
}
