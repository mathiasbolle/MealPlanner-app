package be.mbolle.mealplanner.data.api

import io.ktor.client.HttpClient
import io.ktor.client.request.get

interface MealService {
    suspend fun getMeal(): List<be.mbolle.mealplanner.data.entities.Meal>
}

class Meal(private val client: HttpClient): RecipeApi(client), MealService {
    override suspend fun getMeal(): List<be.mbolle.mealplanner.data.entities.Meal> = client.get("${ENDPOINT}/meals")
}
