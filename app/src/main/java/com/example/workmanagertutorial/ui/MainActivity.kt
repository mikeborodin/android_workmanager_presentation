package com.example.workmanagertutorial.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.workmanagertutorial.R
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity<MainActivityViewModel>(), HasSupportFragmentInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    override val viewModelClass: Class<MainActivityViewModel> = MainActivityViewModel::class.java

    override fun supportFragmentInjector() = dispatchingAndroidInjector


    override val layoutId: Int = R.layout.activity_main


    override fun observeLiveData() {
        viewModel.status.observe(this, Observer {
            tvStatus.text = it
        })

        viewModel.image.observe(this, Observer {
            Glide.with(this)
                .load(it)
                .into(ivPhoto)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btnPhoto.setOnClickListener {
            openFileChooser()
        }
    }


    private fun openFileChooser() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        startActivityForResult(intent, FILE_REQUEST)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (requestCode == FILE_REQUEST && resultCode == Activity.RESULT_OK && intent != null) {
            try {
                val uri = intent.data ?: return

                viewModel.setImage(uri.toString())
                viewModel.uploadPhoto()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        super.onActivityResult(requestCode, resultCode, intent)
    }


    companion object {
        const val FILE_REQUEST = 123
    }

}
