package com.example.roomdatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.roomdatabase.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var studentDatabase: StudentDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.saveBtn.setOnClickListener {
            saveData()
        }
        binding.searchBtn.setOnClickListener {
            searchData()
        }
        binding.deleteallBtn.setOnClickListener {
            GlobalScope.launch {
                studentDatabase.studentDao().deleteAll()
            }
        }
    }

    private fun searchData() {
        val rollNo = binding.rollNoSr.text.toString()
        if (rollNo.isNotEmpty()){
            lateinit var student: Student
            GlobalScope.launch { 
                student = studentDatabase.studentDao().findByRoll(rollNo.toInt())
                if (studentDatabase.studentDao().isEmpty())
                    Handler(Looper.getMainLooper()).post{
                        Toast.makeText(this@MainActivity,"Database have no data",Toast.LENGTH_SHORT).show()
                    }
                else{
                    displayData(student)
                }

            }
        }
    }

    private suspend fun displayData(student: Student) {
        withContext(Dispatchers.Main){
            binding.firstnameEtxt.setText(student.firstName.toString())
            binding.lastnameEtxt.setText(student.lastName.toString())
            binding.rollEtxt.setText(student.rollNo.toString())
        }
    }

    private fun saveData() {
        val firstName = binding.firstnameEtxt.text.toString()
        val lastName = binding.lastnameEtxt.text.toString()
        val rollNo = binding.rollEtxt.text.toString()

        if (firstName.isNotEmpty() && lastName.isNotEmpty() && rollNo.isNotEmpty()){
            val student = Student(null,firstName,lastName,rollNo.toInt())
            GlobalScope.launch ( Dispatchers.IO ){
                studentDatabase.studentDao().insert(student)
            }
            binding.firstnameEtxt.text.clear()
            binding.lastnameEtxt.text.clear()
            binding.rollEtxt.text.clear()

            Toast.makeText(this@MainActivity,"Data Saved",Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this@MainActivity,"Place enter all the data",Toast.LENGTH_SHORT).show()

        }

    }
}