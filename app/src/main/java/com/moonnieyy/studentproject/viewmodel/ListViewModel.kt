package com.moonnieyy.studentproject.viewmodel

import android.app.Application
import android.util.Log
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
import com.moonnieyy.studentproject.util.FileHelper

class ListViewModel(application: Application): AndroidViewModel(application) {
    val studentsLD = MutableLiveData<ArrayList<Student>>()
    val errorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    val TAG = "volleytag"
    private var queue: RequestQueue? = null

    fun refresh() {
        loadingLD.value = true //progress bar start muncul
        errorLD.value = false //errornya gak muncul

        studentsLD.value = arrayListOf(
            Student("16055","Nonie","1998/03/28","5718444778","http://dummyimage.com/75x100"
                    + ".jpg/cc0000/ffffff"),
            Student("13312","Rich","1994/12/14","3925444073","http://dummyimage.com/75x100" +
                    ".jpg/5fa2dd/ffffff"),
            Student("11204","Dinny","1994/10/07","6827808747",
                "http://dummyimage.com/75x100.jpg/5fa2dd/ffffff1")
        )

        // volley ke API
        queue = Volley.newRequestQueue(getApplication())
        val url = "https://www.jsonkeeper.com/b/LLMW"
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            {
                // sukses
                val sType = object: TypeToken<ArrayList<Student>>(){}.type
                val result = Gson().fromJson<ArrayList<Student>>(it, sType)
                studentsLD.value = result as ArrayList<Student>
                loadingLD.value = false

                // simpan juga ke file
                val filehelper = FileHelper(getApplication())
                val jsonstring = Gson().toJson(result)

                filehelper.writeToFile(jsonstring)
                Log.d("print_file", jsonstring)

                // baca json string dari file
                val hasil = filehelper.readFromFile()
                val listStudent = Gson().fromJson<List<Student>>(hasil, sType)
                Log.d("print_file", listStudent.toString())

            },
            {
                // failed
                errorLD.value = true
                loadingLD.value = false
            }
        )
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    fun testSaveFile() {
        val fileHelper = FileHelper(getApplication())
        fileHelper.writeToFile("Hello World")
        val content = fileHelper.readFromFile()
        Log.d("print_file",content)
        Log.d("print_file", fileHelper.getFilePath())
    }
    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}