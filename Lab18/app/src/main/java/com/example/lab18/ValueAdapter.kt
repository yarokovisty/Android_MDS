package com.example.lab18

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lab18.databinding.ItemValueBinding

class ValueAdapter : RecyclerView.Adapter<ValueAdapter.ValueViewHolder>() {
    var valueList = listOf<ValueItem>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ValueViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemValueBinding.bind(view)

        fun bind(valueItem: ValueItem) {
            binding.tvNameValue.text = valueItem.nameValue
            binding.tvValue.text = valueItem.value
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ValueViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_value, parent, false)

        return ValueViewHolder(view)
    }

    override fun getItemCount(): Int {
        return valueList.size
    }

    override fun onBindViewHolder(holder: ValueViewHolder, position: Int) {
        val item = valueList[position]

        holder.bind(item)
    }
}