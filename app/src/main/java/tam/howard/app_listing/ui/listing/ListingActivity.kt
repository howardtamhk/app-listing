package tam.howard.app_listing.ui.listing

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_listing.*
import kotlinx.android.synthetic.main.content_listing.*
import tam.howard.app_listing.R
import tam.howard.app_listing.base.BaseActivity
import tam.howard.app_listing.databinding.ActivityListingBinding

class ListingActivity : BaseActivity<ActivityListingBinding, ListingViewModel>(
    R.layout.activity_listing,
    ListingViewModel::class.java
) {

    private val listingAdapter: ListingAdapter by lazy {
        ListingAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpView()
        subscribeLiveData()

    }

    private fun setUpView() {
        setSupportActionBar(listingToolbar)
        listingRecyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
        listingRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        listingRecyclerView.adapter = this.listingAdapter

        listingRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                if (lastPosition == this@ListingActivity.listingAdapter.itemCount - 3) {
                    this@ListingActivity.viewModel.loadNextPage()
                }
            }
        })
    }

    private fun subscribeLiveData() {
        this.viewModel.listingRecyclerViewModelListLiveData.observe(this, Observer {
            this.listingAdapter.submitList(it)
        })

        this.viewModel.apiError.observe(this, Observer {
            Snackbar.make(coordinatorListing, R.string.api_error_msg, Snackbar.LENGTH_SHORT).show()
        })
    }

}