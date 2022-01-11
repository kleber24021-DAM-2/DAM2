package com.example.seriesfollower.ui.trending

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.seriesfollower.R
import com.example.seriesfollower.databinding.TrendingFragmentBinding

class TrendingFragment : Fragment() {

    private var _binding : TrendingFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: TrendingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = TrendingFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //TODO use viewmodel
    }

}