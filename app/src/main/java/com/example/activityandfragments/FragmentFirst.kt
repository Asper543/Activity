package com.example.activityandfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.activityandfrugments.R

class FragmentFirst: Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_1, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      val text = view.findViewById<TextView>(R.id.name_fragment_container_text_view_1)
        text.textSize = 50f
        super.onViewCreated(view, savedInstanceState)
    }
}