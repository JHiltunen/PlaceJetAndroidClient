package com.jhiltunen.placejetandroid.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey(autoGenerate = true)
    val productId: Long,
    val name: String,
    val description: String,
    val productType: ProductType,
    val cableType: CableType?,
    val cableLength: Double?,
    val manufacturer: String?,
    val model: String?,
    val imageSrc: String?
) {
    //constructor, getter and setter are implicit :)
    override fun toString() = "$productId: $name - $description - $productType"
}

enum class ProductType(val productType: String) {
    AUDIOCABLE("Audiocable"),
    DISPLAYCABLE("Displaycable"),
    POWERCABLE("Powercable"),
    MICSTAND("Microphonestand"),
    MIC("Microphone"),
    MIXER("Mixer")
}

enum class CableType(val cableType: String) {
    XLRMALETOXLRFEMALE("XLR Male to Female"),
    XLRFEMALETOTRS("XLR Female to TRS Jack"),
    XLRMALETOTRS("XLR Male to TRS Jack"),
    HDMITOHDMI("HDMI to HDMI"),
    HDMITOHDMIMINI("HDMI to HDMI MINI"),
    DISPLAYPORTTOHDMI("Displayport to HDMI"),
    USBATOUSBC("USB A to USB C"),
    USBCTOUSBC("USB C to USB C")
}