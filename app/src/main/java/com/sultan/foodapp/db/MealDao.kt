package com.sultan.foodapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sultan.foodapp.pojo.Meal
import com.sultan.foodapp.pojo.MealsByCategoryList

@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(meal : Meal)

    @Delete
    suspend fun delete(meal: Meal)

    @Query("SELECT * FROM mealInformation")
    fun getAllMeals() : LiveData<List<Meal>>

    @Delete
    suspend fun clean(list: MutableList<Meal>)

}