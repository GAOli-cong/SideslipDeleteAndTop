package com.glc.sideslipdeleteandtop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 *author:gaolc
 *date: On 2021/4/20
 *desc:
 */
class SampleAdapter(val list: ArrayList<String>): RecyclerView.Adapter<SampleAdapter.ViewHolder>() {
    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val tvName:TextView=view.findViewById(R.id.tvName)
        val btnTop:TextView=view.findViewById(R.id.btnTop)
        val btnDelete:TextView=view.findViewById(R.id.btnDelete)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleAdapter.ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_sample,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount()=list.size

    override fun onBindViewHolder(holder: SampleAdapter.ViewHolder, position: Int) {
        holder.tvName.setText(list.get(position))

        //删除按钮
        holder.btnDelete.setOnClickListener {
            onItemClickDelete.invoke(it,position)
        }
        //置顶按钮
        holder.btnTop.setOnClickListener {
            onItemClickTop.invoke(it,position)
        }
    }

    //删除
    fun delete(position: Int){
        list.removeAt(position)
        notifyDataSetChanged()
    }
    //置顶
    fun top(position: Int){
        list.add(0,list.removeAt(position))
        notifyDataSetChanged()
    }

    lateinit var onItemClickDelete: (v: View, pos: Int) -> Unit//删除按钮点击事件
    lateinit var onItemClickTop: (v: View, pos: Int) -> Unit//删除按钮点击事件
}