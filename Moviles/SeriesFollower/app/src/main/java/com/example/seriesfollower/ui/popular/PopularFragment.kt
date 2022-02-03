package com.example.seriesfollower.ui.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.seriesfollower.databinding.FragmentPopularBinding
import com.example.seriesfollower.domain.model.queryresult.OwnResult
import com.example.seriesfollower.domain.model.queryresult.ResultType
import com.example.seriesfollower.ui.utils.query.RVPseudoPaginator
import com.example.seriesfollower.ui.utils.query.ResultAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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
        viewModel.handleEvent(PopularContract.Event.PedirDatos(1))

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
                viewModel.handleEvent(PopularContract.Event.PedirDatos(start.toInt()))
            }

        })
    }

    private fun setObservers() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    binding.loading.visibility =
                        if (state.isLoading){
                            View.VISIBLE
                        }else{
                            View.GONE
                        }
                    state.favoriteItems?.let {
                        resultAdapter.submitList(it.results)
                        actualPage = it.page
                        limite = it.totalPages
                    }
                    state.error?.let {
                        Toast.makeText(binding.root.context, it, Toast.LENGTH_SHORT).show()
                        viewModel.handleEvent(PopularContract.Event.ErrorMostrado)
                    }
                }
            }
        }
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