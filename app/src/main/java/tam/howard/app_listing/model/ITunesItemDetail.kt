package tam.howard.app_listing.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ITunesItemDetail(
    @SerialName("artworkUrl512")
    val iconUrl: String = "",
    @SerialName("trackCensoredName")
    val name: String = "",
    @SerialName("averageUserRating")
    val rating: Double = 0.0,
    val genres: ArrayList<String> = arrayListOf(),
    val userRatingCount: Int = 0

)


