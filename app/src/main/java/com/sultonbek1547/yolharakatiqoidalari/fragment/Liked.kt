package com.sultonbek1547.yolharakatiqoidalari.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sultonbek1547.yolharakatiqoidalari.*
import com.sultonbek1547.yolharakatiqoidalari.Constraints.TEMP_USER
import com.sultonbek1547.yolharakatiqoidalari.databinding.FragmentLikedBinding
import com.sultonbek1547.yolharakatiqoidalari.db.MyDbHelper
import java.io.File


class Liked : Fragment(), RvClickInterFace {


    private val binding by lazy { FragmentLikedBinding.inflate(layoutInflater) }
    private lateinit var rvAdapter: RvAdapter
    private lateinit var myDbHelper: MyDbHelper
    private var position = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        myDbHelper = MyDbHelper(requireContext())
        rvAdapter = RvAdapter(requireContext(), myDbHelper.getLikedUsers(), this)

        binding.myRv.adapter = rvAdapter

        return binding.root
    }

    override fun changeLikedState(position: Int, user: MyModel) {
        myDbHelper.editUser(user)
        rvAdapter.list.removeAt(position)
        rvAdapter.notifyItemRemoved(position)

    }

    override fun edit(user: MyModel, position: Int) {
        val intent = Intent(requireContext(), AddActivity::class.java)
        intent.putExtra("isEdit", true)
        intent.putExtra("user", user)
        requireActivity().startActivity(intent)
        this.position = position
    }

    override fun delete(user: MyModel, position: Int) {
        myDbHelper.deleteUser(user)
        rvAdapter.list.removeAt(position)
        rvAdapter.notifyItemRemoved(position)
        rvAdapter.notifyItemRangeChanged(0, rvAdapter.itemCount)

        val file = File(requireActivity().filesDir, "${user.id}.jpg")
        file.delete()

    }

    override fun onStart() {
        if (Constraints.USER_EDITED_STATE) {
            Constraints.USER_EDITED_STATE = false
            rvAdapter.list[position] = TEMP_USER
            rvAdapter.notifyItemChanged(position)
        }

        super.onStart()
    }

}