package com.video.docquityandroidassignment.ui.utils

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.video.docquityandroidassignment.databinding.LayoutProgressBinding


object Progressbar {
    private var dialog: Dialog? = null

    fun builder(context: Context): Dialog? {
        val binding = binding(context)
        dialog = Dialog(context)
        dialog?.setCanceledOnTouchOutside(false)

        binding.clProgress.visibility = View.VISIBLE
        binding.root.isClickable = false
        binding.root.isFocusable = true
        dialog?.setContentView(binding.root)

        dialog?.window?.apply {
            // Set full-screen dimensions
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

            // Make background transparent
            setBackgroundDrawableResource(android.R.color.transparent)

            // Optional: Remove any default padding
            decorView.setPadding(0, 0, 0, 0)

            // Optional: Dim behind
            addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            setDimAmount(0.6f)
        }

        dialog?.create()
        return dialog
    }

    fun hide() {
        if (dialog?.isShowing == true) dialog?.dismiss()
    }

    fun show() {
        if (dialog?.isShowing == false) dialog?.show()
    }

    private fun binding(context: Context): LayoutProgressBinding =
        LayoutProgressBinding.inflate(LayoutInflater.from(context))
}
