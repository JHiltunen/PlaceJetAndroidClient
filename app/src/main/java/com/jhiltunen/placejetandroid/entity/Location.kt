package com.jhiltunen.placejetandroid.entity

enum class Location(val displayName: String) {
    HOME("Home"),
    OFFICE("Office"),
    BACKPACK("Backpack"),
    WAREHOUSE("Warehouse"),
    ONLOAN("On loan"),
    MISSING("Missing"),
    STOLEN("Stolen")
}

fun getValueFromLocationTypes(s: String?): Location? = Location.values().find { it.displayName == s }