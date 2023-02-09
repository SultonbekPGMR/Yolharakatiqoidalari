package com.sultonbek1547.yolharakatiqoidalari

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.sultonbek1547.yolharakatiqoidalari.databinding.RvItemBinding

class RvAdapter(
    val context: Context,
    var list: ArrayList<MyModel>,
    val rvClickInterFace: RvClickInterFace
) :
    RecyclerView.Adapter<RvAdapter.Vh>() {

    inner class Vh(private val itemRvBinding: RvItemBinding) : RecyclerView.ViewHolder(itemRvBinding.root) {
        fun onBind(user: MyModel, position: Int) {
            itemRvBinding.tvName.text = user.name
            itemRvBinding.image.setImageURI( Uri.parse(user.photoPath))
            if (user.likedState == 1) {
                itemRvBinding.btnLike.setImageResource(R.drawable.full_heart)
            }

            itemRvBinding.apply {
                btnLike.setOnClickListener {
                    if (user.likedState == 0) {
                        btnLike.setImageResource(R.drawable.full_heart)
                        list[position].likedState = 1
                        user.likedState = 1
                        rvClickInterFace.changeLikedState(position, user)
                    } else {
                        btnLike.setImageResource(R.drawable.empty_heart)
                        list[position].likedState = 0
                        user.likedState = 0
                        rvClickInterFace.changeLikedState(position, user)
                    }
                }

                btnDelete.setOnClickListener {
                    rvClickInterFace.delete(user,position)
                }

                btnEdit.setOnClickListener {
                    rvClickInterFace.edit(user,position)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) =
        holder.onBind(list[position], position)


    override fun getItemCount(): Int = list.size

}

interface RvClickInterFace {
    fun changeLikedState(position: Int, user: MyModel)
    fun edit(user: MyModel, position: Int)
    fun delete(user: MyModel,position: Int)
}