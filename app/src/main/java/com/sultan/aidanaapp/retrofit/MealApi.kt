package com.sultan.aidanaapp.retrofit


import com.sultan.aidanaapp.pojo.CategoryList
import com.sultan.aidanaapp.pojo.MealList
import com.sultan.aidanaapp.pojo.MealsByCategoryList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface MealApi {
    @GET("random.php")
    fun getRandomMeal() : Call<MealList>

    @GET("lookup.php")
    fun getMealDetails(@Query("i") id:String) : Call<MealList>

    @GET("filter.php")
    fun getPopularItems(@Query("c") categoryName : String) : Call<MealsByCategoryList>

    @GET("categories.php")
    fun getCategories() : Call<CategoryList>

    @GET("filter.php")
    fun getMealsByCategory(@Query("c") categoryName: String) : Call<MealsByCategoryList>

    @GET("search.php")
    fun searchMeals(@Query("s") searchQuery:String) : Call<MealList>

    @GET("search.php?")
    fun getMealByName(@Query("s") s:String):Call<MealList>

    @GET("filter.php")
    fun getDessertsByCategory(@Query("c") categoryName: String) : Call<MealList>
}