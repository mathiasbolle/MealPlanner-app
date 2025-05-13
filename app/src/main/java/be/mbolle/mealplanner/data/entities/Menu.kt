package be.mbolle.mealplanner.data.entities

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
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

@Serializable
data class Menu(
    @SerialName("id")
    val id: Int,
    @SerialName("date")
    @Serializable(with = LocalDateSerializer::class)
    val date: LocalDate,
    @SerialName("mealParts")
    val meals: List<Meal>
)

@Serializable
data class Meal(
    val name: String
)