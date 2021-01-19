package com.dominiccrespo.inventorymanagement.ui.fragments.products

import android.os.Bundle
import android.util.Log.d
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.dominiccrespo.inventorymanagement.R
import com.dominiccrespo.inventorymanagement.inv.models.Product

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddProductFragment : Fragment() {
    private lateinit var database: DatabaseReference

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_product, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = Firebase.database.reference

        view.findViewById<Button>(R.id.submit_add_product_btn).setOnClickListener {
            val productName = view.findViewById<TextView>(R.id.editTextProductName).text.toString()
            val owner = view.findViewById<TextView>(R.id.editTextOwner).text.toString()
            val yearPurchased = view.findViewById<TextView>(R.id.editTextYearPurchased).text.toString().toInt()
            val productDescription = view.findViewById<TextView>(R.id.editTextProductDesc).text.toString()
            val quantity = view.findViewById<TextView>(R.id.editTextQty).text.toString().toInt()
            val pricePerUnit = view.findViewById<TextView>(R.id.editTextPpu).text.toString().toDouble()
            val product = Product(productName, owner, yearPurchased, productDescription, quantity, pricePerUnit)
            saveProductData(product)
        }

    }

    private fun saveProductData(product: Product)
    {
        database = database.child("product")
        database.setValue(product)

        d("test-save", "\r\nProduct Name: ${product.name},\r\n Product Owner: ${product.owner}, \r\n Year Purchased: ${product.year_purchased}, \r\n Product Description: ${product.description}, \r\n Quantity: ${product.quantity}, \r\n Price per Unit: ${product.price_per_unit}")
    }
}