package com.example.guru2.Records

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.guru2.NaviActivity
import com.example.guru2.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*


class MealRecordFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: RecyclerView.Adapter<*>
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var arrayList: ArrayList<MealRecModel>
    lateinit var database: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    var buttonClick:Boolean =false //식단 기록 추가 버튼을 클릭했는지 확인하는 변수


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_meal_record, container, false)
        val list = ArrayList<MealRecModel>()
        val fab_add2: FloatingActionButton = rootView.findViewById(R.id.fab_add2)
        val mActivity = activity as NaviActivity

        recyclerView = rootView.findViewById(R.id.rv_mealRecord)
        recyclerView.setHasFixedSize(true) // 리사이클러뷰 기존성능 강화
        layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        arrayList = ArrayList() // User 객체를 담을 어레이 리스트 (어댑터쪽으로)
        database = FirebaseDatabase.getInstance() // 파이어베이스 데이터베이스 연동
        databaseReference = database.getReference("mealrecord")// DB 테이블 연결
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                arrayList.clear() // 기존 배열리스트가 존재하지않게 초기화
                for (snapshot in dataSnapshot.children) { // 반복문으로 데이터 List를 추출해냄
                    val mealRecModel: MealRecModel =
                        snapshot.getValue(MealRecModel::class.java)!! // 만들어뒀던 객체에 데이터를 담는다.
                    arrayList.add(mealRecModel) // 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비
                }
                adapter.notifyDataSetChanged() // 리스트 저장 및 새로고침해야 반영이 됨
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 디비를 가져오던중 에러 발생 시
                Log.e("MealRecord", databaseError.toException().toString()) // 에러문 출력
            }
        })

        val ct: Context = container!!.context
        adapter = RecyclerAdapterMeal2(arrayList, ct)
        recyclerView.adapter = adapter // 리사이클러뷰에 어댑터 연결

        fab_add2.setOnClickListener(){
            buttonClick = true
            mActivity.MealCheck(buttonClick) //운동 기록 추가 버튼 클릭했다는 데이터 넘기기
            Log.d("test", "화면 전환")
        }


        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MealRecordFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}