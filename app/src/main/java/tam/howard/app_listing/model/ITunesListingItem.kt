package tam.howard.app_listing.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ITunesListingItem(
    val id: ITunesListingItemIdModel
)

@Serializable
data class ITunesListingItemIdModel(
    val attributes: ITunesListingItemIdAttributesModel
)

@Serializable
data class ITunesListingItemIdAttributesModel(
    @SerialName("im:id")
    val id: String
)