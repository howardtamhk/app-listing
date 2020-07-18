package tam.howard.app_listing.base

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import tam.howard.app_listing.BR
import javax.inject.Inject

abstract class BaseActivity<B : ViewDataBinding, VM : BaseViewModel>(
    @LayoutRes private val layoutId: Int,
    private val vmClass: Class<VM>
) : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    protected val viewModel: VM by lazy {
        ViewModelProvider(this, this.viewModelFactory)[this.vmClass]
    }
    private lateinit var binding: B

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = DataBindingUtil.setContentView(this, this.layoutId)
        this.binding.lifecycleOwner = this
        this.binding.setVariable(BR.vm, this.viewModel)

    }
}