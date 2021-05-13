package com.soma_quokka.dreamtree.presentation.main.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.soma_quokka.dreamtree.R
import com.soma_quokka.dreamtree.data.model.CurrentProgress
import com.warkiz.widget.IndicatorSeekBar
import com.warkiz.widget.OnSeekChangeListener
import com.warkiz.widget.SeekParams


class BottomSheetDialog: BottomSheetDialogFragment() {
    private var onMeterSetListener: OnMeterSetListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onMeterSetListener = context as OnMeterSetListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.modal_bottom_sheet_layout, container, false)
        val seekBar = view.findViewById<IndicatorSeekBar>(R.id.seekbar)

        seekBar.setProgress(CurrentProgress.currentProgress)
        seekBar.onSeekChangeListener = object : OnSeekChangeListener {
            override fun onSeeking(seekParams: SeekParams) {
            }

            override fun onStartTrackingTouch(seekBar: IndicatorSeekBar) {
            }

            @SuppressLint("SetTextI18n")
            override fun onStopTrackingTouch(seekBar: IndicatorSeekBar) {
                onMeterSetListener?.onMeterSetListener(seekBar.progress.toDouble())
                CurrentProgress.currentProgress = seekBar.progressFloat
                dismiss()
            }
        }
        return view
    }

    override fun onDetach() {
        super.onDetach()
        onMeterSetListener = null
    }

}