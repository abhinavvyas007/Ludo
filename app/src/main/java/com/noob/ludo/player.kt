package com.noob.ludo

import android.graphics.Color
import android.media.Image
import android.text.BoringLayout
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*

class player {
    var positoin: Int = 0

    var a : Int = 0
    var a_round : Boolean = false
    var inside_a: Boolean = false
    var turn = 0
    var a_won : Boolean = false
    var b_won : Boolean = false
    var c_won : Boolean = false
    var d_won : Boolean = false
    var b : Int = 0
    var b_round : Boolean = false
    var inside_b : Boolean = false
    var c : Int = 0
    var c_round : Boolean = false
    var inside_c: Boolean = false
    var d : Int = 0
    var d_round : Boolean = false
    var inside_d : Boolean = false
    var greet: String = "Welcome"
    var color = Color.BLUE
    var play_icon = R.drawable.red_icon
    var temp : Int = 1
    var kill_greet : String = "Red's Turn"
    var kill_color = Color.RED
}