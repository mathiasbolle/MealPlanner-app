package be.mbolle.mealplanner.data.remote.api

import be.mbolle.mealplanner.BuildConfig
import be.mbolle.mealplanner.data.dto.MenuDateDto
import be.mbolle.mealplanner.data.dto.MenuDto
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.http.ContentType
import io.ktor.http.contentType

const val ENDPOINT = BuildConfig.BUILD_SERVICE // only development endpoint

interface MenuService {
    suspend fun getMenu(): List<MenuDto>
    suspend fun removeMenu(id: Int)
    suspend fun swapMenu(id: Int, menuDto: MenuDateDto): List<Unit>
}

class MenuServiceImpl(private val client: HttpClient) : MenuService {
    override suspend fun getMenu(): List<MenuDto> = client.get("${ENDPOINT}menu")
    override suspend fun removeMenu(id: Int) = client.delete<Unit>("${ENDPOINT}menu/${id}")
    override suspend fun swapMenu(id: Int, menuDto: MenuDateDto): List<Unit> {
        return client.patch<List<Unit>>("${ENDPOINT}/menu/${id}/swap/") {
            contentType(ContentType.Application.Json)
            body = menuDto
        }
    }
}
