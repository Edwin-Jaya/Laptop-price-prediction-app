package com.ariqh.laptoppriceprediction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.ariqh.laptoppriceprediction.databinding.ActivityMainBinding
import com.ariqh.laptoppriceprediction.model.LaptopModel
import com.ariqh.laptoppriceprediction.model.PredictResponse
import com.ariqh.laptoppriceprediction.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val TAG: String = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    var cpu = "";
    var gpu = "";
    var opsys = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListener()
    }

    private fun setupListener() {
        binding.btnPredict.setOnClickListener {
            if(validate()) {
                storePredict()
            } else {
                showMessage("Please enter data correctly!")
            }
        }
        binding.spCpu.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    cpu = parent!!.getItemAtPosition(position).toString()
                } else {
                    cpu = ""
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        binding.spGpu.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    gpu = parent!!.getItemAtPosition(position).toString()
                } else {
                    gpu = ""
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        binding.spOpsys.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    opsys = parent!!.getItemAtPosition(position).toString()
                } else {
                    opsys = ""
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun validate(): Boolean {
        if(binding.edtCompany.text.isNullOrEmpty()
            || cpu.isEmpty()
            || binding.edtRam.text.isNullOrEmpty()
            || gpu.isEmpty()
            || opsys.isEmpty()
            || binding.edtWeight.text.isNullOrEmpty()
            || binding.edtHdd.text.isNullOrEmpty()
            || binding.edtSsd.text.isNullOrEmpty()) {
            return false
        }

        return true
    }

    private fun storePredict() {
        val laptop : LaptopModel = LaptopModel(
            binding.edtCompany.text.toString(),
            cpu,
            binding.edtRam.text.toString().toInt(),
            gpu,
            opsys,
            binding.edtWeight.text.toString().toDouble(),
            binding.edtHdd.text.toString().toInt(),
            binding.edtSsd.text.toString().toInt()
        )

        ApiService().endPoint.storePredict(
            laptop.company,
            laptop.cpu,
            laptop.ram,
            laptop.gpu,
            laptop.opsys,
            laptop.weight,
            laptop.hdd,
            laptop.ssd)
            .enqueue(object: Callback<PredictResponse> {
                override fun onResponse(
                    call: Call<PredictResponse>,
                    response: Response<PredictResponse>
                ) {
                    if(response.isSuccessful) {
                        val price = response.body()!!.harga
                        binding.tvResult.text = "Price : ${price}"
                        showMessage("Predict price is ${price}")
                    }
                }

                override fun onFailure(call: Call<PredictResponse>, t: Throwable) {
                    Log.d(TAG, "errorResponse : $t")
                    showMessage(t.toString())
                }
            })
    }

    private fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}