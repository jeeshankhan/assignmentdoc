package com.video.docquityandroidassignment.base

import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.video.docquityandroidassignment.ui.utils.Progressbar
import com.video.docquityandroidassignment.R
import com.video.docquityandroidassignment.databinding.ActivityMainBaseBinding
import kotlinx.coroutines.launch

open class MainBaseActivity : AppCompatActivity(){

    private  var progressBar: Dialog?= null
    val baseViewModel: BaseViewModel by viewModels()
    private lateinit var binding: ActivityMainBaseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initProgressBar()
    }






    private fun initProgressBar() {
        progressBar = Progressbar.builder(this)
    }

    fun hideLoading() {
        try {
            if (progressBar != null && progressBar!!.isShowing) {
                progressBar?.dismiss()
            }
        } catch (e: IllegalArgumentException) {
            // Handle or log the exception, if necessary
        } catch (e: WindowManager.BadTokenException) {
            // Handle or log the exception, if necessary
        } finally {
            // Optional cleanup
        }
    }


    fun showLoading() {
        try {
            if (!isFinishing && progressBar != null) {
                lifecycleScope.launch {
                    if (!progressBar!!.isShowing) {
                        progressBar?.show()
                    }
                }
            }
        } catch (e: IllegalArgumentException) {
            // Handle or log the exception if necessary
        } catch (e: WindowManager.BadTokenException) {
            // Handle or log the exception if necessary
        } finally {
            // Optional cleanup
        }
    }




    private fun checkNetwork(): Boolean {
        val connManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//          val networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        return true
    }

    open fun showError(msg: String?, view: View) {
        hideLoading()
        msg?.let {
            val snackbar = Snackbar.make(view, it, Snackbar.LENGTH_LONG)
                .setTextColor(view.context.getColor(R.color.white))
                .setBackgroundTint(view.context.getColor(R.color.light_pink))

            // Apply bottom margin (e.g., 32dp)
            val snackbarView = snackbar.view
            val params = snackbarView.layoutParams as ViewGroup.MarginLayoutParams
            val marginInDp = 100
            val marginInPx = (marginInDp * view.resources.displayMetrics.density).toInt()
            params.setMargins(params.leftMargin, params.topMargin, params.rightMargin, marginInPx)
            snackbarView.layoutParams = params
            snackbar.show()
        }
    }

    open fun showErrorToast(msg: String?,context: Context) {
        msg?.let {
            Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
        }
    }
}