package com.sultan.foodapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.sultan.foodapp.activites.MainActivity
import com.sultan.foodapp.adapters.CategoryAdapter
import com.sultan.foodapp.databinding.FragmentCategoriesBinding
import com.sultan.foodapp.viewModel.HomeViewModel


class CategoriesFragment : Fragment() {
    private lateinit var binding : FragmentCategoriesBinding
    private lateinit var categoriesAdapter:CategoryAdapter
    private lateinit var viewModel : HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoriesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        observeCategoriesAdapter()
    }

    private fun observeCategoriesAdapter() {
        viewModel.observeCategoryLiveData().observe(viewLifecycleOwner) { categories ->
            categoriesAdapter.setCategoryList(categories)
        }
    }

    private fun prepareRecyclerView() {
        categoriesAdapter = CategoryAdapter()
        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter = categoriesAdapter
        }
    }

}