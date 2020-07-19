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

        //todo: either api error handling
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
        //todo: either api error handling

        val startIndex = offset * limit
        var endIndex = (offset + 1) * limit
        if (startIndex > endIndex || startIndex > this.freeApplicationList.size || startIndex < 0) {
            return arrayListOf()
        }

        if (endIndex > this.freeApplicationList.size) {
            endIndex = this.freeApplicationList.size
        }

        val idList: List<String> =
            this.grossingApplicationList.subList(startIndex, endIndex)
                .mapNotNull { it.id.attributes?.id }
        return this.iTunesApiProvider.getItemsDetail(idList.joinToString(",")).results

    }

    suspend fun getFreeApplicationDetail(
        offset: Int = 0,
        limit: Int = 10
    ): ArrayList<ITunesItemDetail> {
        //todo: either api error handling
        val startIndex = offset * limit
        var endIndex = (offset + 1) * limit
        if (startIndex > endIndex || startIndex > this.freeApplicationList.size || startIndex < 0) {
            return arrayListOf()
        }

        if (endIndex > this.freeApplicationList.size) {
            endIndex = this.freeApplicationList.size
        }

        val idList: List<String> =
            this.freeApplicationList.subList(startIndex, endIndex)
                .mapNotNull { it.id.attributes?.id }
        return this.iTunesApiProvider.getItemsDetail(idList.joinToString(",")).results

    }


    suspend fun search(
        keyword: String, offset: Int = 0,
        limit: Int = 10
    ): ArrayList<ITunesItemDetail> {
        val filteredList = arrayListOf(
            *this.freeApplicationList.toTypedArray(),
            *this.grossingApplicationList.toTypedArray()
        ).filter {
            it.name.label.contains(
                keyword,
                true
            ) || it.category.attributes?.label?.contains(
                keyword,
                true
            ) == true || it.artist.label.contains(keyword, true) || it.summary.label.contains(
                keyword,
                true
            )
        }
        val startIndex = offset * limit
        var endIndex = (offset + 1) * limit
        if (startIndex > endIndex || startIndex > filteredList.size || startIndex < 0) {
            return arrayListOf()
        }

        if (endIndex > filteredList.size) {
            endIndex = filteredList.size
        }

        val idList: List<String> =
            filteredList.subList(startIndex, endIndex)
                .mapNotNull { it.id.attributes?.id }

        return this.iTunesApiProvider.getItemsDetail(idList.joinToString(",")).results
    }
}