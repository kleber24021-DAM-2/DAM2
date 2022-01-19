package com.example.seriesfollower.ui.favorites

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.view.ActionMode
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.seriesfollower.R
import com.example.seriesfollower.databinding.FragmentFavoritesBinding
import com.example.seriesfollower.domain.model.favorite.FavoriteItem
import com.example.seriesfollower.domain.model.queryresult.ResultType
import com.example.seriesfollower.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private lateinit var favAdapter: FavAdapter

    private val favViewModel: FavoritesViewModel by viewModels()

    private lateinit var actionMode: ActionMode

    private val callback by lazy {
        configContextBar()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerViewsAdapter()
        setObservers()
        val touchHelper = ItemTouchHelper(favAdapter.swipeGesture)
        touchHelper.attachToRecyclerView(binding.rvFavMovies)
        favViewModel.getAllFavs()
    }

    private fun setRecyclerViewsAdapter() {

        favAdapter =
            FavAdapter(binding.root.context, object : FavAdapter.FavActions {
                override fun showFavDetails(item: FavoriteItem) {
                    showItemDetails(item)
                }

                override fun isItemSelected(item: FavoriteItem): Boolean =
                    favViewModel.isFavSelected(item)

                override fun selectItem(item: FavoriteItem) {
                    favViewModel.addFavSelected(item)
                }

                override fun deselectItem(item: FavoriteItem) {
                    favViewModel.delFavSelected(item)
                }

                override fun noSelectedItems(): Boolean =
                    favViewModel.noSelectedItems()

                override fun startSelectMode() {
                    (requireActivity() as MainActivity)
                        .startSupportActionMode(callback)?.let {
                            actionMode = it
                        }
                }

                override fun quitSelectMode() {
                    favViewModel.emptySelectedList()
                    actionMode.finish()
                }

                override fun deleteFav(item: FavoriteItem) {
                    favViewModel.deleteFavorite(item)
                }

                override fun changeBarNumber() {
                    actionMode.title = favViewModel.numSelectedItems().toString()
                }

            })
        with(binding.rvFavMovies) {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = favAdapter
        }
    }

    private fun showItemDetails(item: FavoriteItem) {
        val action = if (item.itemType == ResultType.MOVIE) {
            FavoritesFragmentDirections.actionFragmentFavoritesToMovieDetailFragment(item.id)
        } else {
            FavoritesFragmentDirections.actionFragmentFavoritesToSeriesDetailFragment(item.id)
        }
        findNavController().navigate(action)
    }

    private fun setObservers() {
        favViewModel.favorites.observe(this, {
            favAdapter.submitList(it)
        })
        favViewModel.error.observe(this, {
            Toast.makeText(binding.root.context, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun configContextBar() = object : ActionMode.Callback {
        override fun onCreateActionMode(p0: ActionMode?, p1: Menu?): Boolean {
            requireActivity().menuInflater.inflate(R.menu.delete_menu, p1)
            return true
        }

        override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean {
            return false
        }

        override fun onActionItemClicked(p0: ActionMode?, p1: MenuItem?): Boolean {
            return when (p1?.itemId) {
                R.id.btnDelete -> {
                    favViewModel.delAllSelectedItems()
                    actionMode.finish()
                    true
                }
                else -> false
            }
        }

        override fun onDestroyActionMode(p0: ActionMode?) {
            favAdapter.resetSelectMode()
        }

    }
}