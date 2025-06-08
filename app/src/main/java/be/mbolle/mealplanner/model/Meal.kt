package be.mbolle.mealplanner.model

data class Meal (
    //val mealKind: MealKind,
    val name: String,
    val mealKind: MealKinds
)


enum class MealKinds {
    PASTA_PATATO, VEGETABLES, MEAT, DISH, OTHER, SAUCE
}