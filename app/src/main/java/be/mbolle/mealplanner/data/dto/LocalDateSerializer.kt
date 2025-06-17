package be.mbolle.mealplanner.data.dto

import be.mbolle.mealplanner.data.toLocalDate
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object LocalDateSerializer : KSerializer<LocalDate> {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    override val descriptor: SerialDescriptor
        get() = buildClassSerialDescriptor("be.mbolle.mealplanner.")

    override fun serialize(
        encoder: Encoder,
        value: LocalDate
    ) {
        encoder.encodeString(value.format(formatter)) // Serialize to "yyyy-MM-dd"
    }

    override fun deserialize(decoder: Decoder): LocalDate {
        val stringDate = decoder.decodeString()

        return stringDate.toLocalDate()
    }
}

