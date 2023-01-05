package com.sultan.foodapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.sultan.foodapp.R
import com.sultan.foodapp.activites.CategoryMealActivity
import com.sultan.foodapp.activites.MainActivity
import com.sultan.foodapp.activites.MealActivity
import com.sultan.foodapp.adapters.CategoryAdapter
import com.sultan.foodapp.adapters.MostPopularAdapter
import com.sultan.foodapp.databinding.FragmentHomeBinding
import com.sultan.foodapp.fragments.bottomsheet.MealBottomSheetFragment
import com.sultan.foodapp.pojo.Meal
import com.sultan.foodapp.pojo.MealsByCategory
import com.sultan.foodapp.viewModel.HomeViewModel


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularIItemsAdapter: MostPopularAdapter
    private lateinit var categoriesAdapter: CategoryAdapter

    companion object {
        const val MEAL_ID = "com.sultan.foodapp.fragments.idMeal"
        const val MEAL_NAME = "com.sultan.foodapp.fragments.nameMeal"
        const val MEAL_THUMB = "com.sultan.foodapp.fragments.thumbMeal"
        const val CATEGORY_NAME = "com.sultan.foodapp.fragments.categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //homeMvvm = ViewModelProvider(this)[HomeViewModel::class.java] // fragment lifecycle
        viewModel = (activity as MainActivity).viewModel // main activity lifecycle
        popularIItemsAdapter = MostPopularAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemsRecyclerView()

        viewModel.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()

        viewModel.getPopularItems()
        observePopularItemsLiveData()
        onPopularItemClick()

        viewModel.getCategories()
        observeCategoryLiveData()
        prepareCategoriesRecyclerView()

        onCategoryClick()
        onPopularItemLongClick()

        onSearchIconClick()
    }

    private fun onSearchIconClick() {
        binding.imgSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun onPopularItemLongClick() {
        popularIItemsAdapter.onLongClickListener = { meal ->
            val mealBottomSheetFragment = MealBottomSheetFragment.newInstance(meal.idMeal)
            mealBottomSheetFragment.show(childFragmentManager,"Meal Info")
        }
    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = { category ->
            val intent = Intent(activity, CategoryMealActivity::class.java)
            intent.putExtra(CATEGORY_NAME, category.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter = CategoryAdapter()
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun observeCategoryLiveData() {
        viewModel.observeCategoryLiveData().observe(viewLifecycleOwner) { categories ->
            categories.forEach { _ ->
                categoriesAdapter.setCategoryList(categories)
            }
        }
    }

    private fun onPopularItemClick() {
        popularIItemsAdapter.onItemClick = { meal ->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_NAME, meal.strMeal)
            intent.putExtra(MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularItemsRecyclerView() {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularIItemsAdapter
        }
    }

    private fun observePopularItemsLiveData() {
        viewModel.observePopularItemsLiveData().observe(
            viewLifecycleOwner
        ) { mealList ->
            popularIItemsAdapter.setMeals(mealList = mealList as ArrayList<MealsByCategory>)
        }
    }

    private fun onRandomMealClick() {
        binding.randomMeal.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observerRandomMeal() =
        viewModel.observeRandomMealLiveData().observe(
            viewLifecycleOwner
        ) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal!!.strMealThumb)
                .into(binding.imgRandomMeal)
            this.randomMeal = meal

        }
}