package be.mbolle.mealplanner.di

import android.content.Context
import android.util.Log
import be.mbolle.mealplanner.model.MealRepository
import be.mbolle.mealplanner.data.RemoteMealRepository
import be.mbolle.mealplanner.data.local.room.MealPlannerDatabase
import be.mbolle.mealplanner.data.remote.api.IngredientService
import be.mbolle.mealplanner.data.remote.api.IngredientServiceImpl
import be.mbolle.mealplanner.data.remote.api.MealService
import be.mbolle.mealplanner.data.remote.api.MealServiceImpl
import be.mbolle.mealplanner.data.remote.api.MenuService
import be.mbolle.mealplanner.data.remote.api.MenuServiceImpl
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.DefaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import kotlinx.serialization.json.Json

interface Container {
    val remoteMealRepository: MealRepository
    val roomdDb: MealPlannerDatabase
    val menuService: MenuService
    val mealService: MealService
    val ingredientService: IngredientService
    val client: HttpClient
}

class ContainerImpl(private val context: Context): Container {
    override val client: HttpClient
        get() = ktorHttpClient
    override val remoteMealRepository: MealRepository
        get() = RemoteMealRepository(
            this.menuService,
            this.mealService,
            this.ingredientService,
            roomdDb
        )
    override val roomdDb: MealPlannerDatabase
        get() = MealPlannerDatabase.getDatabase(context)
    override val menuService: MenuService
        get() = MenuServiceImpl(client)
    override val mealService: MealService
        get() = MealServiceImpl(client)
    override val ingredientService: IngredientService
        get() = IngredientServiceImpl(client)
}

private const val TIME_OUT = 60_000
val ktorHttpClient = HttpClient(Android) {

    install(JsonFeature) {
        serializer = KotlinxSerializer(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })

        engine {
            connectTimeout = TIME_OUT
            socketTimeout = TIME_OUT
        }

        install(Logging) {
            logger = object: Logger {
                override fun log(message: String) {
                    Log.v("Logger Ktor ==> ", message)
                }
            }

            level = LogLevel.ALL
        }

        install(ResponseObserver) {
            onResponse { response ->
                Log.d("HTTP status:", "${response.status.value}")
            }
        }

        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
    }
}
