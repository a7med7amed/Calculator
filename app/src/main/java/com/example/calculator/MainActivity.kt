package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var result = ""
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding : ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.displayGreetingView.setText(intent.getStringExtra("EXTRA_MESSAGE"))
        val toast = Toast.makeText(this, intent.getStringExtra("EXTRA_MESSAGE"), Toast.LENGTH_LONG)
        toast.show()

        writeCharacter(character = "1", button = binding.button1, binding = binding)
        writeCharacter(character = "2", button = binding.button2, binding = binding)
        writeCharacter(character = "3", button = binding.button3, binding = binding)
        writeCharacter(character = "4", button = binding.button4, binding = binding)
        writeCharacter(character = "5", button = binding.button5, binding = binding)
        writeCharacter(character = "6", button = binding.button6, binding = binding)
        writeCharacter(character = "7", button = binding.button7, binding = binding)
        writeCharacter(character = "8", button = binding.button8, binding = binding)
        writeCharacter(character = "9", button = binding.button9, binding = binding)
        writeCharacter(character = "0", button = binding.buttonZero, binding = binding)
        writeCharacter(character = ".", button = binding.buttonDot, binding = binding)
        writeCharacter(character = "+", button = binding.buttonPlus, binding = binding)
        writeCharacter(character = "-", button = binding.buttonMinus, binding = binding)
        writeCharacter(character = "/", button = binding.buttonDivide, binding = binding)
        writeCharacter(character = "*", button = binding.buttonMultiple, binding = binding)
        equal(binding = binding)
        clear(binding = binding)
        backspace(binding = binding)


    }
    private fun writeCharacter (character: String, button: Button, binding : ActivityMainBinding){
        button.setOnClickListener {
            result = "$result$character"
            binding.displayTextView.setText(result)
        }
    }
    private fun clear (binding : ActivityMainBinding){
        binding.buttonClear.setOnClickListener {
            result = ""
            binding.displayTextView.setText("0")
        }
    }
    private fun backspace (binding : ActivityMainBinding){
        binding.buttonDel.setOnClickListener {
            result = result.dropLast(1)
            if (result.isEmpty()){
                binding.displayTextView.setText("0")
            }
            else{
                binding.displayTextView.setText(result)
            }

        }
    }
    private fun equal (binding : ActivityMainBinding){
        binding.buttonEqual.setOnClickListener {
            if (!Character.isDigit(result.first()) || !Character.isDigit(result.last()) || containsLetters(result)){
                result = "invalid input"
            }
            else{
                result = solveEquation(result).toString()
            }
            binding.displayTextView.setText(result)
        }

    }

    private fun solveEquation(equation: String): Double {
        val sanitizedEquation = equation.replace(" ", "") // Remove any whitespace

        val numbers = mutableListOf<Double>()
        val operators = mutableListOf<Char>()

        var currentNumber = StringBuilder()

        for (c in sanitizedEquation) {
            if (c.isDigit() || c == '.') {
                currentNumber.append(c)
            } else {
                if (currentNumber.isNotEmpty()) {
                    val number = currentNumber.toString().toDouble()
                    numbers.add(number)
                    currentNumber.clear()
                }

                if (isOperator(c)) {
                    while (operators.isNotEmpty() && hasHigherPrecedence(operators.last(), c)) {
                        val operator = operators.removeAt(operators.lastIndex)
                        val rightOperand = numbers.removeAt(numbers.lastIndex)
                        val leftOperand = numbers.removeAt(numbers.lastIndex)
                        val result = applyOperator(leftOperand, rightOperand, operator)
                        numbers.add(result)
                    }
                    operators.add(c)
                } else if (c == '(') {
                    operators.add(c)
                } else if (c == ')') {
                    while (operators.isNotEmpty() && operators.last() != '(') {
                        val operator = operators.removeAt(operators.lastIndex)
                        val rightOperand = numbers.removeAt(numbers.lastIndex)
                        val leftOperand = numbers.removeAt(numbers.lastIndex)
                        val result = applyOperator(leftOperand, rightOperand, operator)
                        numbers.add(result)
                    }
                    operators.removeAt(operators.lastIndex) // Remove the corresponding '('
                }
            }
        }

        if (currentNumber.isNotEmpty()) {
            val number = currentNumber.toString().toDouble()
            numbers.add(number)
        }

        while (operators.isNotEmpty()) {
            val operator = operators.removeAt(operators.lastIndex)
            val rightOperand = numbers.removeAt(numbers.lastIndex)
            val leftOperand = numbers.removeAt(numbers.lastIndex)
            val result = applyOperator(leftOperand, rightOperand, operator)
            numbers.add(result)
        }

        return numbers.first()
    }

    private fun isOperator(c: Char): Boolean {
        return c == '+' || c == '-' || c == '*' || c == '/'
    }

    private fun hasHigherPrecedence(operator1: Char, operator2: Char): Boolean {
        return (operator1 == '*' || operator1 == '/') && (operator2 == '+' || operator2 == '-')
    }

    private fun applyOperator(leftOperand: Double, rightOperand: Double, operator: Char): Double {
        return when (operator) {
            '+' -> leftOperand + rightOperand
            '-' -> leftOperand - rightOperand
            '*' -> leftOperand * rightOperand
            '/' -> leftOperand / rightOperand
            else -> throw IllegalArgumentException("Invalid operator: $operator")
        }
    }
    private fun containsLetters(str: String): Boolean {
        for (c in str) {
            if (c.isLetter()) {
                return true
            }
        }
        return false
    }




}
