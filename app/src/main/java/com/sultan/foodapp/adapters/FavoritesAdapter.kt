package com.sultan.foodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sultan.foodapp.databinding.MealItemFragmentBinding
import com.sultan.foodapp.pojo.Meal

class FavoritesAdapter() :
    RecyclerView.Adapter<FavoritesAdapter.FavoritesMealsAdapterViewHolder2>() {
    var onItemClick: ((Meal) -> Unit)? = null

    inner class FavoritesMealsAdapterViewHolder2(val binding: MealItemFragmentBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Meal>() {
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoritesMealsAdapterViewHolder2 {
        return FavoritesMealsAdapterViewHolder2(
            MealItemFragmentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(
        holder: FavoritesAdapter.FavoritesMealsAdapterViewHolder2,
        position: Int
    ) {
        val meal = differ.currentList[position]
        Glide.with(holder.itemView)
            .load(meal.strMealThumb)
            .into(holder.binding.imgMeal)
        holder.binding.tvMealName.text = meal.strMeal

        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(meal)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}