package com.moonnieyy.studentproject.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.moonnieyy.studentproject.R
import com.moonnieyy.studentproject.databinding.FragmentStudentDetailBinding
import com.moonnieyy.studentproject.databinding.FragmentStudentListBinding
import com.moonnieyy.studentproject.model.Student
import com.moonnieyy.studentproject.viewmodel.DetailViewModel
import com.moonnieyy.studentproject.viewmodel.ListViewModel

class StudentDetailFragment : Fragment() {
    private lateinit var binding: FragmentStudentDetailBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var student: Student

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // baca id student lalu panggil fetch viewmodel
        // untuk load data student tersebut
        val id = StudentDetailFragmentArgs.fromBundle(requireArguments()).id
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.fetch(id)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.studentLD.observe(viewLifecycleOwner, Observer {
            student = it

            Toast.makeText(context, "Data loaded", Toast.LENGTH_SHORT).show()

            // update UI
            binding.txtID.setText(student.id)
            binding.txtName.setText(student.name)
            binding.txtbod.setText(student.bod)
            binding.txtPhone.setText(student.phone)
        })
    }
}