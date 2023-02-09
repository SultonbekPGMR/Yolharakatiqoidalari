package com.sultonbek1547.yolharakatiqoidalari.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sultonbek1547.yolharakatiqoidalari.R
import com.sultonbek1547.yolharakatiqoidalari.databinding.FragmentAboutBinding


open class About : Fragment() {

    private val binding by lazy { FragmentAboutBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {



        return binding.root
    }

}