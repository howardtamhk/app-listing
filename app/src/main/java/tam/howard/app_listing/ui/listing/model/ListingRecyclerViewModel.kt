package tam.howard.app_listing.ui.listing.model

import kotlinx.serialization.Serializable
import tam.howard.app_listing.model.ITunesItemDetail

@Serializable
sealed class ListingRecyclerViewModel {

    @Serializable
    data class GrossingList(val list: ArrayList<ITunesItemDetail>) : ListingRecyclerViewModel()

    @Serializable
    data class FreeApplicationItem(val detail: ITunesItemDetail) : ListingRecyclerViewModel()
}