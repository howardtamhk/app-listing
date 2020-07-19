package tam.howard.app_listing.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ITunesListingItem(
    @SerialName("im:name")
    val name: ITunesListingItemInnerModel,
    val summary: ITunesListingItemInnerModel,
    val title: ITunesListingItemInnerModel,
    val id: ITunesListingItemInnerModel,
    @SerialName("im:artist")
    val artist: ITunesListingItemInnerModel,
    val category: ITunesListingItemInnerModel

)

@Serializable
data class ITunesListingItemInnerModel(
    val label: String = "",
    val attributes: ITunesListingItemIdAttributesModel? = null
)

@Serializable
data class ITunesListingItemIdAttributesModel(
    @SerialName("im:id")
    val id: String = "",
    val label: String = ""
)