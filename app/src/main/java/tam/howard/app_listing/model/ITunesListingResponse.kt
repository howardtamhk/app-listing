package tam.howard.app_listing.model

import kotlinx.serialization.Serializable

@Serializable
data class ITunesListingResponse(
    val feed: ITunesListingResponseFeedModel?
)

@Serializable
data class ITunesListingResponseFeedModel(val entry: ArrayList<ITunesListingItem> = arrayListOf())