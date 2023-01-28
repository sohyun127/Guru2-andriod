package com.example.guru2.calender_user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.guru2.R


class RecyclerViewAdapter(val itemList: ArrayList<Schedule>):RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>(){


    //아이템 레이아웃과 결합
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.calender_item,parent,false)
        return RecyclerViewHolder(view)
    }

    //view에 내용 입력
    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.time.text = itemList[position].time
        holder.type.text = itemList[position].type
    }

    //리스트 내 아이템 개수
    override fun getItemCount(): Int {
        return itemList.size
    }


    //레이아웃 내 View 연결
    inner class RecyclerViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val time = itemView.findViewById<TextView>(R.id.time)
        val type = itemView.findViewById<TextView>(R.id.schedule_type)
    }

    //데이터 삭제 함수
    fun removeData(position: Int){
        itemList.removeAt(position)
        notifyItemRemoved(position)//특정 아이템 1개 삭제
    }





}