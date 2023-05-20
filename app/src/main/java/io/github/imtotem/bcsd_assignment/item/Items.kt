package io.github.imtotem.bcsd_assignment.item

abstract class ViewItems

data class ColorItem(
    val color: Int
): ViewItems()

data class NumberItem(
    val number: Int
): ViewItems()

data class AlphabetItem(
    val alphabet: Char
): ViewItems()

data class PositionItem(
    val position: Int,
    val size: Int
)