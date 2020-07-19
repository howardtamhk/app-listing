package tam.howard.app_listing.provider

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import tam.howard.app_listing.model.ITunesItemDetailResponse
import tam.howard.app_listing.model.ITunesListingResponse

interface ITunesApiProvider {

    @GET("rss/topfreeapplications/limit={limit}/json")
    suspend fun getTopFreeApplicationsList(@Path("limit") limit: Int = 100): ITunesListingResponse

    @GET("rss/topgrossingapplications/limit={limit}/json")
    suspend fun getTopGrossingApplicationsList(@Path("limit") limit: Int = 10): ITunesListingResponse

    @GET("lookup")
    suspend fun getItemsDetail(@Query("id") appIdList: String): ITunesItemDetailResponse

}