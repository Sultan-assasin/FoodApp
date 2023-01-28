package com.sultan.foodapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sultan.foodapp.activites.MainActivity
import com.sultan.foodapp.activites.MealActivity
import com.sultan.foodapp.adapters.FavoritesAdapter
import com.sultan.foodapp.databinding.FragmentFavoritesBinding
import com.sultan.foodapp.viewModel.HomeViewModel


class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var favoritesAdapter: FavoritesAdapter
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeFavorites()

        itemTouchHelp()
        favoritesClick()
    }

    private fun prepareRecyclerView() {
        favoritesAdapter = FavoritesAdapter()
        binding.favRecView.apply {
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
            adapter = favoritesAdapter
        }
    }

    private fun observeFavorites() {
        viewModel.observeFavoritesMealsLiveData()
            .observe(requireActivity()) { meals ->
                meals.forEach { _ ->
                    favoritesAdapter.differ.submitList(meals)
                }
            }
    }

    private fun favoritesClick() {
        favoritesAdapter.onItemClick = { meal ->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(HomeFragment.MEAL_ID, meal.idMeal)
            intent.putExtra(HomeFragment.MEAL_NAME, meal.strMeal)
            intent.putExtra(HomeFragment.MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun itemTouchHelp() {
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val meal = favoritesAdapter.differ.currentList[position]
                viewModel.deleteMeal(meal)

                Snackbar.make(requireView(), "Meal deleted", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.favRecView)
    }
}