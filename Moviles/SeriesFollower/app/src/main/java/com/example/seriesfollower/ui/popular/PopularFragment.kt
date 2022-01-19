package com.example.seriesfollower.ui.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.seriesfollower.databinding.FragmentPopularBinding
import com.example.seriesfollower.domain.model.queryresult.OwnResult
import com.example.seriesfollower.domain.model.queryresult.ResultType
import com.example.seriesfollower.ui.utils.query.RVPseudoPaginator
import com.example.seriesfollower.ui.utils.query.ResultAdapter
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

        setRecyclerViewAdapter()
        setObservers()
        setListeners()
        viewModel.getTrendingResults(1)

    }

    private fun setRecyclerViewAdapter() {
        resultAdapter =
            ResultAdapter(binding.root.context, object : ResultAdapter.OwnResultActions {
                override fun showResultDetails(item: OwnResult) {
                    showItemDetails(item)
                }
            })
        with(binding.rvMovies) {
            layoutManager = GridLayoutManager(activity, 2)
            setHasFixedSize(true)
            adapter = resultAdapter
        }
    }

    private fun setListeners() {
        binding.rvMovies.addOnScrollListener(object : RVPseudoPaginator(binding.rvMovies) {
            override val isLastPage: Boolean
                get() = actualPage == limite

            override fun loadMore(start: Long, count: Long) {
                viewModel.getTrendingResults(start.toInt())
            }

        })
    }

    private fun setObservers() {
        viewModel.results.observe(this, {
            actualPage = it.page
            limite = it.totalPages
            resultAdapter.submitList(it.results)
            binding.rvMovies.layoutManager?.scrollToPosition(1)
        })
        viewModel.error.observe(this, { error ->
            Toast.makeText(binding.root.context, error, Toast.LENGTH_SHORT).show()
        })
    }

    private fun showItemDetails(item: OwnResult) {
        val action = if (item.resultType == ResultType.MOVIE) {
            PopularFragmentDirections.actionPopularFragmentToMovieDetailFragment(item.id)
        } else {
            PopularFragmentDirections.actionPopularFragmentToSeriesDetailFragment(item.id)
        }
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}