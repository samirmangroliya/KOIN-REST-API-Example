package com.samir.koinrestapiexample.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.samir.koinrestapiexample.data.model.User
import com.samir.koinrestapiexample.databinding.ItemUsersListBinding

class UserListAdapter(val data:List<User>): RecyclerView.Adapter<UserListAdapter.DataViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataViewHolder {
       return DataViewHolder(ItemUsersListBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class DataViewHolder(var binding: ItemUsersListBinding) : ViewHolder(binding.root) {
        fun bind(user: User) {
            try {
               binding.apply {
                   tvname.text = user.name ?: ""
               }
            } catch (e:Exception) {
                e.printStackTrace()
            }
        }
    }

}
