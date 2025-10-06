package com.moonnieyy.studentproject.model

import com.google.gson.annotations.SerializedName

data class Student(
    // kasi tanda tanya supaya boleh null
    var id:String?,
    @SerializedName("student_name")
    var name:String?,
    @SerializedName("birth_of_date")
    var bod:String?,
    var phone:String?,
    @SerializedName("photo_url")
    var photoUrl:String?
)
