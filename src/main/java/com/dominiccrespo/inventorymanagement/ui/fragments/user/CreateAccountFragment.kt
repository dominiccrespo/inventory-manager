package com.dominiccrespo.inventorymanagement.ui.fragments.user

import android.app.ProgressDialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dominiccrespo.inventorymanagement.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateAccountFragment : Fragment() {
//    UI Elements
    private var etFirstName: EditText? = null
    private var etLastName: EditText? = null
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private var etPasswordConfirm: EditText? = null
    private var btnCreateAccount: Button? = null
    private var mProgressBar: ProgressDialog? = null

//    Firebase references
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

//    Global Vars
    private val TAG = "CreateAccountFragment"
    private var firstName: String? = null
    private var lastName: String? = null
    private var email: String? = null
    private var password: String? = null
    private var passwordConfirm: String? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        return inflater.inflate(R.layout.fragment_create_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etFirstName = view.findViewById(R.id.editTextFirstName) as EditText
        etLastName = view.findViewById(R.id.editTextLastName) as EditText
        etEmail = view.findViewById(R.id.editTextEmail) as EditText
        etPassword = view.findViewById(R.id.editTextPassword) as EditText
        etPasswordConfirm = view.findViewById(R.id.editTextPasswordConfirm) as EditText
        btnCreateAccount = view.findViewById(R.id.createAccountBtn) as Button
        mProgressBar = ProgressDialog(activity)

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()

        btnCreateAccount!!.setOnClickListener{ createNewAccount() }

    }

    private fun createNewAccount(){
        firstName = etFirstName?.text.toString()
        lastName = etLastName?.text.toString()
        email = etEmail?.text.toString()
        password = etPassword?.text.toString()
        passwordConfirm = etPasswordConfirm?.text.toString()

        if(!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(passwordConfirm))
        {
            if(TextUtils.equals(password, passwordConfirm)) {
                mProgressBar!!.setMessage("Registering User...")
                mProgressBar!!.show()

                mAuth!!
                    .createUserWithEmailAndPassword(email!!, password!!)
                    .addOnCompleteListener(requireActivity()){task->
                        mProgressBar!!.hide()
                        if(task.isSuccessful)
                        {
                            Log.d(TAG, "createUserWithEmail: success")

                            val userId = mAuth!!.currentUser!!.uid

//                            verify email
                            verifyEmail()

//                            update user profile information
                            val currentUserDb = mDatabaseReference!!.child(userId)
                            currentUserDb.child("firstName").setValue(firstName)
                            currentUserDb.child("lastName").setValue(lastName)

                            updateUserInfoAndUI()
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(requireContext(), "Authentication failed", Toast.LENGTH_SHORT).show()
                        }

                    }
            }
            else
            {
                Toast.makeText(activity, "Passwords do not match.", Toast.LENGTH_SHORT).show()
            }
        }
        else{
            Toast.makeText(activity, "Enter all details", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUserInfoAndUI(){

    }

    private fun verifyEmail(){

    }

}