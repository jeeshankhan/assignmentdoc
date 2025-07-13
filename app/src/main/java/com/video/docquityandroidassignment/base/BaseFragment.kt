package com.video.docquityandroidassignment.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.video.docquityandroidassignment.R


open class BaseFragment : Fragment() {
    var mActivity: MainBaseActivity?= null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainBaseActivity) {
            this.mActivity = context
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        Progressbar.builder(requireContext())
    }

  open  fun showProgressBar(isShow: Boolean) {
        if (isShow) mActivity?.showLoading() else mActivity?.hideLoading()
    }

    open fun showError(msg: String?,view: View) {
        showProgressBar(false)
        msg?.let {
            Snackbar.make(view, it, Snackbar.LENGTH_LONG).setTextColor(resources.getColor(R.color.white,null))
                .setBackgroundTint(resources.getColor(R.color.black,null)).show()
        }
    }


}