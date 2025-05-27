package be.mbolle.mealplanner.data.api

import be.mbolle.mealplanner.data.entities.Ingredient
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType

interface IngredientService {
    suspend fun addIngredient(
        ingredient: Ingredient
    )
}

class IngredientServiceEndpoint(private val client: HttpClient): RecipeApi(client), IngredientService {
    override suspend fun addIngredient(ingredient: Ingredient) {
        client.post<Ingredient>("${ENDPOINT}/ingredients/") {
            contentType(ContentType.Application.Json)
            body = ingredient
        }
    }
}