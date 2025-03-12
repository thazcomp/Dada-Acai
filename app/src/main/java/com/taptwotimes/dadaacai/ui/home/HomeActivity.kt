package com.taptwotimes.dadaacai.ui.home

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationView
import com.taptwotimes.dadaacai.R
import com.taptwotimes.dadaacai.data.preferences.UserPrefs
import com.taptwotimes.dadaacai.databinding.ActivityHomeBinding
import com.taptwotimes.dadaacai.ui.base.BaseActivity
import com.taptwotimes.dadaacai.ui.cart.CartFragment
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.lang.NullPointerException


@AndroidEntryPoint
class HomeActivity: BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityHomeBinding
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNav.selectedItemId = R.id.home_menu
        drawerLayout = binding.myDrawerLayout
        navView = binding.navView

        val header = navView.getHeaderView(0)
        val nameView = header.findViewById<TextView>(R.id.tvName)
        val emailView = header.findViewById<TextView>(R.id.tvEmail)
        val photo :ImageView = header.findViewById<ImageView>(R.id.ivImage)

        viewModel.getUserData {
            nameView.text = UserPrefs.getUserName()
            emailView.text = UserPrefs.getUserEmail()

            try{
                loadImageFromFile(photo)
            }catch (e:NullPointerException){
                e.printStackTrace()
            }
        }
        navView.setNavigationItemSelectedListener(this)

        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.home_menu -> replaceFragment(HomeFragment())
                R.id.drawer_menu -> {
                    if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        drawerLayout.openDrawer(GravityCompat.START)
                    } else {
                        drawerLayout.closeDrawer(GravityCompat.END)
                    }
                }
                R.id.carrinho_menu -> replaceFragment(CartFragment())

            }
            true
        }

        replaceFragment(HomeFragment())
    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, fragment).commit()
        navView.setCheckedItem(R.id.home_nav_menu)
        drawerLayout.closeDrawer(navView)
    }

    private fun loadImageFromFile(imageView: ImageView) {
        val file = File(filesDir, "selected_image.jpg")
        if (file.exists()) {
            imageView.setImageURI(Uri.fromFile(file))
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home_nav_menu -> supportFragmentManager.beginTransaction()
                .replace(R.id.nav_view, HomeFragment()).commit()
            R.id.profile_nav_menu -> supportFragmentManager.beginTransaction()
                .replace(R.id.nav_view, CartFragment()).commit()
        }
        drawerLayout.closeDrawer(GravityCompat.END)
        return true
    }

}