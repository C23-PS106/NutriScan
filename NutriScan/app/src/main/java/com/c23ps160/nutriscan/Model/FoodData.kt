package com.c23ps160.nutriscan.Model

import android.graphics.Bitmap
import java.io.Serializable

class FoodData : Serializable {
    var image: Bitmap? = null
    var foodClass: String? = null
    var confidence: Float = 0f
}
