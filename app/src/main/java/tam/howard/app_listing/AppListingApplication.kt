package tam.howard.app_listing

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import tam.howard.app_listing.di.DaggerApplicationComponent

class AppListingApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.factory().create(this)

    }
}