package com.sultonbek1547.yolharakatiqoidalari.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.sultonbek1547.yolharakatiqoidalari.AddActivity
import com.sultonbek1547.yolharakatiqoidalari.Constraints.TEMP_USER
import com.sultonbek1547.yolharakatiqoidalari.Constraints.VIEW_PAGER_ITEM_POSITION
import com.sultonbek1547.yolharakatiqoidalari.Constraints.WHICH_TYPE_ARRAY
import com.sultonbek1547.yolharakatiqoidalari.MyModel
import com.sultonbek1547.yolharakatiqoidalari.RvClickInterFace
import com.sultonbek1547.yolharakatiqoidalari.ViewPagerAdapter
import com.sultonbek1547.yolharakatiqoidalari.databinding.FragmentHomeBinding
import com.sultonbek1547.yolharakatiqoidalari.db.MyDbHelper


class Home : Fragment(), RvClickInterFace {

    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var myDbHelper: MyDbHelper
    private var position = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        myDbHelper = MyDbHelper(requireContext())


        viewPagerAdapter = ViewPagerAdapter(requireContext(), myDbHelper.getAllUsers(), this)
        binding.myViewPager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.myTabLayout, binding.myViewPager) { tab, position ->
            tab.text = WHICH_TYPE_ARRAY[position]
        }.attach()

        binding.toolbar.setOnMenuItemClickListener {
            val intent = Intent(requireContext(), AddActivity::class.java)
            intent.putExtra("isEdit", false)
            requireActivity().startActivity(intent)
            true
        }


        return binding.root
    }

    override fun changeLikedState(position: Int, user: MyModel) {
        myDbHelper.editUser(user)
    }

    override fun edit(user: MyModel, position: Int) {
        this.position = position
        val intent = Intent(requireContext(), AddActivity::class.java)
        intent.putExtra("isEdit", true)
        intent.putExtra("user", user)
        requireActivity().startActivity(intent)
    }

    override fun delete(user: MyModel, position: Int) {
        viewPagerAdapter.deleteUser(position,binding.myViewPager.currentItem)
        myDbHelper.deleteUser(user)

    }


    override fun onStop() {

        VIEW_PAGER_ITEM_POSITION = binding.myViewPager.currentItem

        super.onStop()
    }

    override fun onStart() {
        viewPagerAdapter = ViewPagerAdapter(requireContext(), myDbHelper.getAllUsers(), this)
        binding.myViewPager.adapter = viewPagerAdapter

        binding.myViewPager.currentItem = VIEW_PAGER_ITEM_POSITION
        super.onStart()
    }
}