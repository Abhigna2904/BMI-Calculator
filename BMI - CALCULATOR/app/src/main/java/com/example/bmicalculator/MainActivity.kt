package com.example.bmicalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.content.ContextCompat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val feetInput = findViewById<EditText>(R.id.editTextFeet)
        val inchesInput = findViewById<EditText>(R.id.editTextInches)
        val weightInput = findViewById<EditText>(R.id.editTextWeight)
        val calculateButton = findViewById<Button>(R.id.buttonCalculate)
        val clearButton = findViewById<Button>(R.id.buttonClear)
        val resultText = findViewById<TextView>(R.id.textViewResult)
        val genderGroup = findViewById<RadioGroup>(R.id.genderGroup)

        calculateButton.setOnClickListener {

            val feetStr = feetInput.text.toString()
            val inchesStr = inchesInput.text.toString()
            val weightStr = weightInput.text.toString()

            if (feetStr.isEmpty() || inchesStr.isEmpty() || weightStr.isEmpty()) {
                Toast.makeText(
                    this,
                    getString(R.string.enter_all_values),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val feet = feetStr.toFloat()
            val inches = inchesStr.toFloat()
            val weight = weightStr.toFloat()

            val totalInches = (feet * 12) + inches
            val heightInMeters = totalInches * 0.0254

            val bmi = weight / (heightInMeters * heightInMeters)

            val bmiFormatted = String.format(
                Locale.getDefault(),
                "%.2f",
                bmi
            )

            val category = when {
                bmi < 18.5 -> getString(R.string.underweight)
                bmi < 25 -> getString(R.string.normal)
                bmi < 30 -> getString(R.string.overweight)
                else -> getString(R.string.obese)
            }

            val color = when {
                bmi < 18.5 -> android.R.color.holo_blue_dark
                bmi < 25 -> android.R.color.holo_green_dark
                bmi < 30 -> android.R.color.holo_orange_dark
                else -> android.R.color.holo_red_dark
            }

            val selectedGenderId = genderGroup.checkedRadioButtonId
            val gender = if (selectedGenderId != -1) {
                findViewById<RadioButton>(selectedGenderId).text.toString()
            } else {
                getString(R.string.others)
            }

            resultText.text = getString(
                R.string.bmi_result,
                bmiFormatted,
                category,
                gender
            )

            resultText.setTextColor(
                ContextCompat.getColor(this, color)
            )
        }

        clearButton.setOnClickListener {
            feetInput.text.clear()
            inchesInput.text.clear()
            weightInput.text.clear()
            resultText.text = ""
            genderGroup.clearCheck()
        }
    }
}
