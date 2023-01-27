package com.example.guru2.Records

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.guru2.NaviActivity
import com.example.guru2.R
import com.example.guru2.containerActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*


class ExerciseRecordFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    private var adapter: RecyclerView.Adapter<*>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var arrayList: ArrayList<ExerciseRecModel>? = null
    private var database: FirebaseDatabase? = null
    private var databaseReference: DatabaseReference? = null
    var buttonClick:Boolean =false //운동 기록 추가 버튼을 클릭했는지 확인하는 변수

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_exercise_record, container, false)

        val list = ArrayList<ExerciseRecModel>()

        recyclerView = rootView.findViewById(R.id.rv_exerciseRecord)
        recyclerView!!.setHasFixedSize(true) // 리사이클러뷰 기존성능 강화
        layoutManager = LinearLayoutManager(context)
        recyclerView!!.layoutManager = layoutManager
        arrayList = ArrayList() // User 객체를 담을 어레이 리스트 (어댑터쪽으로)
        database = FirebaseDatabase.getInstance() // 파이어베이스 데이터베이스 연동
        databaseReference = database!!.getReference("exerciserecord") // DB 테이블 연결
        databaseReference!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(@NonNull dataSnapshot: DataSnapshot) {
                // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                arrayList!!.clear() // 기존 배열리스트가 존재하지않게 초기화
                for (snapshot in dataSnapshot.children) { // 반복문으로 데이터 List를 추출해냄
                    val exerciseRecModel: ExerciseRecModel? =
                        snapshot.getValue(ExerciseRecModel::class.java) // 만들어뒀던 User 객체에 데이터를 담는다.
                    arrayList!!.add(exerciseRecModel!!) // 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비
                }
                adapter!!.notifyDataSetChanged() // 리스트 저장 및 새로고침해야 반영이 됨
            }

            override fun onCancelled(@NonNull databaseError: DatabaseError) {
                // 디비를 가져오던중 에러 발생 시
                Log.e("ExerciseRecord", databaseError.toException().toString()) // 에러문 출력
            }
        })

        adapter = RecyclerAdapterExercise2(arrayList!!, context)
        recyclerView!!.adapter = adapter // 리사이클러뷰에 어댑터 연결

        val fab_add: FloatingActionButton = rootView.findViewById(R.id.fab_add)
        val mActivity = activity as NaviActivity
        fab_add.setOnClickListener() {
            buttonClick = true
            mActivity.ExerciseCheck(buttonClick) //운동 기록 추가 버튼 클릭했다는 데이터 넘기기
            Log.d("test", "화면 전환")
        }

        return rootView
    }




}