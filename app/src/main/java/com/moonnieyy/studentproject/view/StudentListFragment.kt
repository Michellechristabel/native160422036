package com.moonnieyy.studentproject.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.moonnieyy.studentproject.R
import com.moonnieyy.studentproject.databinding.FragmentStudentListBinding
import com.moonnieyy.studentproject.databinding.StudentListItemBinding
import com.moonnieyy.studentproject.viewmodel.ListViewModel


class StudentListFragment : Fragment() {
    private lateinit var binding: FragmentStudentListBinding
    private lateinit var viewModel:ListViewModel
    private val studentListAdapter = StudentListAdapter(arrayListOf())

    // gunanya untuk load layout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    // dipanggil setelah layout dipanggil, boleh utak atik UI di dalam onViewCreated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // init the view model
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.refresh() // load/fetch data

        // testing file
//        viewModel.testSaveFile()

        // setup recycle view
        binding.recViewStudent.layoutManager = LinearLayoutManager(context)
        binding.recViewStudent.adapter = studentListAdapter

        // swipe refresh
        binding.refreshLayout.setOnRefreshListener {
            viewModel.refresh()
            binding.refreshLayout.isRefreshing = false
        }

        observeViewModel()
    }

    fun observeViewModel() {
        // observe - live data - arraulist Student
        viewModel.studentsLD.observe(viewLifecycleOwner, Observer {
            studentListAdapter.updateStudentList(it)
        })

        // observe - live data - loading LD
        viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                // still laoding
                binding.progressLoad.visibility = View.VISIBLE
                binding.recViewStudent.visibility = View.INVISIBLE
            } else {
                // sudah ga loading
                binding.progressLoad.visibility = View.INVISIBLE
                binding.recViewStudent.visibility = View.VISIBLE
            }
        })

        // observe - live data - error LD
        viewModel.errorLD.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                // ada error
                binding.txtError.text = "Something wrong when load student data"
                binding.txtError.visibility = View.VISIBLE
                binding.recViewStudent.visibility = View.INVISIBLE
            } else {
                // tidak ada error
                binding.txtError.visibility = View.INVISIBLE
                binding.recViewStudent.visibility = View.VISIBLE
            }
        })
    }
}