package tam.howard.app_listing.repository

import tam.howard.app_listing.model.ITunesItemDetail
import tam.howard.app_listing.model.ITunesListingItem
import tam.howard.app_listing.model.ITunesListingResponse
import tam.howard.app_listing.provider.ITunesApiProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ITunesRepository @Inject constructor(private val iTunesApiProvider: ITunesApiProvider) {

    private val freeApplicationList: ArrayList<ITunesListingItem> = arrayListOf()
    private val grossingApplicationList: ArrayList<ITunesListingItem> = arrayListOf()


    private suspend fun getTopFreeApplicationsList(): ITunesListingResponse =
        this.iTunesApiProvider.getTopFreeApplicationsList()

    private suspend fun getTopGrossingApplicationsList(): ITunesListingResponse =
        this.iTunesApiProvider.getTopGrossingApplicationsList()

    suspend fun reloadApplicationListing() {
        this.freeApplicationList.clear()
        this.grossingApplicationList.clear()

        //todo: either error handling
        val freeApplicationResponse = this.getTopFreeApplicationsList()
        val grossingApplicationResponse = this.getTopGrossingApplicationsList()

        this.freeApplicationList.addAll(freeApplicationResponse.feed?.entry ?: arrayListOf())
        this.grossingApplicationList.addAll(
            grossingApplicationResponse.feed?.entry ?: arrayListOf()
        )
    }

    suspend fun getGrossingApplicationsDetail(
        offset: Int = 0,
        limit: Int = 10
    ): ArrayList<ITunesItemDetail> {
        //todo: either error handling
        val idList: List<String> =
            this.grossingApplicationList.subList(offset, limit).mapNotNull { it.id.attributes?.id }
        return this.iTunesApiProvider.getItemsDetail(idList.joinToString(",")).results

    }

    suspend fun getFreeApplicationDetail(
        offset: Int = 0,
        limit: Int = 10
    ): ArrayList<ITunesItemDetail> {
        //todo: either error handling
        val idList: List<String> =
            this.freeApplicationList.subList(offset, limit).mapNotNull { it.id.attributes?.id }
        return this.iTunesApiProvider.getItemsDetail(idList.joinToString(",")).results

    }
}