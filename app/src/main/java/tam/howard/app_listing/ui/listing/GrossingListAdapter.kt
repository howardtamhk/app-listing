package tam.howard.app_listing.ui.listing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import coil.api.load
import coil.transform.RoundedCornersTransformation
import tam.howard.app_listing.R
import tam.howard.app_listing.base.BaseViewHolder
import tam.howard.app_listing.databinding.ViewHolderGrossingListItemBinding
import tam.howard.app_listing.model.ITunesItemDetail

class GrossingListAdapter(private val itemDetails: ArrayList<ITunesItemDetail>) :
    ListAdapter<ITunesItemDetail, GrossingListAdapter.GrossingListItemViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrossingListItemViewHolder {
        val binding = DataBindingUtil.inflate<ViewHolderGrossingListItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.view_holder_grossing_list_item,
            parent,
            false
        )
        return GrossingListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GrossingListItemViewHolder, position: Int) {
        holder.bind(itemDetails[position])
    }

    override fun getItemCount(): Int = itemDetails.size

    class GrossingListItemViewHolder(binding: ViewHolderGrossingListItemBinding) :
        BaseViewHolder<ViewHolderGrossingListItemBinding>(binding) {

        fun bind(item: ITunesItemDetail) {
            binding.item = item
            binding.imageViewGrossingItemIcon.load(item.iconUrl) {
                transformations(RoundedCornersTransformation(20f))
            }
        }
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<ITunesItemDetail>() {
            override fun areItemsTheSame(
                oldItem: ITunesItemDetail,
                newItem: ITunesItemDetail
            ): Boolean = oldItem.iconUrl == newItem.iconUrl

            override fun areContentsTheSame(
                oldItem: ITunesItemDetail,
                newItem: ITunesItemDetail
            ): Boolean = oldItem == newItem

        }

    }
}