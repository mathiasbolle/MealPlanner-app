package be.mbolle.mealplanner.data.remote.api

import be.mbolle.mealplanner.data.dto.MealDto
import io.ktor.client.HttpClient
import io.ktor.client.request.get

interface MealService {
    suspend fun getMeal(): List<MealDto>
    //suspend fun removeMeal(id: Int):
}

class MealServiceImpl(private val client: HttpClient) : MealService {
    override suspend fun getMeal(): List<MealDto> = client.get("${ENDPOINT}/meals")
}
