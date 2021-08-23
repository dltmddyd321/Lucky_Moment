package com.example.luckypoint.ladder

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import com.example.luckypoint.R
import com.example.luckypoint.databinding.ActivityLadderReadyFragmentBinding

class LadderReadyFragment : Fragment() {

    private lateinit var binding: ActivityLadderReadyFragmentBinding
    private var listener: GhostLegInputListener? = null
    private var playerCount: Int = MIN_MEMBER_COUNT
    private lateinit var playerListAdapter: LadderAdapter
    private lateinit var resultListAdapter: LadderAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityLadderReadyFragmentBinding.inflate(inflater, container, false)

        initPlayListView()

        binding.btnPlay.setOnClickListener {
            listener?.onCompleteGhostLegInput(
                Integer.parseInt(binding.memberCounter.tvNum.text.toString()),
                (playerListAdapter.dataList.map { data -> data.name }).toTypedArray(),
                (resultListAdapter.dataList.map { data -> data.name }).toTypedArray()
            )
        }
        return binding.root
    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun initPlayListView() {
        binding.memberCounter.tvTitle.text = "참가 인원"
        playerListAdapter = LadderAdapter(context!!, arrayListOf())
        resultListAdapter = LadderAdapter(context!!, arrayListOf())
        binding.playerListView.adapter = playerListAdapter
        binding.resultListView.adapter = resultListAdapter
        binding.memberCounter.tvTitle.text = "당첨 인원"

        binding.memberCounter.btnIncrease.setOnClickListener {
            if (playerCount < MAX_MEMBER_COUNT) {
                playerCount++
                updateView()
            }
        }
        binding.memberCounter.btnDecrease.setOnClickListener {
            if (playerCount > MIN_MEMBER_COUNT) {
                playerCount--
                updateView()
            }
        }

        updateView()
    }




    private fun updateView(beforeCount: Int = playerListAdapter.count) {
        binding.memberCounter.tvNum.text = playerCount.toString()
        if (beforeCount < playerCount) {
            for (i in beforeCount until playerCount) {
                playerListAdapter.addItem(Data(i + 1, "${i+1}번째 사람"))
                resultListAdapter.addItem(Data(i + 1, "${i+1}번째 결과"))
            }
        } else if (beforeCount > playerCount) {
            for (i in playerCount until beforeCount) {
                playerListAdapter.removeItem()
                resultListAdapter.removeItem()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is GhostLegInputListener) {
            listener = context
        }
    }

    companion object {
        const val MAX_MEMBER_COUNT: Int = 8
        const val MIN_MEMBER_COUNT: Int = 2
    }
}

interface GhostLegInputListener {
    fun onCompleteGhostLegInput(totalCount: Int, playerList: Array<String>, resultList: Array<String>)
}