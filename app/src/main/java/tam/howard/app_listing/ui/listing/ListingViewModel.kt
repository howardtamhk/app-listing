package tam.howard.app_listing.ui.listing

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tam.howard.app_listing.base.BaseViewModel
import tam.howard.app_listing.repository.ITunesRepository
import tam.howard.app_listing.ui.listing.model.ListingRecyclerViewModel
import javax.inject.Inject

class ListingViewModel @Inject constructor(private val iTunesRepository: ITunesRepository) :
    BaseViewModel() {

    val listingRecyclerViewModelListLiveData: MutableLiveData<ArrayList<ListingRecyclerViewModel>> =
        MutableLiveData(arrayListOf())


    fun reload() {
        viewModelScope.launch {
            this@ListingViewModel.iTunesRepository.reloadApplicationListing()
            val grossingAppDetails =
                this@ListingViewModel.iTunesRepository.getGrossingApplicationsDetail()
            val freeAppDetails = this@ListingViewModel.iTunesRepository.getFreeApplicationDetail()

            this@ListingViewModel.listingRecyclerViewModelListLiveData.value =
                arrayListOf<ListingRecyclerViewModel>(
                    ListingRecyclerViewModel.GrossingList(
                        grossingAppDetails
                    ),
                    *freeAppDetails.map { ListingRecyclerViewModel.FreeApplicationItem(it) }
                        .toTypedArray()
                )

        }
    }
}