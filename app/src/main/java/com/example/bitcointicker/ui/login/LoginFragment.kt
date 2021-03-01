package com.example.bitcointicker.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.bitcointicker.BaseFragment
import com.example.bitcointicker.R
import com.example.bitcointicker.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.math.log

class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mView =  super.onCreateView(inflater, container, savedInstanceState)?.rootView

        init()
        listeners()

        return mView
    }

    private fun init(){
        auth = Firebase.auth
    }

    private fun listeners(){
        dataBinding.buttonLogin.setOnClickListener {
            login()
        }

        dataBinding.buttonRegister.setOnClickListener{
            register()
        }
    }


    private fun login(){
        auth.signInWithEmailAndPassword(dataBinding.editTextEmail.text.toString(), dataBinding.editTextPassword.text.toString())
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    navigateCoinDetail()
                }
                else {
                    Toast.makeText(requireContext(), "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun register(){
        auth.createUserWithEmailAndPassword(dataBinding.editTextEmail.text.toString(), dataBinding.editTextPassword.text.toString())
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    navigateCoinDetail()
                }
                else {
                    Toast.makeText(requireContext(), "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun navigateCoinDetail(){
        findNavController().navigate(R.id.action_fragment_login_to_dialog_fragment_coin_detail)
    }

    override fun getLayoutRes(): Int = R.layout.fragment_login
}