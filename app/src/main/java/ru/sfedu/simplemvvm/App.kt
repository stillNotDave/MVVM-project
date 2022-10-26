package ru.sfedu.simplemvvm

import android.app.Application
import ru.sfedu.simplemvvm.model.colors.InMemoryColorsRepository


// Класс - точка входа в приложение
// Является Singleton scope который содежрит модели
class App : Application() {

    val models = listOf<Any>(
        InMemoryColorsRepository()
    )

}