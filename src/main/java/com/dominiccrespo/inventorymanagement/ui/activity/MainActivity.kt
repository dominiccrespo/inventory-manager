package com.dominiccrespo.inventorymanagement.ui.activity


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.dominiccrespo.inventorymanagement.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    //    Firebase references
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()
        if(mAuth!!.getCurrentUser()!=null)
        {
            setContentView(R.layout.activity_main)
            setSupportActionBar(findViewById(R.id.toolbar))
        }
        else {
           val activityIntent = Intent(this, UserActivity::class.java)
            startActivity(activityIntent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemsSelected(item: MenuItem): Boolean = when (item.itemId) {
      R.id.action_logout -> {
          Log.d("test", "In Logout")
          true
      }
        else -> super.onOptionsItemSelected(item)
    }

}