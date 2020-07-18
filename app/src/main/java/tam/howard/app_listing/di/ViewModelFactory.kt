package tam.howard.app_listing.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class ViewModelFactory @Inject constructor(private val viewModels: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        try {
            @Suppress("UNCHECKED_CAST")
            viewModels[modelClass]?.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }


}