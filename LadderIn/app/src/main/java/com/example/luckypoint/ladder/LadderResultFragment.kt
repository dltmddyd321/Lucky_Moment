package com.example.luckypoint.ladder

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.luckypoint.R
import com.example.luckypoint.databinding.ActivityLadderResultFragmentBinding

class LadderResultFragment : DialogFragment() {
    private lateinit var binding: ActivityLadderResultFragmentBinding
    private var listener: GhostLegResultListener? = null

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityLadderResultFragmentBinding.inflate(inflater, container, false)
        binding.result.text = arguments!!.getString(RESULT_KEY)
        binding.root.setOnClickListener { dismiss() }
        return binding.root
    }

    fun setListener(listener: GhostLegResultListener): LadderResultFragment {
        this.listener = listener
        return this
    }

    companion object {
        const val RESULT_KEY: String = "result"
    }
}

interface GhostLegResultListener {
    fun onCloseGhostLegResult()
}