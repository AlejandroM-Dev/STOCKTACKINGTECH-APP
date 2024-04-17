package com.example.stocktakingtech.IO.response

import com.example.stocktakingtech.Model.Step


data class DistanceResponse (
        val distance : Int,
        val steps : List<Step>,
        val time : String
)