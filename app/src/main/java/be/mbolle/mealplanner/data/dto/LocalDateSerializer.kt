package be.mbolle.mealplanner.data.dto

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
        val dateSequence = stringDate.splitToSequence("-").toList()
        val year = dateSequence.first().toInt()
        val month = dateSequence[1].toInt()
        val day = dateSequence.last().toInt()

        return LocalDate.of(year, month, day)
    }
}

