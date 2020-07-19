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

    val searchValue: MutableLiveData<String> = MutableLiveData()

    var offset: Int = 0


    init {
        this.reloadListing()
    }

    fun reload() {
        if (this.searchValue.value.isNullOrBlank()) {
            this.reloadListing()
        } else {
            this.search()
        }
    }


    private fun reloadListing() {
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

    fun search() {
        viewModelScope.launch {
            this@ListingViewModel.isLoading.value = true
            val currentOffset = offset
            offset = 0

            try {
                val result =
                    this@ListingViewModel.iTunesRepository.search(
                        this@ListingViewModel.searchValue.value ?: ""
                    )

                this@ListingViewModel.listingRecyclerViewModelListLiveData.value =
                    arrayListOf(
                        *result.map { ListingRecyclerViewModel.SearchItem(it) }
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

    fun loadNextPage() {
        if (this.searchValue.value.isNullOrBlank()) {
            this.loadNextFreeAppListingPage()
        } else {
            this.loadNextSearchPage()
        }
    }

    private fun loadNextFreeAppListingPage() {
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

    private fun loadNextSearchPage() {
        viewModelScope.launch {
            this@ListingViewModel.isLoading.value = true
            offset++

            try {
                val result =
                    this@ListingViewModel.iTunesRepository.search(
                        this@ListingViewModel.searchValue.value ?: "",
                        offset = offset
                    )

                this@ListingViewModel.listingRecyclerViewModelListLiveData.value =
                    arrayListOf(
                        *this@ListingViewModel.listingRecyclerViewModelListLiveData.value?.toTypedArray()
                            ?: arrayOf(),
                        *result.map { ListingRecyclerViewModel.SearchItem(it) }
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