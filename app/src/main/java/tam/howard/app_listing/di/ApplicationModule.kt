package tam.howard.app_listing.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import tam.howard.app_listing.BuildConfig
import tam.howard.app_listing.provider.ITunesApiProvider
import tam.howard.app_listing.util.config.EnvironmentConstant
import javax.inject.Singleton


@Module()
object ApplicationModule {

    @UnstableDefault
    @JvmStatic
    @Singleton
    @Provides
    fun providesITunesApiProvider(): ITunesApiProvider {
        val okhttpClient = OkHttpClient.Builder()
        if (BuildConfig.SHOW_LOG) {
            okhttpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }
        return Retrofit.Builder()
            .baseUrl(EnvironmentConstant.API_BASE_URL)
            .client(okhttpClient.build())
            .addConverterFactory(
                Json(
                    configuration = JsonConfiguration(
                        ignoreUnknownKeys = true,
                        prettyPrint = true
                    )
                ).asConverterFactory(
                    "application/json".toMediaType()
                )
            )
            .build()
            .create(ITunesApiProvider::class.java)
    }

}