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

    val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val listingRecyclerViewModelListLiveData: MutableLiveData<ArrayList<ListingRecyclerViewModel>> =
        MutableLiveData(arrayListOf())

    val apiError: MutableLiveData<Boolean> = MutableLiveData()

    var offset: Int = 0


    fun reload() {
        viewModelScope.launch {
            this@ListingViewModel.isLoading.value = true
            val currentOffset = offset
            offset = 0

            try {
                this@ListingViewModel.iTunesRepository.reloadApplicationListing()
                val grossingAppDetails =
                    this@ListingViewModel.iTunesRepository.getGrossingApplicationsDetail()
                val freeAppDetails =
                    this@ListingViewModel.iTunesRepository.getFreeApplicationDetail()

                this@ListingViewModel.listingRecyclerViewModelListLiveData.value =
                    arrayListOf(
                        ListingRecyclerViewModel.GrossingList(
                            grossingAppDetails
                        ),
                        *freeAppDetails.map { ListingRecyclerViewModel.FreeApplicationItem(it) }
                            .toTypedArray()
                    )
            } catch (e: Exception) {
                offset = currentOffset
                this@ListingViewModel.apiError.value = true
            } finally {
                this@ListingViewModel.isLoading.value = false

            }

        }
    }

    fun loadNextFreeAppListingPage() {
        if (this.isLoading.value == true) {
            return
        }
        viewModelScope.launch {
            this@ListingViewModel.isLoading.value = true
            offset++

            try {
                val freeAppDetails =
                    this@ListingViewModel.iTunesRepository.getFreeApplicationDetail(offset = offset)

                this@ListingViewModel.listingRecyclerViewModelListLiveData.value =
                    arrayListOf<ListingRecyclerViewModel>(
                        *this@ListingViewModel.listingRecyclerViewModelListLiveData.value?.toTypedArray()
                            ?: arrayOf(),
                        *freeAppDetails.map { ListingRecyclerViewModel.FreeApplicationItem(it) }
                            .toTypedArray()
                    )
            } catch (e: Exception) {
                offset--
                this@ListingViewModel.apiError.value = true
            } finally {
                this@ListingViewModel.isLoading.value = false
            }


        }
    }
}