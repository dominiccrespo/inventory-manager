package com.dominiccrespo.inventorymanagement.ui.fragments.user

import android.app.ProgressDialog
import android.content.Intent
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
import androidx.navigation.fragment.findNavController
import com.dominiccrespo.inventorymanagement.R
import com.dominiccrespo.inventorymanagement.ui.activity.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateAccountFragment : Fragment() {
//    UI Elems
    private var etFirstName: EditText? = null
    private var etLastName: EditText? = null
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private var etPasswordConfirm: EditText? = null
    private var btnCreateAccount: Button? = null
    private var mProgressBar: ProgressDialog? = null
    private var btnLogin: Button? = null

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
        btnLogin = view.findViewById(R.id.createLoginBtn) as Button
        mProgressBar = ProgressDialog(activity)

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()

        btnCreateAccount!!.setOnClickListener{ createNewAccount() }
        btnLogin!!.setOnClickListener{findNavController().navigate(R.id.action_createAccountFragment3_to_loginFragment)}

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

                            nextActivity()
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(requireContext(), task.exception.toString(), Toast.LENGTH_SHORT).show()
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

    private fun nextActivity(){
        val intent = Intent(requireActivity(), MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun verifyEmail(){
        val mUser = mAuth!!.currentUser
        mUser!!.sendEmailVerification()
                .addOnCompleteListener(requireActivity()) {
                    task ->
                    if(task.isSuccessful) {
                        Toast.makeText(requireContext(), "Verification email sent to " + mUser.getEmail(), Toast.LENGTH_SHORT).show()
                    } else {
                        Log.e(TAG, "sendEmailVerification", task.exception)
                        Toast.makeText(requireActivity(), "Failed to send verification email.", Toast.LENGTH_SHORT).show()
                    }
                }
    }

}
