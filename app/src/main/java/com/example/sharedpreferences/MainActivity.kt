package com.example.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var userName: EditText
    lateinit var userMessage: EditText
    lateinit var counter : Button
    lateinit var remember : CheckBox

    var count : Int = 0

    //the containers for the saved items in the activity
    var name : String ?= null
    var message : String? = null
    var isChecked: Boolean ?= null

    //the sharedPreferences object
    lateinit var sharedPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userName = findViewById(R.id.editTextText)
        userMessage = findViewById(R.id.editTextTextMultiLine)
        counter = findViewById(R.id.button)
        remember = findViewById(R.id.checkBox)

        counter.setOnClickListener {
            count += count
            counter.setText("" + count) //the "" before the integer value converts the interger to a string directly

        }
    }

    //the lifecycle period when the app is about to be closed or switched
    override fun onPause() {
        super.onPause()

        //the saveData method called in the onPause() lifecycle period of this activity
        saveData()
    }

    fun saveData(){
        sharedPreferences = this.getSharedPreferences("saveData", Context.MODE_PRIVATE)

        name = userName.text.toString()
        message = userMessage.text.toString()
        isChecked = remember.isChecked

        val editor = sharedPreferences.edit()

        //saving the data using a key-value pairs ( the first parameter is the key and the second is the data to be saved in the data format previously defined)
        editor.putString("key name", name)
        editor.putString("key message", message)
        editor.putInt("key count", count)
        editor.putBoolean("key remember",isChecked!!)

        editor.apply()

        Toast.makeText(applicationContext,"Your data is saved.",Toast.LENGTH_LONG).show()
    }

    fun retrieveData(){

        sharedPreferences = this.getSharedPreferences("saveData",Context.MODE_PRIVATE)

        //for each sharedPreferences.get() method, we define the key and the default value of the variable
        name = sharedPreferences.getString("key name",null)
        message = sharedPreferences.getString("key message", null)
        count = sharedPreferences.getInt("key count", 0)
        isChecked = sharedPreferences.getBoolean("key remember", false)

        //and we display the retreived data on to the view of the application
        userName.setText(name)
        userMessage.setText(message)
        counter.setText("" + count)
        remember.isChecked = isChecked!!

    }

    //its best to run the sharedPreferences saved data in the onResume() method since it is the method that
    //reruns the app after a switch or pause in the application
    override fun onResume() {
        super.onResume()

        retrieveData()
    }
}