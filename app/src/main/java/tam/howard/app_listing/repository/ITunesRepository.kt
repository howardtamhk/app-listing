package tam.howard.app_listing.repository

import tam.howard.app_listing.provider.ITunesApiProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ITunesRepository @Inject constructor(private val iTunesApiProvider: ITunesApiProvider) {


    suspend fun getTopFreeApplicationsList() = this.iTunesApiProvider.getTopFreeApplicationsList()
}