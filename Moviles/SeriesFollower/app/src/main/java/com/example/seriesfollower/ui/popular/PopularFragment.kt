package com.example.seriesfollower.ui.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.seriesfollower.databinding.FragmentPopularBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PopularFragment : Fragment() {

    private var _binding: FragmentPopularBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TrendingViewModel by viewModels()
    private lateinit var resultAdapter: ResultAdapter

    private var actualPage = 0
    private var limite = 1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPopularBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resultAdapter =
            ResultAdapter(binding.root.context, object : ResultAdapter.OwnResultActions {
                override fun makeFavorite(movieId: Int) {
                    TODO("Not yet implemented")
                }
            })
        with(binding.rvMovies) {
            layoutManager = GridLayoutManager(activity, 2)
            setHasFixedSize(true)
            adapter = resultAdapter
        }

        viewModel.results.observe(this, {
            actualPage = it.page
            limite = it.totalPages
            resultAdapter.submitList(it.results)
            binding.rvMovies.layoutManager?.scrollToPosition(1)
        })
        viewModel.error.observe(this, { error ->
            Toast.makeText(binding.root.context, error, Toast.LENGTH_SHORT).show()
        })
        viewModel.getTrendingResults(1)
        binding.rvMovies.addOnScrollListener(object: RVPseudoPaginator(binding.rvMovies){
            override val isLastPage: Boolean
                get() = actualPage == limite

            override fun loadMore(start: Long, count: Long) {
                viewModel.getTrendingResults(start.toInt())
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}