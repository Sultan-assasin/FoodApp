package com.sultan.aidanaapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sultan.aidanaapp.databinding.SingleMealCardBinding
import com.sultan.aidanaapp.pojo.Meal

class MealRecyclerAdapter: RecyclerView.Adapter<MealRecyclerAdapter.MealViewHolder>() {

    private var mealList: List<Meal> = ArrayList()
    private lateinit var setOnMealClickListener: SetOnMealClickListener

    fun setCategoryList(mealList: List<Meal>) {
        this.mealList = mealList
        notifyDataSetChanged()
    }

    fun setOnMealClickListener(setOnMealClickListener: SetOnMealClickListener) {
        this.setOnMealClickListener = setOnMealClickListener
    }

    class MealViewHolder(val binding: SingleMealCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        return MealViewHolder(SingleMealCardBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.binding.apply {
            tvMealName.text = mealList[position].strMeal
            Glide.with(holder.itemView)
                .load(mealList[position].strMealThumb)
                .into(imgMeal)
        }

        holder.itemView.setOnClickListener {
            setOnMealClickListener.setOnClickListener(mealList[position])
        }
    }

    override fun getItemCount(): Int {
        return mealList.size
    }
}

interface SetOnMealClickListener {
    fun setOnClickListener(meal: Meal)
}