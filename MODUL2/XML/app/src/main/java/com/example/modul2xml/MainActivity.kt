package com.example.modul2xml

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etTagihan = findViewById<EditText>(R.id.etTagihan)
        val spinnerTip = findViewById<Spinner>(R.id.spinnerTip)
        val switchBulat = findViewById<Switch>(R.id.switchBulat)
        val btnHitung = findViewById<Button>(R.id.btnHitung)
        val tvHasil = findViewById<TextView>(R.id.tvHasil)

        val pilihanPersentase = arrayOf("15%", "18%", "20%")
        val nilaiPersentase = arrayOf(0.15, 0.18, 0.20)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, pilihanPersentase)
        spinnerTip.adapter = adapter

        btnHitung.setOnClickListener {
            val inputAngka = etTagihan.text.toString()
            val tagihan = inputAngka.toDoubleOrNull() ?: 0.0

            val posisiTerpilih = spinnerTip.selectedItemPosition

            val persentaseTip = nilaiPersentase[posisiTerpilih]

            var jumlahTip = tagihan * persentaseTip

            if (switchBulat.isChecked) {
                jumlahTip = ceil(jumlahTip)
            }

            tvHasil.text = String.format("Jumlah Tip: $%.2f", jumlahTip)
        }
    }
}