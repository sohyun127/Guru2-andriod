package com.example.guru2.Records

import android.app.Dialog
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.guru2.R
import com.google.firebase.database.FirebaseDatabase

class popup_customexercise(private val context: Context) {
    private val dialog = Dialog(context)
    lateinit var strCustomExerciseDate: String
    lateinit var strCustomExerciseName: String
    lateinit var strCustomSet: String
    lateinit var strCustomCount: String

    fun saveData(dataName: String){
        dialog.setContentView(R.layout.popup_customexercise)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()

        val edtExerciseDate2 = dialog.findViewById<EditText>(R.id.edtExerciseDate2)
        val edt_custom_exercise_name = dialog.findViewById<EditText>(R.id.edt_custom_exercise_name)
        val edt_custom_set = dialog.findViewById<EditText>(R.id.edt_custom_set)
        val edt_custom_count = dialog.findViewById<EditText>(R.id.edt_custom_count)
        val btnCancel = dialog.findViewById<Button>(R.id.custom_btnCancel)
        val btnOk = dialog.findViewById<Button>(R.id.custom_btnOk)

        checkChanges(edtExerciseDate2, edt_custom_exercise_name, edt_custom_set, edt_custom_count)

        btnOk.setOnClickListener {
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference()

            // 입력한 데이터 저장
            if(!::strCustomExerciseDate.isInitialized || !::strCustomExerciseName.isInitialized
                || !::strCustomSet.isInitialized || !::strCustomCount.isInitialized
                || strCustomExerciseDate == "" || strCustomExerciseName == ""
                || strCustomSet == "" || strCustomCount == "") {
                Toast.makeText(context, "빈칸을 모두 채워주세요.", Toast.LENGTH_SHORT).show()
            }
            else{
                val dataInput = ExerciseRecModel(
                    strCustomExerciseName, strCustomExerciseDate, strCustomSet, strCustomCount
                )

                myRef.child(dataName).push().setValue(dataInput)
            }
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    fun saveData2(dataName: String, uid: String){
        dialog.setContentView(R.layout.popup_customexercise)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()

        val edtExerciseDate2 = dialog.findViewById<EditText>(R.id.edtExerciseDate2)
        val edt_custom_exercise_name = dialog.findViewById<EditText>(R.id.edt_custom_exercise_name)
        val edt_custom_set = dialog.findViewById<EditText>(R.id.edt_custom_set)
        val edt_custom_count = dialog.findViewById<EditText>(R.id.edt_custom_count)
        val btnCancel = dialog.findViewById<Button>(R.id.custom_btnCancel)
        val btnOk = dialog.findViewById<Button>(R.id.custom_btnOk)

        checkChanges(edtExerciseDate2, edt_custom_exercise_name, edt_custom_set, edt_custom_count)

        btnOk.setOnClickListener {
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference()

            // 입력한 데이터 저장
            if(!::strCustomExerciseDate.isInitialized || !::strCustomExerciseName.isInitialized
                || !::strCustomSet.isInitialized || !::strCustomCount.isInitialized
                || strCustomExerciseDate == "" || strCustomExerciseName == ""
                || strCustomSet == "" || strCustomCount == "") {
                Toast.makeText(context, "빈칸을 모두 채워주세요.", Toast.LENGTH_SHORT).show()
            }
            else{
                val dataInput = ExerciseRecModel(
                    strCustomExerciseName, strCustomExerciseDate, strCustomSet, strCustomCount
                )

                myRef.child(dataName).child(uid).push().setValue(dataInput)
            }
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    interface ButtonClickListener {
        fun onClicked()
    }

    private lateinit var  onClickListener: ButtonClickListener

    fun setOnClickedListener(listener: ButtonClickListener) {
        onClickListener = listener
    }

    fun checkChanges(edtExerciseDate: EditText, edtExerciseName: EditText, edt_set: EditText, edt_count: EditText) {

        edtExerciseDate.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                strCustomExerciseDate = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                           after: Int, ) {}

            override fun afterTextChanged(s: Editable) {
            }
        })

        edtExerciseName.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                strCustomExerciseName = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                           after: Int, ) {}

            override fun afterTextChanged(s: Editable) {
            }
        })

        edt_set.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                strCustomSet = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                           after: Int, ) {}

            override fun afterTextChanged(s: Editable) {
            }
        })

        edt_count.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                strCustomCount = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                           after: Int, ) {}

            override fun afterTextChanged(s: Editable) {
            }
        })
    }
}