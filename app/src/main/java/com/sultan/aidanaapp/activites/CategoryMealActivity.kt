package com.sultan.aidanaapp.activites

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.sultan.aidanaapp.adapters.CategoryMealsAdapter
import com.sultan.aidanaapp.databinding.ActivityCategoryMealBinding
import com.sultan.aidanaapp.fragments.HomeFragment
import com.sultan.aidanaapp.viewModel.CategoryMealsViewModel

class CategoryMealActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryMealBinding
    private lateinit var categoryMvvm: CategoryMealsViewModel
    private lateinit var categoryMealsAdapter: CategoryMealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoryMvvm = ViewModelProviders.of(this)[CategoryMealsViewModel::class.java]
        categoryMvvm.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)
        categoryMvvm.observeMealsLiveData().observe(this) { mealList ->
            binding.tvCategoryCount.text = mealList.size.toString()
            categoryMealsAdapter.setMealList(mealList)

        }
        prepareRecyclerView()
        categoryClick()
    }

    private fun prepareRecyclerView() {
        categoryMealsAdapter = CategoryMealsAdapter()
        binding.rvMeals.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = categoryMealsAdapter
        }
    }

    private fun categoryClick() {
        val intent = Intent(applicationContext, MealActivity::class.java)
        categoryMealsAdapter.onItemClick = { meal ->
            intent.putExtra(HomeFragment.MEAL_ID, meal.idMeal)
            intent.putExtra(HomeFragment.MEAL_NAME, meal.strMeal)
            intent.putExtra(HomeFragment.MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }
}