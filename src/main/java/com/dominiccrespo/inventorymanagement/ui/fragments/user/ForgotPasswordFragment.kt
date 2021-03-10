package com.dominiccrespo.inventorymanagement.ui.fragments.user

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
import com.dominiccrespo.inventorymanagement.R
import com.dominiccrespo.inventorymanagement.ui.activity.MainActivity
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordFragment : Fragment() {
    private val TAG = "ForgotPasswordActivity"

//    UI Elems
    private var etEmail: EditText? = null
    private var btnSubmit: Button? = null

//    Firebase references
    private var mAuth: FirebaseAuth? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_forgot_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etEmail = view.findViewById(R.id.editTextForgotPasswordEmail) as EditText
        btnSubmit = view.findViewById(R.id.passwordResetBtn) as Button

        mAuth = FirebaseAuth.getInstance()

        btnSubmit!!.setOnClickListener{ sendPasswordResetEmail() }
    }

    private fun sendPasswordResetEmail() {
        val email = etEmail?.text.toString()

        if(!TextUtils.isEmpty(email)) {
            mAuth!!
                    .sendPasswordResetEmail(email)
                    .addOnCompleteListener{
                      task ->
                        if(task.isSuccessful) {
                            val message = "Email sent."
                            Log.d(TAG, message)
                            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
                            nextActivity()
                        } else {
                            task.exception!!.message?.let { Log.w(TAG, it) }
                            Toast.makeText(activity, "No user found with this email.", Toast.LENGTH_SHORT).show()
                        }
                    }
        } else {
            Toast.makeText(activity, "Enter Email", Toast.LENGTH_SHORT).show()
        }
    }

    private fun nextActivity() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}