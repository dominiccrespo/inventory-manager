package com.dominiccrespo.inventorymanagement.inv.models

data class Product (val name: String, val owner: String, val year_purchased: Int, val description: String, val quantity: Int, val price_per_unit: Double)