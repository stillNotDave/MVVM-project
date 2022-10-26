package ru.sfedu.simplemvvm.model.views

interface UiActions {

    fun toast(message: String)

    fun getString(messageRes: Int, vararg args: Any): String
}