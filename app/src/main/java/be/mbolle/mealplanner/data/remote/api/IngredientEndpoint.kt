package be.mbolle.mealplanner.data.remote.api

import be.mbolle.mealplanner.data.dto.IngredientDto
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType

interface IngredientService {
    suspend fun addIngredient(
        ingredientDto: IngredientDto
    )
}

class IngredientServiceImpl(private val client: HttpClient) : IngredientService {
    override suspend fun addIngredient(ingredientDto: IngredientDto) {
        client.post<IngredientDto>("${ENDPOINT}/ingredients/") {
            contentType(ContentType.Application.Json)
            body = ingredientDto
        }
    }
}