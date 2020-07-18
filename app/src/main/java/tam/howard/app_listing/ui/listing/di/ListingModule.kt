package tam.howard.app_listing.ui.listing.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import tam.howard.app_listing.di.ViewModelKey
import tam.howard.app_listing.di.ViewModelModule
import tam.howard.app_listing.ui.listing.ListingActivity
import tam.howard.app_listing.ui.listing.ListingViewModel

@Module
abstract class ListingModule {
    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun listingActivity(): ListingActivity

    @Binds
    @IntoMap
    @ViewModelKey(ListingViewModel::class)
    abstract fun bindListingViewModel(viewModel: ListingViewModel): ViewModel
}