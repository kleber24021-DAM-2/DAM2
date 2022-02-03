package com.example.seriesfollower.ui.search

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.seriesfollower.GeneralConstants
import com.example.seriesfollower.databinding.FragmentSearchBinding
import com.example.seriesfollower.domain.model.queryresult.OwnResult
import com.example.seriesfollower.domain.model.queryresult.ResultType
import com.example.seriesfollower.ui.utils.query.RVPseudoPaginator
import com.example.seriesfollower.ui.utils.query.ResultAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val searchViewModel : SearchViewModel by viewModels()
    private lateinit var resultAdapter:ResultAdapter

    private var actualPage = 1
    private var limite = 1
    private var actualQuery = GeneralConstants.EMPTY_STRING

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerViewAdapter()
        setListeners()
        setObservers()
        binding.searchView.requestFocus()
        binding.searchView.requestFocusFromTouch()
    }

    private fun setRecyclerViewAdapter(){
        resultAdapter =
            ResultAdapter(binding.root.context, object : ResultAdapter.OwnResultActions{
                override fun showResultDetails(item: OwnResult) {
                    showItemDetails(item)
                }
            })
        with(binding.rvResults){
            layoutManager = GridLayoutManager(activity, 2)
            setHasFixedSize(true)
            adapter = resultAdapter
        }
    }

    private fun setListeners(){
        binding.rvResults.addOnScrollListener(object : RVPseudoPaginator(binding.rvResults){
            override val isLastPage: Boolean
                get() = actualPage == limite

            override fun loadMore(start: Long, count: Long) {
                searchViewModel.handleEvent(SearchContract.Event.GetSearchResults(actualQuery,start.toInt()))
            }
        })
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(inputText: String?): Boolean {
                inputText?.let{
                    searchViewModel.handleEvent(SearchContract.Event.GetSearchResults(it, actualPage))
                    actualQuery = it
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let{
                    if (it.isNotEmpty() || it.isNotBlank()){
                        searchViewModel.handleEvent(SearchContract.Event.GetSearchResults(it, actualPage))
                        actualQuery = it
                    }
                }
                return true
            }
        })
        binding.searchView.setOnQueryTextFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                view?.let {
                    showInputMethod(it)
                }
            }
        }
    }

    private fun setObservers(){
        lifecycleScope.launch{
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                searchViewModel.uiState.collect{state ->
                    binding.loading.visibility =
                        if (state.isLoading){
                            View.VISIBLE
                        }else{
                            View.GONE
                        }
                    state.queryInfo?.let {
                        actualPage = it.page
                        limite = it.totalPages
                        resultAdapter.submitList(it.results)
                    }
                    state.error?.let {
                        Toast.makeText(binding.root.context, it, Toast.LENGTH_SHORT).show()
                        searchViewModel.handleEvent(SearchContract.Event.ErrorMostrado)
                    }
                }
            }
        }
    }

    private fun showItemDetails(item:OwnResult){
        val action = if (item.resultType == ResultType.MOVIE){
            SearchFragmentDirections.actionSearchFragmentToMovieDetailFragment(item.id)
        }else{
            SearchFragmentDirections.actionSearchFragmentToSeriesDetailFragment(item.id)
        }
        findNavController().navigate(action)
    }

    private fun showInputMethod(view : View){
        val inputManager = binding.root.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.showSoftInput(view, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}