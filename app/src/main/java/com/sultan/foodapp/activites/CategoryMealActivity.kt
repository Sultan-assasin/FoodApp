package com.sultan.foodapp.activites

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.sultan.foodapp.adapters.CategoryMealsAdapter
import com.sultan.foodapp.databinding.ActivityCategoryMealBinding
import com.sultan.foodapp.fragments.HomeFragment
import com.sultan.foodapp.viewModel.CategoryMealsViewModel

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
    }

    private fun prepareRecyclerView() {
        categoryMealsAdapter = CategoryMealsAdapter()
        binding.rvMeals.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = categoryMealsAdapter
        }
    }
}