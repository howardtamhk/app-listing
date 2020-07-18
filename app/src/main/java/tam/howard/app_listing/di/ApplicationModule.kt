package tam.howard.app_listing.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import tam.howard.app_listing.provider.ITunesApiProvider
import tam.howard.app_listing.util.config.EnvironmentConstant
import javax.inject.Singleton


@Module()
object ApplicationModule {

    @JvmStatic
    @Singleton
    @Provides
    fun providesITunesApiProvider(): ITunesApiProvider {
        return Retrofit.Builder()
            .baseUrl(EnvironmentConstant.API_BASE_URL)
            .build()
            .create(ITunesApiProvider::class.java)
    }

}