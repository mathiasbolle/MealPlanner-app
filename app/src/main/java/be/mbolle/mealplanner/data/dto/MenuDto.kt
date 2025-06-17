package be.mbolle.mealplanner.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class MenuDto(
    @SerialName("id")
    val id: Int,
    @SerialName("date")
    @Serializable(with = LocalDateSerializer::class)
    val date: LocalDate,
    @SerialName("mealParts")
    val mealMenuDtos: List<MealMenuDto>
)

@Serializable
data class MenuDateDto(
    @SerialName("id")
    val id: Int,
    @SerialName("date")
    @Serializable(with = LocalDateSerializer::class)
    val date: LocalDate,
)