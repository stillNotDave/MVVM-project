package ru.sfedu.simplemvvm.model.views.changecolor

import ru.sfedu.simplemvvm.model.colors.NamedColor

/**
 * Represents list item for the color; it may be selected or not
 */
data class NamedColorListItem(
    val namedColor: NamedColor,
    val selected: Boolean
)