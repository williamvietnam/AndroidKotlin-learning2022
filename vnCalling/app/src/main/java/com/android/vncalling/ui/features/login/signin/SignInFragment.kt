package com.android.vncalling.ui.features.login.signin

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.android.vncalling.R
import com.android.vncalling.base.BaseFragment
import com.android.vncalling.databinding.FragmentSignInBinding
import com.android.vncalling.ui.features.container.MainActivity

class SignInFragment : BaseFragment<FragmentSignInBinding, SignInViewModel>() {

    companion object {
        val TAG: String = SignInFragment::class.java.simpleName
    }

    override fun createViewModel(): SignInViewModel {
        return ViewModelProvider(this)[SignInViewModel::class.java]
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSignInBinding = FragmentSignInBinding.inflate(inflater, container, false)

    override fun initialize() {

        //------------------------------ setup live data ------------------------------------
        viewModel.getShowToast().observe(this, showToast)
        viewModel.getIsLoading().observe(this, isLoading)
        viewModel.getIsLoginSuccess().observe(this, isLoginSuccess)

        //---------------------------- setup events click -----------------------------------
        this.binding.btnSignIn.setOnClickListener {
            login()
        }

        this.binding.forgotPassword.setOnClickListener {
            findNavController().navigate(R.id.actionSignInToForgotPassword)
            Log.d(TAG, "debug: navigate from sign in to forgot password screen")
        }

        this.binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.actionToSignUp)
            Log.d(TAG, "debug: navigate from sign in to sign up screen")
        }
    }

    private fun login() {
        val accountName: String = this.binding.accountName.getText()
        val password: String = this.binding.password.getText()
        if (viewModel.isValidateInputFields(accountName = accountName, password = password)) {
            viewModel.login(accountName = accountName, password = password)
        }
    }

    private fun loading(isLoading: Boolean?) {
        if (isLoading != null && isLoading) {
            this.binding.btnSignIn.visibility = View.GONE
            this.binding.progressBar.visibility = View.VISIBLE
        } else {
            this.binding.btnSignIn.visibility = View.VISIBLE
            this.binding.progressBar.visibility = View.GONE
        }
    }

    private val showToast: Observer<String> = object : Observer<String> {
        override fun onChanged(message: String?) {
            this@SignInFragment.showToast(message = message)
        }
    }

    private val isLoading: Observer<Boolean> = object : Observer<Boolean> {
        override fun onChanged(isLoading: Boolean?) {
            this@SignInFragment.loading(isLoading = isLoading)
        }
    }

    private val isLoginSuccess: Observer<Boolean> = object : Observer<Boolean> {
        override fun onChanged(isLoginSuccess: Boolean?) {
            if (isLoginSuccess != null && isLoginSuccess) {
                startActivity(Intent(activity, MainActivity::class.java))
                activity?.finish()
            }
        }
    }
}