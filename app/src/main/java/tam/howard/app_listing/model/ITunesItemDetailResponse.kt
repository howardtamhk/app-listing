package tam.howard.app_listing.model

import kotlinx.serialization.Serializable

@Serializable
data class ITunesItemDetailResponse(val results: ArrayList<ITunesItemDetail> = arrayListOf())