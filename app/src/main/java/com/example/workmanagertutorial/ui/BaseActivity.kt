package com.example.workmanagertutorial.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


abstract class BaseActivity<T : BaseViewModel> : DaggerAppCompatActivity() {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    protected abstract val viewModelClass: Class<T>

    protected val viewModel: T by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(viewModelClass)
    }

    protected abstract fun observeLiveData()

    protected abstract val layoutId: Int

    protected var binding: ViewDataBinding? = null

    open var useDataBinding = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (useDataBinding && layoutId != -1)
            binding = DataBindingUtil.setContentView(this, layoutId)
        else if (layoutId != -1)
            setContentView(layoutId)
        AndroidInjection.inject(this)

        observeAllData()
    }

    private fun observeAllData() {
        observeLiveData()
    }


}