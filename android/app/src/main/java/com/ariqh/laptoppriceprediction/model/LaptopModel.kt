package com.ariqh.laptoppriceprediction.model

data class LaptopModel(
    val company: String,
    val cpu: String,
    val ram: Int,
    val gpu: String,
    val opsys: String,
    val weight: Double,
    val hdd: Int,
    val ssd: Int,
)
