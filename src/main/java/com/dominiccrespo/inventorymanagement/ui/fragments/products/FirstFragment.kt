package com.dominiccrespo.inventorymanagement.ui.fragments.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.dominiccrespo.inventorymanagement.R

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        view.findViewById<Button>(R.id.add_inventory_btn).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_AddProductFragment)
        }

        view.findViewById<Button>(R.id.view_all_inventory_btn).setOnClickListener{
            findNavController().navigate(R.id.action_FirstFragment_to_ViewAllProductsFragment)
        }


    }
}