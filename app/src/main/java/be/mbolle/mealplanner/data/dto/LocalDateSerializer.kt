package be.mbolle.mealplanner.data.dto

import be.mbolle.mealplanner.data.toLocalDate
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDate

object LocalDateSerializer : KSerializer<LocalDate> {
    override val descriptor: SerialDescriptor
        get() = buildClassSerialDescriptor("be.mbolle.mealplanner.")

    override fun serialize(
        encoder: Encoder,
        value: LocalDate
    ) {
        //TODO encode this to the YYYY-MM-DD format
        //encoder.encodeString(value.)
    }

    override fun deserialize(decoder: Decoder): LocalDate {
        val stringDate = decoder.decodeString()

        return stringDate.toLocalDate()
    }
}

