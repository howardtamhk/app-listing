package tam.howard.app_listing.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<B : ViewDataBinding>(protected val binding: B) :
    RecyclerView.ViewHolder(binding.root)