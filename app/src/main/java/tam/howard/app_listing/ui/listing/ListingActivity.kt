package tam.howard.app_listing.ui.listing

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_listing.*
import tam.howard.app_listing.R
import tam.howard.app_listing.base.BaseActivity
import tam.howard.app_listing.databinding.ActivityListingBinding

class ListingActivity : BaseActivity<ActivityListingBinding, ListingViewModel>(
    R.layout.activity_listing,
    ListingViewModel::class.java
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(listingToolbar)

        this.viewModel.reload()
    }
}