package com.example.kotlinquiz

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinquiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val questions = arrayOf(
        "How do you insert comments in Kotlin code?",
        "In Kotlin, how do you declare a read-only variable?",
        "What is the Kotlin equivalent of a `switch` statement in Java?",
        "To create an array in Kotlin, which of the following do you use?",
        "Which keyword is used to declare a function?"
    )
    private val options = arrayOf(
        arrayOf("// This is a comment", "/* This is a comment */", "//* This is a comment"),
        arrayOf("Using `var`", "Using `val`", "Using `const`"),
        arrayOf("`switch`", "`case`", "`when`"),
        arrayOf("arrayOf()", "Array[]", "new Array()"),
        arrayOf("func", "fun", "function")
    )
    private val correctAnswers = arrayOf(0, 1, 2, 0, 1)
    private var currentQuestionIndex = 0
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize views
        val restartSpinner = findViewById<Spinner>(R.id.restartSpinner)

        // Create ArrayAdapter with default options
        val defaultOptions = arrayOf("Select an option", "Restart", "End")
        val adapter = ArrayAdapter(this, R.layout.spinner_item, defaultOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        restartSpinner.adapter = adapter

        restartSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                // Handle selection based on position
                when (position) {
                    1 -> restartQuiz()
                    2 -> finish()
                }
                parent.setSelection(0) // Reset selection to "Select an option"
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Handle nothing selected event if needed
            }
        }

        // Setup Button Click Listeners
        binding.option1Button.setOnClickListener {
            checkAnswer(0)
        }

        binding.option2Button.setOnClickListener {
            checkAnswer(1)
        }

        binding.option3Button.setOnClickListener {
            checkAnswer(2)
        }

        // Display first question
        displayQuestion()
    }

    private fun correctButtonColors(buttonIndex: Int) {
        when (buttonIndex) {
            0 -> binding.option1Button.setBackgroundColor(Color.GREEN)
            1 -> binding.option2Button.setBackgroundColor(Color.GREEN)
            2 -> binding.option3Button.setBackgroundColor(Color.GREEN)
        }
    }

    private fun wrongButtonColors(buttonIndex: Int) {
        when (buttonIndex) {
            0 -> binding.option1Button.setBackgroundColor(Color.RED)
            1 -> binding.option2Button.setBackgroundColor(Color.RED)
            2 -> binding.option3Button.setBackgroundColor(Color.RED)
        }
    }

    private fun resetButtonColors() {
        binding.option1Button.setBackgroundColor(Color.rgb(50, 59, 96))
        binding.option2Button.setBackgroundColor(Color.rgb(50, 59, 96))
        binding.option3Button.setBackgroundColor(Color.rgb(50, 59, 96))
    }

    private fun showResults() {
        binding.questionLayout.visibility = View.GONE
        binding.resultText.visibility = View.VISIBLE
        binding.restartSpinner.visibility = View.VISIBLE
        binding.resultText.text = "Your score: $score out of ${questions.size}"
    }

    private fun displayQuestion() {
        binding.questionLayout.visibility = View.VISIBLE
        binding.resultText.visibility = View.GONE
        binding.restartSpinner.visibility = View.GONE
        binding.questionText.text = questions[currentQuestionIndex]
        binding.option1Button.text = options[currentQuestionIndex][0]
        binding.option2Button.text = options[currentQuestionIndex][1]
        binding.option3Button.text = options[currentQuestionIndex][2]
        resetButtonColors()
    }

    private fun checkAnswer(selectedAnswerIndex: Int) {
        val correctAnswerIndex = correctAnswers[currentQuestionIndex]
        if (selectedAnswerIndex == correctAnswerIndex) {
            score++
            correctButtonColors(selectedAnswerIndex)
        } else {
            wrongButtonColors(selectedAnswerIndex)
            correctButtonColors(correctAnswerIndex)
        }
        if (currentQuestionIndex < questions.size - 1) {
            currentQuestionIndex++
            binding.questionText.postDelayed({ displayQuestion() }, 1000)
        } else {
            showResults()
        }
    }

    private fun restartQuiz() {
        currentQuestionIndex = 0
        score = 0
        displayQuestion()
    }
}
