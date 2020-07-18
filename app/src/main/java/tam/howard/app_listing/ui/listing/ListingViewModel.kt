package tam.howard.app_listing.ui.listing

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tam.howard.app_listing.base.BaseViewModel
import tam.howard.app_listing.repository.ITunesRepository
import javax.inject.Inject

class ListingViewModel @Inject constructor(private val iTunesRepository: ITunesRepository) :
    BaseViewModel() {


    fun test() {
        viewModelScope.launch {
            val list =
                iTunesRepository.getTopFreeApplicationsList().feed?.entry?.map { it.id.attributes.id }
                    ?: arrayListOf()

        }
    }
}