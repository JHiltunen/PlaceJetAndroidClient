package com.jhiltunen.placejetandroid.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Products(
    @PrimaryKey(autoGenerate = true)
    val productId: Long,
    val name: String,
    val description: String,
    val productType: ProductType?,
    val cableType: CableType?,
    val cableLength: Double?,
    val manufacturer: String?,
    val model: String?,
    val imageSrc: String?
) {
    //constructor, getter and setter are implicit :)
    override fun toString() = "$productId: $name - $description - $productType"
}

enum class ProductType(val displayName: String) {
    AUDIOCABLE("Audiocable"),
    DISPLAYCABLE("Displaycable"),
    POWERCABLE("Powercable"),
    MICSTAND("Microphonestand"),
    MIC("Microphone"),
    MIXER("Mixer")
}

enum class CableType(val displayName: String) {
    XLRMALETOXLRFEMALE("XLR Male to Female"),
    XLRFEMALETOTRS("XLR Female to TRS Jack"),
    XLRMALETOTRS("XLR Male to TRS Jack"),
    HDMITOHDMI("HDMI to HDMI"),
    HDMITOHDMIMINI("HDMI to HDMI MINI"),
    DISPLAYPORTTOHDMI("Displayport to HDMI"),
    USBATOUSBC("USB A to USB C"),
    USBCTOUSBC("USB C to USB C")
}

inline fun <reified T: Enum<T>> printEnumValues() {
    for(value in enumValues<T>()) {
        println(value)
    }
}

fun getValueFromProductTypes(s: String): ProductType? = ProductType.values().find { it.displayName == s }
fun getValueFromCableTypes(s: String): CableType? = CableType.values().find { it.displayName == s }