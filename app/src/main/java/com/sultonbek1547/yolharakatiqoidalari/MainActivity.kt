package com.sultonbek1547.yolharakatiqoidalari

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.sultonbek1547.yolharakatiqoidalari.databinding.ActivityMainBinding
import com.sultonbek1547.yolharakatiqoidalari.fragment.About
import com.sultonbek1547.yolharakatiqoidalari.fragment.Home
import com.sultonbek1547.yolharakatiqoidalari.fragment.Liked

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val fragmentHome = Home()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_YolHarakatiQoidalari)
        setContentView(binding.root)
        val fragmentAbout = About()
        val fragmentLiked = Liked()

        supportFragmentManager.beginTransaction()
            .add(binding.myContainer.id, fragmentHome)
            .commit()

        binding.bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_menu_about -> {
                    supportFragmentManager.beginTransaction()
                        .replace(binding.myContainer.id, fragmentAbout)
                        .commit()
                }
                R.id.bottom_menu_like -> {
                    supportFragmentManager.beginTransaction()
                        .replace(binding.myContainer.id, fragmentLiked)
                        .commit()
                }
                R.id.bottom_menu_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(binding.myContainer.id, fragmentHome)
                        .commit()
                }


            }
            true
        }
    }

    override fun onBackPressed() {
            finish()
    }
}