package tam.howard.app_listing.provider

import retrofit2.http.GET
import retrofit2.http.Path
import tam.howard.app_listing.model.ITunesListingResponse

interface ITunesApiProvider {

    @GET("rss/topfreeapplications/limit={limit}/json")
    suspend fun getTopFreeApplicationsList(@Path("limit") limit: Int = 100): ITunesListingResponse

}