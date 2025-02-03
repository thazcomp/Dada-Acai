package com.taptwotimes.dadaacai.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.taptwotimes.dadaacai.R
import com.taptwotimes.dadaacai.databinding.ActivityHomeBinding
import com.taptwotimes.dadaacai.ui.base.BaseActivity
import com.taptwotimes.dadaacai.ui.cart.CartFragment

class HomeActivity: BaseActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(HomeFragment())

        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.home_menu -> replaceFragment(HomeFragment())
                //R.id.drawer_menu -> //Open Navigation Drawer
                R.id.carrinho_menu -> replaceFragment(CartFragment())

            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment).commit()
    }
}