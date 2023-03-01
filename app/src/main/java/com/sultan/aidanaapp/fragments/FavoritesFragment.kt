package com.sultan.aidanaapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.sultan.aidanaapp.R
import com.sultan.aidanaapp.activites.MainActivity
import com.sultan.aidanaapp.activites.MealActivity
import com.sultan.aidanaapp.adapters.MealsAdapter
import com.sultan.aidanaapp.databinding.FragmentFavoritesBinding
import com.sultan.aidanaapp.viewModel.HomeViewModel


class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var favoritesAdapter: MealsAdapter
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

        btnBuy()
    }

    private fun btnBuy() {
        binding.buttonClick.setOnClickListener {
            if(favoritesAdapter.differ.currentList.isEmpty()){
                Toast.makeText(activity, "Add products to buy", Toast.LENGTH_SHORT).show()
            }
            else{
                val list = favoritesAdapter.differ.currentList
                viewModel.clean(list) // and not forget this code
                Toast.makeText(activity, "Successfully bought", Toast.LENGTH_SHORT).show()
                favoritesAdapter.differ.submitList(emptyList()) // this code helped to clean adapter
                openMyFragment()
            }

        }
    }

    private fun prepareRecyclerView() {
        favoritesAdapter = MealsAdapter()
        binding.favRecView.apply {
//            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
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

    private fun openMyFragment() {
        val fragment = AddressFragment()
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.host_fragment, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }
}