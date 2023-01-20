package com.example.guru2

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_exercise_re_frag.*


class ExerciseRe_frag : Fragment() {
    private var text: String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            text = it.getString("text", "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_exercise_re_frag, container, false)
        val fabexercise: FloatingActionButton = view.findViewById(R.id.fab_exercise)
        val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
        val inputExerciseFragment = InputExerciseFragment();
        fabexercise.setOnClickListener() {
            transaction.replace(R.id.exerciseRecordContainer, inputExerciseFragment);
            transaction.commit();
            Log.d("test", "화면 전환")
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView.text = text

    }



    companion object {
        fun newInstance(text1: String) =
            ExerciseRe_frag().apply {
                arguments = Bundle().apply {
                    putString("text", text1)
                }
            }
    }

}