package com.dominiccrespo.inventorymanagement.ui.fragments.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dominiccrespo.inventorymanagement.R

class CreateOrLoginUserFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_or_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        view.findViewById<Button>(R.id.goToCreateAccountBtn).setOnClickListener {
            findNavController().navigate(R.id.action_createOrLoginUserFragment_to_createAccountFragment3)
        }

       view.findViewById<Button>(R.id.goToUserLogin).setOnClickListener{
            findNavController().navigate(R.id.action_createOrLoginUserFragment_to_loginFragment)
        }


    }
}