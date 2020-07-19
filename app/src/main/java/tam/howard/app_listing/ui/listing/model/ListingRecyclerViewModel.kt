package tam.howard.app_listing.ui.listing.model

import kotlinx.serialization.Serializable
import tam.howard.app_listing.model.ITunesItemDetail

@Serializable
sealed class ListingRecyclerViewModel {

    @Serializable
    class GrossingList(val list: ArrayList<ITunesItemDetail>) : ListingRecyclerViewModel()
    @Serializable
    class FreeApplicationItem(val detail: ITunesItemDetail) : ListingRecyclerViewModel()
}