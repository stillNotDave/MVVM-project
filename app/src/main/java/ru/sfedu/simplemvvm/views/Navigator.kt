package ru.sfedu.simplemvvm.model.views

import ru.sfedu.simplemvvm.model.views.base.BaseScreen

// Этот интерфейс позволяет запускать экраны и возвращаться обратно и отдавать какие-то результаты
interface Navigator {

    fun launch(screen: BaseScreen)

    // Получение строки из ресурсов по Id
    fun goBack(result: Any? = null)

}