package tam.howard.app_listing.ui.listing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import coil.api.load
import tam.howard.app_listing.R
import tam.howard.app_listing.base.BaseViewHolder
import tam.howard.app_listing.databinding.ViewHolderFreeAppItemBinding
import tam.howard.app_listing.databinding.ViewHolderGrossingAppListingBinding
import tam.howard.app_listing.ui.listing.model.ListingRecyclerViewModel

class ListingAdapter(private val itemModels: ArrayList<ListingRecyclerViewModel> = arrayListOf()) :
    ListAdapter<ListingRecyclerViewModel, ListingAdapter.ListingBaseViewHolder<out ViewDataBinding>>(
        DIFF_UTIL
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListingBaseViewHolder<out ViewDataBinding> {
        when (viewType) {
            R.layout.view_holder_grossing_app_listing -> {
                val binding = DataBindingUtil.inflate<ViewHolderGrossingAppListingBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.view_holder_grossing_app_listing,
                    parent,
                    false
                )
                return GrossingAppListingViewHolder(binding)
            }
            else -> {
                val binding = DataBindingUtil.inflate<ViewHolderFreeAppItemBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.view_holder_free_app_item,
                    parent,
                    false
                )
                return FreeAppItemViewHolder(binding)
            }

        }
    }

    override fun onBindViewHolder(
        holder: ListingBaseViewHolder<out ViewDataBinding>,
        position: Int
    ) {
        holder.bind(itemModels[position], position)
    }


    override fun getItemViewType(position: Int): Int {
        return when (itemModels[position]) {
            is ListingRecyclerViewModel.GrossingList -> R.layout.view_holder_grossing_app_listing
            is ListingRecyclerViewModel.FreeApplicationItem -> R.layout.view_holder_free_app_item
        }
    }

    override fun submitList(list: MutableList<ListingRecyclerViewModel>?) {
        this.itemModels.clear()
        this.itemModels.addAll(list ?: arrayListOf())
        super.submitList(list)
    }

    override fun getItemCount(): Int = this.itemModels.size


    abstract class ListingBaseViewHolder<B : ViewDataBinding>(binding: B) :
        BaseViewHolder<B>(binding) {
        abstract fun bind(item: ListingRecyclerViewModel, index: Int)
    }

    class GrossingAppListingViewHolder(binding: ViewHolderGrossingAppListingBinding) :
        ListingBaseViewHolder<ViewHolderGrossingAppListingBinding>(binding) {
        override fun bind(item: ListingRecyclerViewModel, index: Int) {
            val grossingListing = item as? ListingRecyclerViewModel.GrossingList
            grossingListing?.let {
                val list = it.list
                binding.recyclerViewGrossingList.layoutManager = LinearLayoutManager(
                    this.itemView.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )

                binding.recyclerViewGrossingList.adapter = GrossingListAdapter(list)

            }

        }

    }

    class FreeAppItemViewHolder(binding: ViewHolderFreeAppItemBinding) :
        ListingBaseViewHolder<ViewHolderFreeAppItemBinding>(binding) {
        override fun bind(item: ListingRecyclerViewModel, index: Int) {
            val freeItem = item as? ListingRecyclerViewModel.FreeApplicationItem
            freeItem?.let {
                binding.item = it.detail
                binding.rank = index.toString()
                binding.ratingBarFreeItem.rating = it.detail.rating
                binding.executePendingBindings()

                binding.imageViewFreeItemIcon.load(it.detail.iconUrl)
            }

        }

    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<ListingRecyclerViewModel>() {
            override fun areItemsTheSame(
                oldItem: ListingRecyclerViewModel,
                newItem: ListingRecyclerViewModel
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: ListingRecyclerViewModel,
                newItem: ListingRecyclerViewModel
            ): Boolean = oldItem == newItem
        }

    }
}