package com.sultan.aidanaapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sultan.aidanaapp.databinding.MealItemBinding
import com.sultan.aidanaapp.pojo.Meal
import com.sultan.aidanaapp.pojo.MealsByCategory

class CategoryMealsAdapter : RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViewModel>() {
    private var mealList = ArrayList<Meal>()
    lateinit var onItemClick : (Meal)-> Unit

    inner class CategoryMealsViewModel(val binding: MealItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun setMealList(mealList: List<Meal>) {
        this.mealList = mealList as ArrayList<Meal>
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewModel {
        return CategoryMealsViewModel(
            MealItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryMealsViewModel, position: Int) {
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb)
            .into(holder.binding.imgMeal)
        holder.binding.tvMealName.text = mealList[position].strMeal

        holder.binding.imgMeal.setOnClickListener {

            onItemClick!!.invoke(mealList[position])
        }

    }

    override fun getItemCount(): Int {
        return mealList.size
    }
}