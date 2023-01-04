package com.sultan.foodapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sultan.foodapp.pojo.MealsByCarygoryList
import com.sultan.foodapp.pojo.MealsByCategory
import com.sultan.foodapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel : ViewModel(){
    val mealsLiveData = MutableLiveData<List<MealsByCategory>>()
    fun getMealsByCategory(categoryName : String){
        RetrofitInstance.api.getMealsByCategory(categoryName).enqueue(object :
            Callback<MealsByCarygoryList>{
            override fun onResponse(
                call: Call<MealsByCarygoryList>,
                response: Response<MealsByCarygoryList>
            ) {
                response.body()?.let { mealList ->
                    mealsLiveData.postValue(mealList.meals)
                }
            }

            override fun onFailure(call: Call<MealsByCarygoryList>, t: Throwable) {
                Log.d("CategoryMealsViewModel", t.message.toString())
            }

        })
    }
    fun observeMealsLiveData() : LiveData<List<MealsByCategory>>{
        return mealsLiveData
    }
}