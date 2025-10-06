package com.moonnieyy.studentproject.viewmodel

import android.app.Application
import android.content.ContentValues.TAG
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.moonnieyy.studentproject.model.Student

class DetailViewModel(app: Application): AndroidViewModel(app) {
    val studentLD = MutableLiveData<Student>()
    val TAG = "volleytag"
    private var queue: RequestQueue? = null

    fun update() {
        //diisi coding untuk simpan studentLD object ke serve
        //pake volley
    }

    fun fetch(id:String) {
        queue = Volley.newRequestQueue(getApplication())
        val url = "https://www.jsonkeeper.com/b/LLMW"
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            {
                // sukses
                val sType = object: TypeToken<ArrayList<Student>>(){}.type
                val result = Gson().fromJson<ArrayList<Student>>(it, sType)
                val arrStudent = result as ArrayList<Student>
                studentLD.value = arrStudent.find { it.id == id } as Student
            },
            {
                // failed
            }
        )
        stringRequest.tag = TAG
        queue?.add(stringRequest)

    }
}