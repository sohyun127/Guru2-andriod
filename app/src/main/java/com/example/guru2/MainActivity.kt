package com.example.guru2

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_input_meal.*

class MainActivity : AppCompatActivity() {
    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val Button1 = findViewById<Button>(R.id.button)
        val loginTest=findViewById<Button>(R.id.loginTest)

        fun moveToAnotherPage(){
            val intent = Intent(this,containerActivity::class.java)
            startActivity(intent)
        }

        Button1.setOnClickListener{
            moveToAnotherPage()
        }

        loginTest.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

        mAuth= FirebaseAuth.getInstance()
        var currentUser = mAuth?.currentUser

        //이미 로그인한적이 있는지 확인 (자동로그인)
        /*  if (currentUser == null) {

              Timer().schedule(object : TimerTask() {
                  override fun run() {
                      val intent: Intent = Intent(applicationContext, MainActivity::class.java)
                      startActivity(intent)
                      finish()
                  }
              }, 2000)

          }else{

              Timer().schedule(object : TimerTask() {
                  override fun run() {
                      val intent: Intent = Intent(applicationContext, activity_login::class.java)
                      startActivity(intent)
                      finish()
                  }
              }, 2000)

          }*/
    }

    companion object{
        private var instance:MainActivity? = null
        fun getInstance(): MainActivity? {
            return instance
        }
    }

    override fun  onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val REQUEST_CODE = 1

        if(resultCode != Activity.RESULT_OK) {
            return
        }
        when(requestCode){
            REQUEST_CODE->{
                data?:return
                val uri=data.data as Uri
                mealImg.setImageURI(uri);
            }

            else->{
                Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun changeFragment(index: Int) {
        when (index) {
            1 -> {
                val inputExerciseFragment = InputExerciseFragment();
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, inputExerciseFragment)
                    .commit()
            }

            2 -> {
                val inputMealFragment = InputMealFragment();
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, inputMealFragment)
                    .commit()
            }

            3 -> {
                val exerciseRecordFragment = ExerciseRecordFragment();
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, exerciseRecordFragment)
                    .commit()
            }

            4 -> {
                val mealRecordFragment = MealRecordFragment();
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, mealRecordFragment)
                    .commit()
            }
        }
    }
}