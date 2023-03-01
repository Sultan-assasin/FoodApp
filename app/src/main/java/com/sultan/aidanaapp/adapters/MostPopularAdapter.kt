package com.sultan.aidanaapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sultan.aidanaapp.databinding.PopularItemsBinding
import com.sultan.aidanaapp.pojo.Meal
import com.sultan.aidanaapp.pojo.MealsByCategory

class MostPopularAdapter() : RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>() {
    private var mealList = ArrayList<Meal>()
    lateinit var onItemClick : (Meal)-> Unit
    var onLongClickListener: ((Meal)-> Unit)?=null

    fun setMeals(mealList: ArrayList<Meal>){
        this.mealList = mealList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        return PopularMealViewHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context), parent,false))
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb)
            .into(holder.binding.imgPopularMealItem)

        holder.itemView.setOnClickListener{
            onItemClick.invoke(mealList[position])
        }
        holder.itemView.setOnLongClickListener {
            onLongClickListener?.invoke(mealList[position])
            true
        }

        holder.binding.apply {
            tvPopular.setText(mealList[position].strMeal)

        }

    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    class PopularMealViewHolder(val binding: PopularItemsBinding) :
        RecyclerView.ViewHolder(binding.root)
}