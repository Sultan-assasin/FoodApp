package com.sultan.aidanaapp.fragments.bottomsheet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sultan.aidanaapp.activites.MainActivity
import com.sultan.aidanaapp.activites.MealActivity
import com.sultan.aidanaapp.databinding.FragmentMealBottomSheetBinding
import com.sultan.aidanaapp.fragments.HomeFragment
import com.sultan.aidanaapp.viewModel.HomeViewModel

private const val MEAL_ID = "param1"

class MealBottomSheetFragment : BottomSheetDialogFragment() {

    private var mealId: String? = null
    private lateinit var binding: FragmentMealBottomSheetBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(MEAL_ID)
        }

        viewModel = (activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMealBottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mealId?.let {
            viewModel.getMealById(it)
        }

        observeBottomSheetMeal()

        onBottomSheetDialogClick()
    }

    private fun onBottomSheetDialogClick() {
        binding.bottomSheet.setOnClickListener {
            if(mealName!= null && mealThumb!= null){
                val intent = Intent(activity,MealActivity::class.java)
                intent.apply {
                    putExtra(HomeFragment.MEAL_ID,mealId)
                    putExtra(HomeFragment.MEAL_NAME,mealName)
                    putExtra(HomeFragment.MEAL_THUMB,mealThumb)
                }
                startActivity(intent)
            }

        }
    }
    private var mealName:String? = null
    private var mealThumb:String? = null

    private fun observeBottomSheetMeal() {
        viewModel.observeBottomSheetMeal().observe(viewLifecycleOwner) { meal ->
            Glide.with(this).load(meal.strMealThumb).into(binding.imgBtmSheet)
            binding.tvBtmSheetArea.text = meal.strArea
            binding.bymSheetMealName.text = meal.strMeal
            binding.tvBtmSheetCategory.text = meal.strCategory

            mealName = meal.strMeal
            mealThumb = meal.strMealThumb

        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            MealBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, param1)
                }
            }
    }
}