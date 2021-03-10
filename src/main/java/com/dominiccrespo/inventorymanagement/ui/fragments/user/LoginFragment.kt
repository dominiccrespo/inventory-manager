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
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dominiccrespo.inventorymanagement.R
import com.dominiccrespo.inventorymanagement.ui.activity.MainActivity
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {
    //    Global Vars
    private var email: String? = null
    private var password: String? = null
    private val TAG = "LoginFragment"

    //    UI elems
    private var etEmail : EditText? = null
    private var etPassword : EditText? = null
    private var btnLogin : Button? = null
    private var tvForgotPassword : TextView? = null
    private var btnCreateAccount : Button? = null
    private var mProgressBar: ProgressDialog? = null

    private var mAuth: FirebaseAuth? = null



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        return inflater.inflate(R.layout.fragment_user_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etEmail = view.findViewById(R.id.editTextLoginEmail)
        etPassword = view.findViewById(R.id.editTextLoginPassword)
        btnLogin = view.findViewById(R.id.loginBtn)
        tvForgotPassword = view.findViewById(R.id.tvForgotPassword)
        btnCreateAccount = view.findViewById(R.id.createAccountBtnLogin)
        mProgressBar = ProgressDialog(activity)

        mAuth = FirebaseAuth.getInstance()

        btnLogin!!.setOnClickListener{ login() }
        btnCreateAccount!!.setOnClickListener{findNavController().navigate(R.id.action_loginFragment_to_createAccountFragment3)}
        tvForgotPassword!!.setOnClickListener { findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment) }
    }

    private fun login() {
        email = etEmail?.text.toString()
        password = etPassword?.text.toString()

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password))
        {
            mProgressBar!!.setMessage("Registering User...")
            Log.d(TAG, "Logging in user.")

            mAuth!!.signInWithEmailAndPassword(email!!,password!!)
                    .addOnCompleteListener(requireActivity()) { task ->
                        mProgressBar!!.hide()

                        if(task.isSuccessful) {
                            Log.d(TAG, "signInWithEmail:success")
                            nextActivity()
                        } else {
                            Log.e(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(activity, "Authentication failed.", Toast.LENGTH_SHORT).show()
                        }
                    }
        } else {
            Toast.makeText(activity, "Enter all details", Toast.LENGTH_SHORT).show()
        }
    }

    private fun nextActivity() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}