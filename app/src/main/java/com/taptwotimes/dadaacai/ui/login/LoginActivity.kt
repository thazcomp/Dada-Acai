package com.taptwotimes.dadaacai.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.taptwotimes.dadaacai.databinding.ActivityLoginBinding
import com.taptwotimes.dadaacai.ui.base.BaseActivity
import com.taptwotimes.dadaacai.ui.home.HomeActivity
import com.taptwotimes.dadaacai.ui.signup.SignUpActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setButtonsClickListener()
        binding.btEntrar.isEnabled = false
        verifyButtonEnabled()
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        target?.let{
            return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }?:run{
            return false
        }
    }

    private fun setButtonsClickListener() {
        binding.btEntrar.setOnClickListener {
            viewModel.doLogin(
                getLoginString(),
                getPassString(),
                this,
                ::goToHome
            ){
                showError("Usuário ou senha incorretos", true, true)
            }
        }

        binding.tvCriarConta.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showError(text: String?, emailError:Boolean, passError:Boolean) {
        if(emailError){
            binding.tilEmail.error = text
        }
        if(passError){
            binding.tilPass.error = text
        }
    }

    private fun goToHome() {
        binding.tilPass.error = null
        binding.tilEmail.error = null
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    private fun verifyButtonEnabled() {
        binding.tilEmail.editText?.addTextChangedListener { text ->
            if(isValidEmail(text.toString())){
                val passText = binding.tilPass.editText?.text.toString()
                if(greaterThan5(text.toString())){
                    showError(null, true, true)
                    binding.btEntrar.isEnabled = greaterThan5(passText)
                }
            }else{
                showError("Email inválido", true, false)
            }
        }

        binding.tilPass.editText?.addTextChangedListener { text ->
            val emailText = binding.tilEmail.editText?.text.toString()
            if(greaterThan5(text.toString())){
                showError(null, true, true)
                binding.btEntrar.isEnabled = greaterThan5(emailText)
            }else{
                showError("A senha deve ter mais de 5 caracteres", false, true)
            }
        }
    }

    private fun greaterThan5(text:String):Boolean{
        return text.length>5
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {

    }

    private fun getLoginString(): String {
        return binding.tilEmail.editText?.text.toString()
    }

    private fun getPassString(): String {
        return binding.tilPass.editText?.text.toString()
    }

}