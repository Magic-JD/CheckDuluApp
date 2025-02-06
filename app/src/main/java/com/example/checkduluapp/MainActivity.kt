package com.example.checkduluapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Random
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstantState: Bundle?){
        super.onCreate(savedInstantState)
        setContentView(R.layout.activity_main)

        val rollButton = findViewById<Button>(R.id.rollButton)
        val resultsTextView = findViewById<TextView>(R.id.resultsTextView)
        val barcodeInput = findViewById<EditText>(R.id.barcodeInput)
        val amountInGrams = findViewById<EditText>(R.id.amountInput)

        rollButton.setOnClickListener {
            getInformation(barcodeInput.text.toString(), amountInGrams.text.toString().toDouble(), resultsTextView)
        }
    }

    private fun getInformation(barcode: String, numberOfGrams: Double, resultsTextView: TextView) {
        RetrofitClient.apiService.getInformationMobile(barcode, numberOfGrams).enqueue(object : Callback<InformationResponse> {
            override fun onResponse(call: Call<InformationResponse>, response: Response<InformationResponse>) {
                if (response.isSuccessful) {
                    val informationResponse = response.body()
                    // Handle the response
                    resultsTextView.text = "Product Name:\n${informationResponse?.productName}\n\nSugar per ${numberOfGrams}g: ${String.format("%.1f", informationResponse?.sugarPerGivenAmount)}g"
                } else {
                    // Handle the error
                    resultsTextView.text = "Product could not be found"
                }
            }

            override fun onFailure(call: Call<InformationResponse>, t: Throwable) {
                // Handle network failure
                resultsTextView.text = "Network Error: ${t.message}"
            }
        })
    }
}
