package tam.howard.app_listing.ui.listing

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
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
    }
}