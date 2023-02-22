package com.sultan.foodapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sultan.foodapp.databinding.FragmentAddressBinding

class AddressFragment : Fragment() {
    private lateinit var binding: FragmentAddressBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddressBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            buttonSave.setOnClickListener {
                val addressTitle = edAddressTitle.text.clear()
                val fullName = edFullName.text.clear()
                val street = edStreet.text.clear()
                val phone = edPhone.text.clear()
                val state = edState.text.clear()
                val city = edCity.text.clear()

                Toast.makeText(requireContext(),"Thanks for buy",Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
            }
            buttonDelelte.setOnClickListener {
                val addressTitle = edAddressTitle.text.clear()
                val fullName = edFullName.text.clear()
                val street = edStreet.text.clear()
                val phone = edPhone.text.clear()
                val state = edState.text.clear()
                val city = edCity.text.clear()
            }
            imageAddressClose.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

}