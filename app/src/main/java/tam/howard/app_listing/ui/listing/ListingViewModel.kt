package tam.howard.app_listing.ui.listing

import tam.howard.app_listing.base.BaseViewModel
import tam.howard.app_listing.repository.ITunesRepository
import javax.inject.Inject

class ListingViewModel @Inject constructor(private val iTunesRepository: ITunesRepository) :
    BaseViewModel() {

}