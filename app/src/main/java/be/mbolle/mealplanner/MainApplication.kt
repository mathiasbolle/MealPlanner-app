package be.mbolle.mealplanner

import android.app.Application
import be.mbolle.mealplanner.di.Container
import be.mbolle.mealplanner.di.ContainerImpl

class MainApplication: Application() {

    companion object {
        lateinit var container: Container
    }

    override fun onCreate() {
        super.onCreate()
        container = ContainerImpl()
    }
}