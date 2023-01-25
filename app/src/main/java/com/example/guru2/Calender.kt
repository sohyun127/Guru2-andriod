package com.example.guru2

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.calender_item.view.*
import kotlinx.android.synthetic.main.fragment_calender.*


class Calender : Fragment() {

    var isFabOpen : Boolean = false //fab 버튼 클릭 확인용 변수
    var firestore : FirebaseFirestore? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_calender, container, false)
        firestore =  FirebaseFirestore.getInstance() //파이어스토어 인스턴스 초기화

        recyclerview.adapter=RecyclerViewAdapter()
        recyclerview.layoutManager=LinearLayoutManager(activity)

        return view
    }


    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
       //Schedule 클래스 ArrayList 생성
       var scheduleList:ArrayList<Schedule> = arrayListOf()
            init{ //schedulelist의 문서를 불러온 뒤 Schedule으로 변환해 ArrayList에 담음
                firestore?.collection("scheduleList")?.addSnapshotListener{
                    querySnapshot, firebaseFirestoreException ->
                    //ArrayList 비워줌
                    scheduleList.clear()

                    for(snapshot in querySnapshot!!.documents){
                        var item = snapshot.toObject(Schedule::class.java)
                        scheduleList.add(item!!)
                    }
                    notifyDataSetChanged()
                }

            }

        //xml파일을 inflate하여 ViewHolder를 생성
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
           var view = LayoutInflater.from(parent.context).inflate(R.layout.calender_item,parent,false)
            return RecyclerAdapterMeal.ViewHolder(view)
        }

        inner class ViewHolder(view:View) : RecyclerView.ViewHolder(view){}

        //onCreateViewHolder에서 만든 view와 실제 데이터를 연결
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewHolder =(holder as ViewHolder).itemView

            viewHolder.hour.text = scheduleList[position].hour.toString()
            viewHolder.minute.text = scheduleList[position].minute.toString()

        }

        //리사이클러뷰의 아이템 총 개수 반환
        override fun getItemCount(): Int {
            return scheduleList.size

        }


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //객체 생성
        val btnAdd = view.findViewById<FloatingActionButton>(R.id.btn_add) //일정 추가하기 버튼
        val btnClass = view.findViewById<FloatingActionButton>(R.id.btn_class)//수업 예약 버튼
        val btnIndi = view.findViewById<FloatingActionButton>(R.id.btn_indi)//개인 운동 버튼
        val textClass = view.findViewById<TextView>(R.id.text_class)//수업 예약 글씨
        val textIndi = view.findViewById<TextView>(R.id.text_indi)//개인 운동 글씨
        val cal = view.findViewById<CalendarView>(R.id.cal) //캘린더
        val dialog: ClassDialog = ClassDialog().getInstance() //수업 예약 팝업창
        val dialog2: IndividualExerciseDialog = IndividualExerciseDialog().getInstance() //개인 운동 팝업창


        //일정 추가하기 버튼 클릭시
        btnAdd.setOnClickListener{

            if(isFabOpen) //플로팅액션 버튼 열기 - 닫혀있는 플로팅 버튼 꺼내는 애니메이션 세팅
            {
                btnClass.visibility= View.INVISIBLE
                btnIndi.visibility=View.INVISIBLE
                textClass.visibility=View.INVISIBLE
                textIndi.visibility=View.INVISIBLE
                ObjectAnimator.ofFloat(btnIndi,"translationY",0f).apply{start()}
                ObjectAnimator.ofFloat(btnClass,"translationY",0f).apply{start()}
                ObjectAnimator.ofFloat(textClass,"translationY",0f).apply{start()}
                ObjectAnimator.ofFloat(textIndi,"translationY",0f).apply{start()}
                btnAdd.setImageResource(R.drawable.ic_baseline_add_24)
            }
            else if(!isFabOpen)  //플로팅 액션 버튼 닫기 - 열려있는 플로팅 버튼 집어넣는 애니메이션 세팅
            {
                btnClass.visibility= View.VISIBLE
                btnIndi.visibility=View.VISIBLE
                textClass.visibility=View.VISIBLE
                textIndi.visibility=View.VISIBLE
                ObjectAnimator.ofFloat(btnIndi, "translationY", -200f).apply { start() }
                ObjectAnimator.ofFloat(btnClass, "translationY", -400f).apply { start() }
                ObjectAnimator.ofFloat(textIndi, "translationY", -200f).apply { start() }
                ObjectAnimator.ofFloat(textClass, "translationY", -350f).apply { start() }
                btnAdd.setImageResource(R.drawable.ic_baseline_clear_24)
            }
            isFabOpen = !isFabOpen
        }

        //수업 예약하기 버튼 클릭시
        btnClass.setOnClickListener{
            //다이얼로그 띄우기
            activity?.supportFragmentManager?.let {fragmentManager ->
                dialog.show(fragmentManager,"TAG_DIALOG_EVENT")
            }
        }

        //개인 운동 버튼 클릭시
        btnIndi.setOnClickListener{
            //다이얼로그 띄우기
            activity?.supportFragmentManager?.let {fragmentManager ->
                dialog2.show(fragmentManager,"TAG_DIALOG_EVENT")
            }
        }



    }

}