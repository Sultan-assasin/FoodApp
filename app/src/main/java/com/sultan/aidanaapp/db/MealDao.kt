package com.sultan.aidanaapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sultan.aidanaapp.pojo.Meal

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