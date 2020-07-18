package tam.howard.app_listing.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import tam.howard.app_listing.AppListingApplication
import tam.howard.app_listing.ui.listing.di.ListingModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ApplicationModule::class,
        ListingModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<AppListingApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }
}