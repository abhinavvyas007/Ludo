package com.noob.ludo

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.two_activity.*
import java.util.*
import kotlin.concurrent.schedule
import kotlin.random.Random

class TwoActivity : AppCompatActivity() {
    var audio = MediaPlayer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.two_activity)

        val greet = arrayOf("Green's Turn", "Red's Turn")
        var red = player()
        red.play_icon = R.drawable.red_icon
        red.greet = greet[0]
        red.color = Color.GREEN
        red.turn = 0
        red.kill_greet = "Red's Turn"
        red.kill_color = Color.RED

        var green = player()
        green.play_icon = R.drawable.green_icon
        green.greet = greet[1]
        green.color = Color.RED
        green.turn = 1
        green.kill_greet = "Green's Turn"
        green.kill_color = Color.GREEN


        val dice_image = listOf(
            R.drawable.one,
            R.drawable.two,
            R.drawable.three,
            R.drawable.four,
            R.drawable.five,
            R.drawable.six
        )
        var tiles = arrayOf(
            pos_1, pos_2, pos_3, pos_4, pos_5, pos_6, pos_7, pos_8, pos_9, pos_10,
            pos_11, pos_12, pos_13, pos_14, pos_15, pos_16, pos_17, pos_18, pos_19, pos_20,
            pos_21, pos_22, pos_23, pos_24, pos_25, pos_26, pos_27, pos_28, pos_29, pos_30,
            pos_31, pos_32, pos_33, pos_34, pos_35, pos_36, pos_37, pos_38, pos_39, pos_40,
            pos_41, pos_42, pos_43, pos_44, pos_45, pos_46, pos_47, pos_48, pos_49, pos_50,
            pos_51, pos_52
        )
        var win_block = arrayOf(r_6, g_6)
        var g_win = arrayOf(g_1, g_2, g_3, g_4, g_5)
        var r_win = arrayOf(r_1, r_2, r_3, r_4, r_5)


        var mp: MediaPlayer = MediaPlayer.create(this, R.raw.dice_sound)
        var chance = 0
        var num_on_dice = 6
        var chance_temp: Int = 0
        var anim: Animation = AnimationUtils.loadAnimation(this, R.anim.rotate)

        val bundle = intent.extras
        val sound_is_playing = bundle!!.getBoolean("my_boolean_key")

        var dead : MediaPlayer = MediaPlayer.create(this, R.raw.death)
        audio = MediaPlayer.create(this, R.raw.back_audio)
        audio.setLooping(true)
        if (sound_is_playing) {
            audio.start()
            sound.setBackgroundResource(R.drawable.sound_on)
        }
        else if (!sound_is_playing) {
            audio.pause()
            sound.setBackgroundResource(R.drawable.sound_off)
        }
        sound.setOnClickListener {
            if (audio.isPlaying()) {
                audio.pause()
                sound.setBackgroundResource(R.drawable.sound_off)
            } else {
                audio.start()
                sound.setBackgroundResource(R.drawable.sound_on)
            }
        }
        fun winner(play: player) {
            if (play.a_won == true && play.b_won == true && play.c_won == true && play.d_won == true) {
                var intent = Intent(this, winneractivity::class.java)
                startActivity(intent)
            }
        }

        fun move(play: player, num_dice: Int) {
            chance_temp = chance

            if ((play.a == 0 || play.a_won || (play.inside_a && (play.a + num_dice > 5))) && (play.b == 0 || play.b_won || (play.inside_b && (play.b + num_dice > 5))) && (play.c == 0 || play.c_won || (play.inside_c && (play.c + num_dice > 5))) && (play.d == 0 || play.d_won || (play.inside_d && (play.d + num_dice > 5))) && num_dice != 5) {
                chance++
                if (chance > 1) {
                    chance = 0
                }
                player_chance.setTextColor(play.color)
                player_chance.setText(play.greet)
            }
            if ((play.a_won || (play.inside_a && (play.a + num_dice > 5))) && (play.b_won || (play.inside_b && (play.b + num_dice > 5))) && (play.c_won || (play.inside_c && (play.c + num_dice > 5))) && (play.d_won || (play.inside_d && (play.d + num_dice > 5))) && num_dice == 5) {
                chance++
                if (chance > 1) {
                    chance = 0
                }
                player_chance.setTextColor(play.color)
                player_chance.setText(play.greet)
            }
            if (play.a == -1) {

            } else if (play == red && play.a == 51 && num_dice == 5) {
                tiles[play.a - 1].setOnClickListener {
                    tiles[play.a - 1].setImageResource(0)
                    tiles[play.a - 1].setOnClickListener(null)
                    play.inside_a = true
                    play.a_won = true
                    win_block[play.turn].setImageResource(play.play_icon)
                    winner(red)
                    play.a = -1
                    if (num_dice == 5) {
                        play.temp = 1
                    }
                    red_a.setOnClickListener(null)
                    red_b.setOnClickListener(null)
                    red_c.setOnClickListener(null)
                    red_d.setOnClickListener(null)
                }
            } else if (play == green && play.a == 25 && num_dice == 5) {
                tiles[play.a - 1].setOnClickListener {
                    tiles[play.a - 1].setImageResource(0)
                    tiles[play.a - 1].setOnClickListener(null)
                    play.inside_a = true
                    play.a_won = true
                    win_block[play.turn].setImageResource(play.play_icon)
                    winner(green)
                    play.a = -1
                    if (num_dice == 5) {
                        play.temp = 1
                    }
                    green_a.setOnClickListener(null)
                    green_b.setOnClickListener(null)
                    green_c.setOnClickListener(null)
                    green_d.setOnClickListener(null)
                }
            } else if (play == red && play.inside_a == true && (play.a + num_dice < 6) && !play.a_won) {
                r_win[play.a - 1].setOnClickListener {
                    r_win[play.a - 1].setOnClickListener(null)
                    if ((play.inside_b && play.a == play.b) || (play.inside_c && play.a == play.c) || (play.inside_d && play.a == play.d)) {

                    } else {
                        r_win[play.a - 1].setImageResource(0)
                    }
                    if (play.a + num_dice == 5) {
                        play.a_won = true
                        win_block[play.turn].setImageResource(play.play_icon)
                        winner(red)
                        play.a = -1
                        play.temp = 1
                    } else {
                        play.a = play.a + num_dice + 1
                        r_win[play.a - 1].setImageResource(play.play_icon)
                        if (num_dice != 5) {
                            chance++
                            if (chance > 1) {
                                chance = 0
                            }
                            player_chance.setTextColor(play.color)
                            player_chance.setText(play.greet)
                        }
                    }
                    if (num_dice == 5) {
                        play.temp = 1
                    }
                }
            } else if (play == green && play.inside_a == true && (play.a + num_dice < 6) && !play.a_won) {
                g_win[play.a - 1].setOnClickListener {
                    g_win[play.a - 1].setOnClickListener(null)
                    if ((play.inside_b && play.a == play.b) || (play.inside_c && play.a == play.c) || (play.inside_d && play.a == play.d)) {

                    } else {
                        g_win[play.a - 1].setImageResource(0)
                    }
                    if (play.a + num_dice == 5) {
                        play.a_won = true
                        win_block[play.turn].setImageResource(play.play_icon)
                        winner(green)
                        play.a = -1
                        play.temp = 1
                    } else {
                        if (num_dice != 5) {
                            chance++
                            if (chance > 1) {
                                chance = 0
                            }
                        }
                        player_chance.setTextColor(play.color)
                        player_chance.setText(play.greet)
                        play.a = play.a + num_dice + 1
                        g_win[play.a - 1].setImageResource(play.play_icon)

                    }
                    if (num_dice == 5) {
                        play.temp = 1
                    }
                }
            } else if (play.a > 0 && !play.inside_a) {
                tiles[play.a - 1].setOnClickListener {
                    if (num_dice == 5) {
                        play.temp = 1
                    }
                    if (play == green && (play.a + num_dice > 24) && play.a_round == true) {
                        if (num_dice != 5) {
                            chance++
                            if (chance > 1) {
                                chance = 0
                            }
                            player_chance.setTextColor(play.color)
                            player_chance.setText(play.greet)
                        }
                        if ((!play.inside_b && play.a == play.b) || (!play.inside_c && play.a == play.c) || (!play.inside_d && play.a == play.d)) {

                        } else {
                            tiles[play.a - 1].setImageResource(0)
                        }
                        if (play.a != -1 && (play.a == 1 || play.a == 14 || play.a == 27 || play.a == 40 || play.a == 9 || play.a == 22 || play.a == 35 || play.a == 48)) {
                            if (play != red && ((play.a == red.a && !red.inside_a) || (play.a == red.b && !red.inside_b) || (play.a == red.c && !red.inside_c) || (play.a == red.d && !red.inside_d))) {
                                tiles[play.a - 1].setImageResource(red.play_icon)
                            }
                            if (play != green && ((play.a == green.a && !green.inside_a) || (play.a == green.b && !green.inside_b) || (play.a == green.c && !green.inside_c) || (play.a == green.d && !green.inside_d))) {
                                tiles[play.a - 1].setImageResource(green.play_icon)
                            }
                        }
                        if (play.a > 0) {
                            tiles[play.a - 1].setOnClickListener(null)
                        }
                        if (play.b > 0) {
                            tiles[play.b - 1].setOnClickListener(null)
                        }
                        if (play.c > 0) {
                            tiles[play.c - 1].setOnClickListener(null)
                        }
                        if (play.d > 0) {
                            tiles[play.d - 1].setOnClickListener(null)
                        }
                        play.a = ((play.a + num_dice) % 24)
                        g_win[play.a - 1].setImageResource(play.play_icon)
                        play.inside_a = true

                        if (play == red) {
                            red_a.setOnClickListener(null)
                            red_b.setOnClickListener(null)
                            red_c.setOnClickListener(null)
                            red_d.setOnClickListener(null)
                        } else if (play == green) {
                            green_a.setOnClickListener(null)
                            green_b.setOnClickListener(null)
                            green_c.setOnClickListener(null)
                            green_d.setOnClickListener(null)
                        }
                    } else if (play == red && (play.a + num_dice > 50)) {
                        if (num_dice != 5) {
                            chance++
                            if (chance > 1) {
                                chance = 0
                            }
                            player_chance.setTextColor(play.color)
                            player_chance.setText(play.greet)
                        }

                        if ((!play.inside_b && play.a == play.b) || (!play.inside_c && play.a == play.c) || (!play.inside_d && play.a == play.d)) {

                        } else {
                            tiles[play.a - 1].setImageResource(0)
                        }
                        if (play.a != -1 && (play.a == 1 || play.a == 14 || play.a == 27 || play.a == 40 || play.a == 9 || play.a == 22 || play.a == 35 || play.a == 48)) {
                            if (play != red && ((play.a == red.a && !red.inside_a) || (play.a == red.b && !red.inside_b) || (play.a == red.c && !red.inside_c) || (play.a == red.d && !red.inside_d))) {
                                tiles[play.a - 1].setImageResource(red.play_icon)
                            }
                            if (play != green && ((play.a == green.a && !green.inside_a) || (play.a == green.b && !green.inside_b) || (play.a == green.c && !green.inside_c) || (play.a == green.d && !green.inside_d))) {
                                tiles[play.a - 1].setImageResource(green.play_icon)
                            }
                        }
                        if (play.a > 0) {
                            tiles[play.a - 1].setOnClickListener(null)
                        }
                        if (play.b > 0) {
                            tiles[play.b - 1].setOnClickListener(null)
                        }
                        if (play.c > 0) {
                            tiles[play.c - 1].setOnClickListener(null)
                        }
                        if (play.d > 0) {
                            tiles[play.d - 1].setOnClickListener(null)
                        }
                        play.a = ((play.a + num_dice) % 50)
                        r_win[play.a - 1].setImageResource(play.play_icon)
                        play.inside_a = true

                        if (play == red) {
                            red_a.setOnClickListener(null)
                            red_b.setOnClickListener(null)
                            red_c.setOnClickListener(null)
                            red_d.setOnClickListener(null)
                        } else if (play == green) {
                            green_a.setOnClickListener(null)
                            green_b.setOnClickListener(null)
                            green_c.setOnClickListener(null)
                            green_d.setOnClickListener(null)
                        }
                    } else if (play.a + num_dice > 51) {
                        if (num_dice != 5) {
                            chance++
                            if (chance > 1) {
                                chance = 0
                            }
                            player_chance.setTextColor(play.color)
                            player_chance.setText(play.greet)
                        }
                        if ((!play.inside_b && play.a == play.b) || (!play.inside_c && play.a == play.c) || (!play.inside_d && play.a == play.d)) {

                        } else {
                            tiles[play.a - 1].setImageResource(0)
                        }
                        if (play.a != -1 && (play.a == 1 || play.a == 14 || play.a == 27 || play.a == 40 || play.a == 9 || play.a == 22 || play.a == 35 || play.a == 48)) {
                            if (play != red && ((play.a == red.a && !red.inside_a) || (play.a == red.b && !red.inside_b) || (play.a == red.c && !red.inside_c) || (play.a == red.d && !red.inside_d))) {
                                tiles[play.a - 1].setImageResource(red.play_icon)
                            }
                            if (play != green && ((play.a == green.a && !green.inside_a) || (play.a == green.b && !green.inside_b) || (play.a == green.c && !green.inside_c) || (play.a == green.d && !green.inside_d))) {
                                tiles[play.a - 1].setImageResource(green.play_icon)
                            }

                        }
                        if (play.a > 0) {
                            tiles[play.a - 1].setOnClickListener(null)
                        }
                        if (play.b > 0) {
                            tiles[play.b - 1].setOnClickListener(null)
                        }
                        if (play.c > 0) {
                            tiles[play.c - 1].setOnClickListener(null)
                        }
                        if (play.d > 0) {
                            tiles[play.d - 1].setOnClickListener(null)
                        }
                        play.a = ((play.a + num_dice) % 51)
                        tiles[play.a - 1].setImageResource(play.play_icon)
                        play.a_round = true

                        if (play == red) {
                            red_a.setOnClickListener(null)
                            red_b.setOnClickListener(null)
                            red_c.setOnClickListener(null)
                            red_d.setOnClickListener(null)
                        } else if (play == green) {
                            green_a.setOnClickListener(null)
                            green_b.setOnClickListener(null)
                            green_c.setOnClickListener(null)
                            green_d.setOnClickListener(null)
                        }
                    } else {
                        if ((!play.inside_b && play.a == play.b) || (!play.inside_c && play.a == play.c) || (!play.inside_d && play.a == play.d)) {

                        } else {
                            tiles[play.a - 1].setImageResource(0)
                        }
                        if (play.a != -1 && (play.a == 1 || play.a == 14 || play.a == 27 || play.a == 40 || play.a == 9 || play.a == 22 || play.a == 35 || play.a == 48)) {
                            if (play != red && ((play.a == red.a && !red.inside_a) || (play.a == red.b && !red.inside_b) || (play.a == red.c && !red.inside_c) || (play.a == red.d && !red.inside_d))) {
                                tiles[play.a - 1].setImageResource(red.play_icon)
                            }
                            if (play != green && ((play.a == green.a && !green.inside_a) || (play.a == green.b && !green.inside_b) || (play.a == green.c && !green.inside_c) || (play.a == green.d && !green.inside_d))) {
                                tiles[play.a - 1].setImageResource(green.play_icon)
                            }
                        }
                        tiles[play.a + num_dice].setImageResource(play.play_icon)
                        
                        if (num_dice != 5) {
                            chance++
                            if (chance > 1) {
                                chance = 0
                            }
                            player_chance.setTextColor(play.color)
                            player_chance.setText(play.greet)
                        }
                        if (play.a > 0) {
                            tiles[play.a - 1].setOnClickListener(null)
                        }
                        if (play.b > 0) {
                            tiles[play.b - 1].setOnClickListener(null)
                        }
                        if (play.c > 0) {
                            tiles[play.c - 1].setOnClickListener(null)
                        }
                        if (play.d > 0) {
                            tiles[play.d - 1].setOnClickListener(null)
                        }
                        play.a = play.a + num_dice + 1

                        if (play == red) {
                            red_a.setOnClickListener(null)
                            red_b.setOnClickListener(null)
                            red_c.setOnClickListener(null)
                            red_d.setOnClickListener(null)
                        } else if (play == green) {
                            green_a.setOnClickListener(null)
                            green_b.setOnClickListener(null)
                            green_c.setOnClickListener(null)
                            green_d.setOnClickListener(null)
                        }
                    }
                    if (play.a != -1 && play.a != 1 && play.a != 14 && play.a != 27 && play.a != 40 && play.a != 9 && play.a != 22 && play.a != 35 && play.a != 48) {
                        if (play != red && (play.a == red.a && !red.inside_a)) {
                            red_a.setImageResource(R.drawable.red_icon)
                            red.a = 0
                            red_a.setOnClickListener(null)
                            red.a_round = false
                            chance = chance_temp
                            play.temp = 1
                            player_chance.setTextColor(play.kill_color)
                            player_chance.setText(play.kill_greet)
                            dead.start()
                        }
                        if (play != red && play.a == red.b && !red.inside_b) {
                            red_b.setImageResource(R.drawable.red_icon)
                            red.b = 0
                            red_b.setOnClickListener(null)
                            red.b_round = false
                            chance = chance_temp
                            play.temp = 1
                            player_chance.setTextColor(play.kill_color)
                            player_chance.setText(play.kill_greet)
                            dead.start()
                        }
                        if (play != red && play.a == red.c && !red.inside_c) {
                            red_c.setImageResource(R.drawable.red_icon)
                            red.c = 0
                            red_c.setOnClickListener(null)
                            red.c_round = false
                            chance = chance_temp
                            play.temp = 1
                            player_chance.setTextColor(play.kill_color)
                            player_chance.setText(play.kill_greet)
                            dead.start()
                        }
                        if (play != red && play.a == red.d && !red.inside_d) {
                            red_d.setImageResource(R.drawable.red_icon)
                            red.d = 0
                            red_d.setOnClickListener(null)
                            red.d_round = false
                            chance = chance_temp
                            play.temp = 1
                            player_chance.setTextColor(play.kill_color)
                            player_chance.setText(play.kill_greet)
                            dead.start()
                        }
                        if (play != green && (play.a == green.a && !green.inside_a)) {
                            green_a.setImageResource(R.drawable.green_icon)
                            green.a = 0
                            green_a.setOnClickListener(null)
                            green.a_round = false
                            chance = chance_temp
                            play.temp = 1
                            player_chance.setTextColor(play.kill_color)
                            player_chance.setText(play.kill_greet)
                            dead.start()
                        }
                        if (play != green && play.a == green.b && !green.inside_b) {
                            green_b.setImageResource(R.drawable.green_icon)
                            green.b = 0
                            green_b.setOnClickListener(null)
                            green.b_round = false
                            chance = chance_temp
                            play.temp = 1
                            player_chance.setTextColor(play.kill_color)
                            player_chance.setText(play.kill_greet)
                            dead.start()
                        }
                        if (play != green && play.a == green.c && !green.inside_c) {
                            green_c.setImageResource(R.drawable.green_icon)
                            green.c = 0
                            green_c.setOnClickListener(null)
                            green.c_round = false
                            chance = chance_temp
                            play.temp = 1
                            player_chance.setTextColor(play.kill_color)
                            player_chance.setText(play.kill_greet)
                            dead.start()
                        }
                        if (play != green && play.a == green.d && !green.inside_d) {
                            green_d.setImageResource(R.drawable.green_icon)
                            green.d = 0
                            green_d.setOnClickListener(null)
                            green.d_round = false
                            chance = chance_temp
                            play.temp = 1
                            player_chance.setTextColor(play.kill_color)
                            player_chance.setText(play.kill_greet)
                            dead.start()
                        }
                    }
                }
            }
            if (play.b == -1) {

            } else if (play == red && play.b == 51 && num_dice == 5) {
                tiles[play.b - 1].setOnClickListener {
                    tiles[play.b - 1].setImageResource(0)
                    tiles[play.b - 1].setOnClickListener(null)
                    play.inside_b = true
                    win_block[play.turn].setImageResource(play.play_icon)
                    winner(red)
                    play.b_won = true
                    play.b = -1
                    if (num_dice == 5) {
                        play.temp = 1
                    }
                    red_a.setOnClickListener(null)
                    red_b.setOnClickListener(null)
                    red_c.setOnClickListener(null)
                    red_d.setOnClickListener(null)
                }
            } else if (play == green && play.b == 25 && num_dice == 5) {
                tiles[play.b - 1].setOnClickListener {
                    tiles[play.b - 1].setImageResource(0)
                    tiles[play.b - 1].setOnClickListener(null)
                    play.inside_b = true
                    play.b_won = true
                    win_block[play.turn].setImageResource(play.play_icon)
                    winner(green)
                    play.b = -1
                    if (num_dice == 5) {
                        play.temp = 1
                    }
                    green_a.setOnClickListener(null)
                    green_b.setOnClickListener(null)
                    green_c.setOnClickListener(null)
                    green_d.setOnClickListener(null)
                }
            } else if (play == red && play.inside_b == true && (play.b + num_dice < 6) && !play.b_won) {
                r_win[play.b - 1].setOnClickListener {
                    r_win[play.b - 1].setOnClickListener(null)
                    if ((play.inside_a && play.b == play.a) || (play.inside_c && play.b == play.c) || (play.inside_d && play.b == play.d)) {

                    } else {
                        r_win[play.b - 1].setImageResource(0)
                    }
                    if (play.b + num_dice == 5) {
                        win_block[play.turn].setImageResource(play.play_icon)
                        winner(red)
                        play.b_won = true
                        play.b = -1
                        play.temp = 1
                    } else {
                        if (num_dice != 5) {
                            chance++
                            if (chance > 1) {
                                chance = 0
                            }
                        }
                        player_chance.setTextColor(play.color)
                        player_chance.setText(play.greet)
                        play.b = play.b + num_dice + 1
                        r_win[play.b - 1].setImageResource(play.play_icon)
                    }
                    if (num_dice == 5) {
                        play.temp = 1
                    }
                }
            } else if (play == green && play.inside_b == true && (play.b + num_dice < 6) && !play.b_won) {
                g_win[play.b - 1].setOnClickListener {
                    g_win[play.b - 1].setOnClickListener(null)
                    if ((play.inside_a && play.b == play.a) || (play.inside_c && play.b == play.c) || (play.inside_d && play.b == play.d)) {

                    } else {
                        g_win[play.b - 1].setImageResource(0)
                    }
                    if (play.b + num_dice == 5) {
                        play.b_won = true
                        win_block[play.turn].setImageResource(play.play_icon)
                        winner(green)
                        play.b = -1
                        play.temp = 1
                    } else {
                        if (num_dice != 5) {
                            chance++
                            if (chance > 1) {
                                chance = 0
                            }
                        }
                        player_chance.setTextColor(play.color)
                        player_chance.setText(play.greet)
                        play.b = play.b + num_dice + 1
                        g_win[play.b - 1].setImageResource(play.play_icon)
                    }
                    if (num_dice == 5) {
                        play.temp = 1
                    }
                }
            } else if (play.b > 0 && !play.inside_b) {
                tiles[play.b - 1].setOnClickListener {
                    if (num_dice == 5) {
                        play.temp = 1
                    }
                    if (play == green && (play.b + num_dice > 24) && (play.b_round == true)) {
                        if (num_dice != 5) {
                            chance++
                            if (chance > 1) {
                                chance = 0
                            }
                            player_chance.setTextColor(play.color)
                            player_chance.setText(play.greet)
                        }

                        if ((!play.inside_a && play.b == play.a) || (!play.inside_c && play.b == play.c) || (!play.inside_d && play.b == play.d)) {

                        } else {
                            tiles[play.b - 1].setImageResource(0)
                        }
                        if (play.b != -1 && (play.b == 1 || play.b == 14 || play.b == 27 || play.b == 40 || play.b == 9 || play.b == 22 || play.b == 35 || play.b == 48)) {
                            if (play != red && ((play.b == red.a && !red.inside_a) || (play.b == red.b && !red.inside_b) || (play.b == red.c && !red.inside_c) || (play.b == red.d && !red.inside_d))) {
                                tiles[play.b - 1].setImageResource(red.play_icon)
                            }
                            if (play != green && ((play.b == green.a && !green.inside_a) || (play.b == green.b && !green.inside_b) || (play.b == green.c && !green.inside_c) || (play.b == green.d && !green.inside_d))) {
                                tiles[play.b - 1].setImageResource(green.play_icon)
                            }
                        }

                        if (play.a > 0) {
                            tiles[play.a - 1].setOnClickListener(null)
                        }
                        if (play.b > 0) {
                            tiles[play.b - 1].setOnClickListener(null)
                        }
                        if (play.c > 0) {
                            tiles[play.c - 1].setOnClickListener(null)
                        }
                        if (play.d > 0) {
                            tiles[play.d - 1].setOnClickListener(null)
                        }
                        play.b = ((play.b + num_dice) % 24)
                        g_win[play.b - 1].setImageResource(play.play_icon)
                        play.inside_b = true

                        if (play == red) {
                            red_a.setOnClickListener(null)
                            red_b.setOnClickListener(null)
                            red_c.setOnClickListener(null)
                            red_d.setOnClickListener(null)
                        } else if (play == green) {
                            green_a.setOnClickListener(null)
                            green_b.setOnClickListener(null)
                            green_c.setOnClickListener(null)
                            green_d.setOnClickListener(null)
                        }
                    } else if (play == red && (play.b + num_dice > 50)) {
                        if (num_dice != 5) {
                            chance++
                            if (chance > 1) {
                                chance = 0
                            }
                            player_chance.setTextColor(play.color)
                            player_chance.setText(play.greet)
                        }
                        if ((!play.inside_a && play.b == play.a) || (!play.inside_c && play.b == play.c) || (!play.inside_d && play.b == play.d)) {

                        } else {
                            tiles[play.b - 1].setImageResource(0)
                        }
                        if (play.b != -1 && (play.b == 1 || play.b == 14 || play.b == 27 || play.b == 40 || play.b == 9 || play.b == 22 || play.b == 35 || play.b == 48)) {
                            if (play != red && ((play.b == red.a && !red.inside_a) || (play.b == red.b && !red.inside_b) || (play.b == red.c && !red.inside_c) || (play.b == red.d && !red.inside_d))) {
                                tiles[play.b - 1].setImageResource(red.play_icon)
                            }
                            if (play != green && ((play.b == green.a && !green.inside_a) || (play.b == green.b && !green.inside_b) || (play.b == green.c && !green.inside_c) || (play.b == green.d && !green.inside_d))) {
                                tiles[play.b - 1].setImageResource(green.play_icon)
                            }
                        }
                        if (play.a > 0) {
                            tiles[play.a - 1].setOnClickListener(null)
                        }
                        if (play.b > 0) {
                            tiles[play.b - 1].setOnClickListener(null)
                        }
                        if (play.c > 0) {
                            tiles[play.c - 1].setOnClickListener(null)
                        }
                        if (play.d > 0) {
                            tiles[play.d - 1].setOnClickListener(null)
                        }
                        play.b = ((play.b + num_dice) % 50)
                        r_win[play.b - 1].setImageResource(play.play_icon)
                        play.inside_b = true

                        if (play == red) {
                            red_a.setOnClickListener(null)
                            red_b.setOnClickListener(null)
                            red_c.setOnClickListener(null)
                            red_d.setOnClickListener(null)
                        } else if (play == green) {
                            green_a.setOnClickListener(null)
                            green_b.setOnClickListener(null)
                            green_c.setOnClickListener(null)
                            green_d.setOnClickListener(null)
                        }
                    } else if (play.b + num_dice > 51) {
                        if (num_dice != 5) {
                            chance++
                            if (chance > 1) {
                                chance = 0
                            }
                        }
                        if ((!play.inside_a && play.b == play.a) || (!play.inside_c && play.b == play.c) || (!play.inside_d && play.b == play.d)) {

                        } else {
                            tiles[play.b - 1].setImageResource(0)
                        }
                        if (play.b != -1 && (play.b == 1 || play.b == 14 || play.b == 27 || play.b == 40 || play.b == 9 || play.b == 22 || play.b == 35 || play.b == 48)) {
                            if (play != red && ((play.b == red.a && !red.inside_a) || (play.b == red.b && !red.inside_b) || (play.b == red.c && !red.inside_c) || (play.b == red.d && !red.inside_d))) {
                                tiles[play.b - 1].setImageResource(red.play_icon)
                            }
                            if (play != green && ((play.b == green.a && !green.inside_a) || (play.b == green.b && !green.inside_b) || (play.b == green.c && !green.inside_c) || (play.b == green.d && !green.inside_d))) {
                                tiles[play.b - 1].setImageResource(green.play_icon)
                            }

                        }
                        if (play.a > 0) {
                            tiles[play.a - 1].setOnClickListener(null)
                        }
                        if (play.b > 0) {
                            tiles[play.b - 1].setOnClickListener(null)
                        }
                        if (play.c > 0) {
                            tiles[play.c - 1].setOnClickListener(null)
                        }
                        if (play.d > 0) {
                            tiles[play.d - 1].setOnClickListener(null)
                        }
                        play.b = ((play.b + num_dice) % 51)
                        tiles[play.b - 1].setImageResource(play.play_icon)
                        play.b_round = true

                        if (play == red) {
                            red_a.setOnClickListener(null)
                            red_b.setOnClickListener(null)
                            red_c.setOnClickListener(null)
                            red_d.setOnClickListener(null)
                        } else if (play == green) {
                            green_a.setOnClickListener(null)
                            green_b.setOnClickListener(null)
                            green_c.setOnClickListener(null)
                            green_d.setOnClickListener(null)
                        }
                    } else {
                        if ((!play.inside_a && play.b == play.a) || (!play.inside_c && play.b == play.c) || (!play.inside_d && play.b == play.d)) {

                        } else {
                            tiles[play.b - 1].setImageResource(0)
                        }
                        if (play.b != -1 && (play.b == 1 || play.b == 14 || play.b == 27 || play.b == 40 || play.b == 9 || play.b == 22 || play.b == 35 || play.b == 48)) {
                            if (play != red && ((play.b == red.a && !red.inside_a) || (play.b == red.b && !red.inside_b) || (play.b == red.c && !red.inside_c) || (play.b == red.d && !red.inside_d))) {
                                tiles[play.b - 1].setImageResource(red.play_icon)
                            }
                            if (play != green && ((play.b == green.a && !green.inside_a) || (play.b == green.b && !green.inside_b) || (play.b == green.c && !green.inside_c) || (play.b == green.d && !green.inside_d))) {
                                tiles[play.b - 1].setImageResource(green.play_icon)
                            }

                        }
                        tiles[play.b + num_dice].setImageResource(play.play_icon)
                        if (num_dice != 5) {
                            chance++
                            if (chance > 1) {
                                chance = 0
                            }
                            player_chance.setTextColor(play.color)
                            player_chance.setText(play.greet)
                        }
                        if (play.a > 0) {
                            tiles[play.a - 1].setOnClickListener(null)
                        }
                        if (play.b > 0) {
                            tiles[play.b - 1].setOnClickListener(null)
                        }
                        if (play.c > 0) {
                            tiles[play.c - 1].setOnClickListener(null)
                        }
                        if (play.d > 0) {
                            tiles[play.d - 1].setOnClickListener(null)
                        }
                        play.b = play.b + num_dice + 1
                        if (play == red) {
                            red_a.setOnClickListener(null)
                            red_b.setOnClickListener(null)
                            red_c.setOnClickListener(null)
                            red_d.setOnClickListener(null)
                        } else if (play == green) {
                            green_a.setOnClickListener(null)
                            green_b.setOnClickListener(null)
                            green_c.setOnClickListener(null)
                            green_d.setOnClickListener(null)
                        }
                    }
                    if (play.b != -1 && play.b != 1 && play.b != 14 && play.b != 27 && play.b != 40 && play.b != 9 && play.b != 22 && play.b != 35 && play.b != 48) {

                        if (play != red && (play.b == red.a) && !red.inside_a) {
                            red_a.setImageResource(R.drawable.red_icon)
                            red.a = 0
                            red_a.setOnClickListener(null)
                            red.a_round = false
                            chance = chance_temp
                            play.temp = 1
                            player_chance.setTextColor(play.kill_color)
                            player_chance.setText(play.kill_greet)
                            dead.start()
                        }
                        if (play != red && play.b == red.b && !red.inside_b) {
                            red_b.setImageResource(R.drawable.red_icon)
                            red.b = 0
                            red_b.setOnClickListener(null)
                            red.b_round = false
                            chance = chance_temp
                            play.temp = 1
                            player_chance.setTextColor(play.kill_color)
                            player_chance.setText(play.kill_greet)
                            dead.start()
                        }
                        if (play != red && play.b == red.c && !red.inside_c) {
                            red_c.setImageResource(R.drawable.red_icon)
                            red.c = 0
                            red_c.setOnClickListener(null)
                            red.c_round = false
                            chance = chance_temp
                            play.temp = 1
                            player_chance.setTextColor(play.kill_color)
                            player_chance.setText(play.kill_greet)
                            dead.start()
                        }
                        if (play != red && play.b == red.d && !red.inside_d) {
                            red_d.setImageResource(R.drawable.red_icon)
                            red.d = 0
                            red_d.setOnClickListener(null)
                            red.d_round = false
                            chance = chance_temp
                            play.temp = 1
                            player_chance.setTextColor(play.kill_color)
                            player_chance.setText(play.kill_greet)
                            dead.start()
                        }
                        if (play != green && (play.b == green.a) && !green.inside_a) {
                            green_a.setImageResource(R.drawable.green_icon)
                            green.a = 0
                            green_a.setOnClickListener(null)
                            green.a_round = false
                            chance = chance_temp
                            play.temp = 1
                            player_chance.setTextColor(play.kill_color)
                            player_chance.setText(play.kill_greet)
                            dead.start()
                        }
                        if (play != green && play.b == green.b && !green.inside_b) {
                            green_b.setImageResource(R.drawable.green_icon)
                            green.b = 0
                            green_b.setOnClickListener(null)
                            green.b_round = false
                            chance = chance_temp
                            play.temp = 1
                            player_chance.setTextColor(play.kill_color)
                            player_chance.setText(play.kill_greet)
                            dead.start()
                        }
                        if (play != green && play.b == green.c && !green.inside_c) {
                            green_c.setImageResource(R.drawable.green_icon)
                            green.c = 0
                            green_c.setOnClickListener(null)
                            green.c_round = false
                            chance = chance_temp
                            play.temp = 1
                            player_chance.setTextColor(play.kill_color)
                            player_chance.setText(play.kill_greet)
                            dead.start()
                        }
                        if (play != green && play.b == green.d && !green.inside_d) {
                            green_d.setImageResource(R.drawable.green_icon)
                            green.d = 0
                            green_d.setOnClickListener(null)
                            green.d_round = false
                            chance = chance_temp
                            play.temp = 1
                            player_chance.setTextColor(play.kill_color)
                            player_chance.setText(play.kill_greet)
                            dead.start()
                        }
                    }
                }
            }
            if (play.c == -1) {

            } else if (play == red && play.c == 51 && num_dice == 5) {
                tiles[play.c - 1].setOnClickListener {
                    tiles[play.c - 1].setImageResource(0)
                    tiles[play.c - 1].setOnClickListener(null)
                    play.inside_c = true
                    play.c_won = true
                    win_block[play.turn].setImageResource(play.play_icon)
                    winner(red)
                    play.c = -1
                    if (num_dice == 5) {
                        play.temp = 1
                    }
                }
            } else if (play == green && play.c == 25 && num_dice == 5) {
                tiles[play.c - 1].setOnClickListener {
                    tiles[play.c - 1].setImageResource(0)
                    tiles[play.c - 1].setOnClickListener(null)
                    play.inside_c = true
                    play.c_won = true
                    win_block[play.turn].setImageResource(play.play_icon)
                    winner(green)
                    play.c = -1
                    if (num_dice == 5) {
                        play.temp = 1
                    }
                }
            } else if (play == red && play.inside_c == true && (play.c + num_dice < 6) && !play.c_won) {
                r_win[play.c - 1].setOnClickListener {
                    r_win[play.c - 1].setOnClickListener(null)
                    if ((play.inside_a && play.c == play.a) || (play.inside_b && play.c == play.b) || (play.inside_d && play.c == play.d)) {

                    } else {
                        r_win[play.c - 1].setImageResource(0)
                    }
                    if (play.c + num_dice == 5) {
                        play.c_won = true
                        win_block[play.turn].setImageResource(play.play_icon)
                        winner(red)
                        play.c = -1
                        play.temp = 1
                    } else {
                        if (num_dice != 5) {
                            chance++
                            if (chance > 1) {
                                chance = 0
                            }
                            player_chance.setTextColor(play.color)
                            player_chance.setText(play.greet)
                        }
                        play.c = play.c + num_dice + 1
                        r_win[play.c - 1].setImageResource(play.play_icon)
                    }
                    if (num_dice == 5) {
                        play.temp = 1
                    }
                }
            } else if (play == green && play.inside_c == true && (play.c + num_dice < 6) && !play.c_won) {
                g_win[play.c - 1].setOnClickListener {
                    g_win[play.c - 1].setOnClickListener(null)
                    if ((play.inside_a && play.c == play.a) || (play.inside_b && play.c == play.b) || (play.inside_d && play.c == play.d)) {

                    } else {
                        g_win[play.c - 1].setImageResource(0)
                    }
                    if (play.c + num_dice == 5) {
                        play.c_won = true
                        win_block[play.turn].setImageResource(play.play_icon)
                        winner(green)
                        play.c = -1
                        play.temp = 1
                    } else {
                        if (num_dice != 5) {
                            chance++
                            if (chance > 1) {
                                chance = 0
                            }
                            player_chance.setTextColor(play.color)
                            player_chance.setText(play.greet)
                        }
                        play.c = play.c + num_dice + 1
                        g_win[play.c - 1].setImageResource(play.play_icon)
                    }
                    if (num_dice == 5) {
                        play.temp = 1
                    }
                }
            } else if (play.c > 0 && !play.inside_c) {
                tiles[play.c - 1].setOnClickListener {
                    if (num_dice == 5) {
                        play.temp = 1
                    }
                    if (play == green && (play.c + num_dice > 24) && play.c_round == true) {
                        if (num_dice != 5) {
                            chance++
                            if (chance > 1) {
                                chance = 0
                            }
                            player_chance.setTextColor(play.color)
                            player_chance.setText(play.greet)
                        }
                        if ((!play.inside_a && play.c == play.a) || (!play.inside_b && play.c == play.b) || (!play.inside_d && play.c == play.d)) {

                        } else {
                            tiles[play.c - 1].setImageResource(0)
                        }
                        if (play.c != -1 && (play.c == 1 || play.c == 14 || play.c == 27 || play.c == 40 || play.c == 9 || play.c == 22 || play.c == 35 || play.c == 48)) {
                            if (play != red && ((play.c == red.a && !red.inside_a) || (play.c == red.b && !red.inside_b) || (play.c == red.c && !red.inside_c) || (play.c == red.d && !red.inside_d))) {
                                tiles[play.c - 1].setImageResource(red.play_icon)
                            }
                            if (play != green && ((play.c == green.a && !green.inside_a) || (play.c == green.b && !green.inside_b) || (play.c == green.c && !green.inside_c) || (play.c == green.d && !green.inside_d))) {
                                tiles[play.c - 1].setImageResource(green.play_icon)
                            }
                        }
                        if (play.a > 0) {
                            tiles[play.a - 1].setOnClickListener(null)
                        }
                        if (play.b > 0) {
                            tiles[play.b - 1].setOnClickListener(null)
                        }
                        if (play.c > 0) {
                            tiles[play.c - 1].setOnClickListener(null)
                        }
                        if (play.d > 0) {
                            tiles[play.d - 1].setOnClickListener(null)
                        }
                        play.c = ((play.c + num_dice) % 24)
                        g_win[play.c - 1].setImageResource(play.play_icon)
                        play.inside_c = true

                        if (play == red) {
                            red_a.setOnClickListener(null)
                            red_b.setOnClickListener(null)
                            red_c.setOnClickListener(null)
                            red_d.setOnClickListener(null)
                        } else if (play == green) {
                            green_a.setOnClickListener(null)
                            green_b.setOnClickListener(null)
                            green_c.setOnClickListener(null)
                            green_d.setOnClickListener(null)
                        }
                    } else if (play == red && (play.c + num_dice > 50)) {
                        if (num_dice != 5) {
                            chance++
                            if (chance > 1) {
                                chance = 0
                            }
                            player_chance.setTextColor(play.color)
                            player_chance.setText(play.greet)
                        }
                        if ((!play.inside_a && play.c == play.a) || (!play.inside_b && play.c == play.b) || (!play.inside_d && play.c == play.d)) {

                        } else {
                            tiles[play.c - 1].setImageResource(0)
                        }
                        if (play.c != -1 && (play.c == 1 || play.c == 14 || play.c == 27 || play.c == 40 || play.c == 9 || play.c == 22 || play.c == 35 || play.c == 48)) {
                            if (play != red && ((play.c == red.a && !red.inside_a) || (play.c == red.b && !red.inside_b) || (play.c == red.c && !red.inside_c) || (play.c == red.d && !red.inside_d))) {
                                tiles[play.c - 1].setImageResource(red.play_icon)
                            }
                            if (play != green && ((play.c == green.a && !green.inside_a) || (play.c == green.b && !green.inside_b) || (play.c == green.c && !green.inside_c) || (play.c == green.d && !green.inside_d))) {
                                tiles[play.c - 1].setImageResource(green.play_icon)
                            }
                        }
                        if (play.a > 0) {
                            tiles[play.a - 1].setOnClickListener(null)
                        }
                        if (play.b > 0) {
                            tiles[play.b - 1].setOnClickListener(null)
                        }
                        if (play.c > 0) {
                            tiles[play.c - 1].setOnClickListener(null)
                        }
                        if (play.d > 0) {
                            tiles[play.d - 1].setOnClickListener(null)
                        }
                        play.c = ((play.c + num_dice) % 50)
                        r_win[play.c - 1].setImageResource(play.play_icon)
                        play.inside_c = true

                        if (play == red) {
                            red_a.setOnClickListener(null)
                            red_b.setOnClickListener(null)
                            red_c.setOnClickListener(null)
                            red_d.setOnClickListener(null)
                        } else if (play == green) {
                            green_a.setOnClickListener(null)
                            green_b.setOnClickListener(null)
                            green_c.setOnClickListener(null)
                            green_d.setOnClickListener(null)
                        }
                    } else if (play.c + num_dice > 51) {
                        if (num_dice != 5) {
                            chance++
                            if (chance > 1) {
                                chance = 0
                            }
                            player_chance.setTextColor(play.color)
                            player_chance.setText(play.greet)
                        }
                        play.c_round = true
                        if ((!play.inside_a && play.c == play.a) || (!play.inside_b && play.c == play.b) || (!play.inside_d && play.c == play.d)) {

                        } else {
                            tiles[play.c - 1].setImageResource(0)
                        }
                        if (play.c != -1 && (play.c == 1 || play.c == 14 || play.c == 27 || play.c == 40 || play.c == 9 || play.c == 22 || play.c == 35 || play.c == 48)) {
                            if (play != red && ((play.c == red.a && !red.inside_a) || (play.c == red.b && !red.inside_b) || (play.c == red.c && !red.inside_c) || (play.c == red.d && !red.inside_d))) {
                                tiles[play.c - 1].setImageResource(red.play_icon)
                            }
                            if (play != green && ((play.c == green.a && !green.inside_a) || (play.c == green.b && !green.inside_b) || (play.c == green.c && !green.inside_c) || (play.c == green.d && !green.inside_d))) {
                                tiles[play.c - 1].setImageResource(green.play_icon)
                            }

                        }
                        if (play.a > 0) {
                            tiles[play.a - 1].setOnClickListener(null)
                        }
                        if (play.b > 0) {
                            tiles[play.b - 1].setOnClickListener(null)
                        }
                        if (play.c > 0) {
                            tiles[play.c - 1].setOnClickListener(null)
                        }
                        if (play.d > 0) {
                            tiles[play.d - 1].setOnClickListener(null)
                        }
                        play.c = ((play.c + num_dice) % 51)
                        tiles[play.c - 1].setImageResource(play.play_icon)

                        if (play == red) {
                            red_a.setOnClickListener(null)
                            red_b.setOnClickListener(null)
                            red_c.setOnClickListener(null)
                            red_d.setOnClickListener(null)
                        } else if (play == green) {
                            green_a.setOnClickListener(null)
                            green_b.setOnClickListener(null)
                            green_c.setOnClickListener(null)
                            green_d.setOnClickListener(null)
                        }
                    } else {
                        if ((!play.inside_a && play.c == play.a) || (!play.inside_b && play.c == play.b) || (!play.inside_d && play.c == play.d)) {

                        } else {
                            tiles[play.c - 1].setImageResource(0)
                        }
                        if (play.c != -1 && (play.c == 1 || play.c == 14 || play.c == 27 || play.c == 40 || play.c == 9 || play.c == 22 || play.c == 35 || play.c == 48)) {
                            if (play != red && ((play.c == red.a && !red.inside_a) || (play.c == red.b && !red.inside_b) || (play.c == red.c && !red.inside_c) || (play.c == red.d && !red.inside_d))) {
                                tiles[play.c - 1].setImageResource(red.play_icon)
                            }
                            if (play != green && ((play.c == green.a && !green.inside_a) || (play.c == green.b && !green.inside_b) || (play.c == green.c && !green.inside_c) || (play.c == green.d && !green.inside_d))) {
                                tiles[play.c - 1].setImageResource(green.play_icon)
                            }

                        }
                        tiles[play.c + num_dice].setImageResource(play.play_icon)
                        if (play.a > 0) {
                            tiles[play.a - 1].setOnClickListener(null)
                        }
                        if (play.b > 0) {
                            tiles[play.b - 1].setOnClickListener(null)
                        }
                        if (play.c > 0) {
                            tiles[play.c - 1].setOnClickListener(null)
                        }
                        if (play.d > 0) {
                            tiles[play.d - 1].setOnClickListener(null)
                        }
                        if (num_dice != 5) {
                            chance++
                            if (chance > 1) {
                                chance = 0
                            }
                            player_chance.setTextColor(play.color)
                            player_chance.setText(play.greet)
                        }
                        play.c = play.c + num_dice + 1


                        if (play == red) {
                            red_a.setOnClickListener(null)
                            red_b.setOnClickListener(null)
                            red_c.setOnClickListener(null)
                            red_d.setOnClickListener(null)
                        } else if (play == green) {
                            green_a.setOnClickListener(null)
                            green_b.setOnClickListener(null)
                            green_c.setOnClickListener(null)
                            green_d.setOnClickListener(null)
                        }
                    }
                    if (play.c != -1 && play.c != 1 && play.c != 14 && play.c != 27 && play.c != 40 && play.c != 9 && play.c != 22 && play.c != 35 && play.c != 48) {


                        if (play != red && (play.c == red.a) && !red.inside_a) {
                            red_a.setImageResource(R.drawable.red_icon)
                            red.a = 0
                            red_a.setOnClickListener(null)
                            red.a_round = false
                            chance = chance_temp
                            play.temp = 1
                            player_chance.setTextColor(play.kill_color)
                            player_chance.setText(play.kill_greet)
                            dead.start()
                        }
                        if (play != red && play.c == red.b && !red.inside_b) {
                            red_b.setImageResource(R.drawable.red_icon)
                            red.b = 0
                            red_b.setOnClickListener(null)
                            red.b_round = false
                            chance = chance_temp
                            play.temp = 1
                            player_chance.setTextColor(play.kill_color)
                            player_chance.setText(play.kill_greet)
                            dead.start()
                        }
                        if (play != red && play.c == red.c && !red.inside_c) {
                            red_c.setImageResource(R.drawable.red_icon)
                            red.c = 0
                            red_c.setOnClickListener(null)
                            red.c_round = false
                            chance = chance_temp
                            play.temp = 1
                            player_chance.setTextColor(play.kill_color)
                            player_chance.setText(play.kill_greet)
                            dead.start()
                        }
                        if (play != red && play.c == red.d && !red.inside_d) {
                            red_d.setImageResource(R.drawable.red_icon)
                            red.d = 0
                            red_d.setOnClickListener(null)
                            red.d_round = false
                            chance = chance_temp
                            play.temp = 1
                            player_chance.setTextColor(play.kill_color)
                            player_chance.setText(play.kill_greet)
                            dead.start()
                        }
                        if (play != green && (play.c == green.a) && !green.inside_a) {
                            green_a.setImageResource(R.drawable.green_icon)
                            green.a = 0
                            green_a.setOnClickListener(null)
                            green.a_round = false
                            chance = chance_temp
                            play.temp = 1
                            player_chance.setTextColor(play.kill_color)
                            player_chance.setText(play.kill_greet)
                            dead.start()
                        }
                        if (play != green && play.c == green.b && !green.inside_b) {
                            green_b.setImageResource(R.drawable.green_icon)
                            green.b = 0
                            green.b_round = false
                            chance = chance_temp
                            play.temp = 1
                            player_chance.setTextColor(play.kill_color)
                            player_chance.setText(play.kill_greet)
                            dead.start()
                        }
                        if (play != green && play.c == green.c && !green.inside_c) {
                            green_c.setImageResource(R.drawable.green_icon)
                            green.c = 0
                            green_c.setOnClickListener(null)
                            green.c_round = false
                            chance = chance_temp
                            play.temp = 1
                            player_chance.setTextColor(play.kill_color)
                            player_chance.setText(play.kill_greet)
                            dead.start()
                        }
                        if (play != green && play.c == green.d && !green.inside_d) {
                            green_d.setImageResource(R.drawable.green_icon)
                            green.d = 0
                            green_d.setOnClickListener(null)
                            green.d_round = false
                            chance = chance_temp
                            play.temp = 1
                            player_chance.setTextColor(play.kill_color)
                            player_chance.setText(play.kill_greet)
                            dead.start()
                        }
                    }
                }
            }
            if (play.d == -1) {

            } else if (play == red && play.d == 51 && num_dice == 5) {
                tiles[play.d - 1].setOnClickListener {
                    tiles[play.d - 1].setImageResource(0)
                    tiles[play.d - 1].setOnClickListener(null)
                    play.inside_d = true
                    play.d_won = true
                    win_block[play.turn].setImageResource(play.play_icon)
                    winner(red)
                    play.d = -1
                    if (num_dice == 5) {
                        play.temp = 1
                    }
                }
            } else if (play == green && play.d == 25 && num_dice == 5) {
                tiles[play.d - 1].setOnClickListener {
                    tiles[play.d - 1].setImageResource(0)
                    tiles[play.d - 1].setOnClickListener(null)
                    play.inside_d = true
                    play.d_won = true
                    win_block[play.turn].setImageResource(play.play_icon)
                    winner(green)
                    play.d = -1
                    if (num_dice == 5) {
                        play.temp = 1
                    }
                }
            } else if (play == red && play.inside_d == true && (play.d + num_dice < 6) && !play.d_won) {
                r_win[play.d - 1].setOnClickListener {
                    r_win[play.d - 1].setOnClickListener(null)
                    if ((play.inside_a && play.d == play.a) || (play.inside_b && play.d == play.b) || (play.inside_c && play.d == play.c)) {

                    } else {
                        r_win[play.d - 1].setImageResource(0)
                    }
                    if (play.d + num_dice == 5) {
                        play.d_won = true
                        win_block[play.turn].setImageResource(play.play_icon)
                        winner(red)
                        play.d = -1
                        play.temp = 1
                    } else {
                        if (num_dice != 5) {
                            chance++
                            if (chance > 1) {
                                chance = 0
                            }
                            player_chance.setTextColor(play.color)
                            player_chance.setText(play.greet)
                        }
                        play.d = play.d + num_dice + 1
                        r_win[play.d - 1].setImageResource(play.play_icon)
                    }
                    if (num_dice == 5) {
                        play.temp = 1
                    }
                }
            } else if (play == green && play.inside_d == true && (play.d + num_dice < 6) && !play.d_won) {
                g_win[play.d - 1].setOnClickListener {
                    g_win[play.d - 1].setOnClickListener(null)
                    if ((play.inside_a && play.d == play.a) || (play.inside_b && play.d == play.b) || (play.inside_c && play.d == play.c)) {

                    } else {
                        g_win[play.d - 1].setImageResource(0)
                    }
                    if (play.d + num_dice == 5) {
                        play.d_won = true
                        win_block[play.turn].setImageResource(play.play_icon)
                        winner(green)
                        play.d = -1
                        play.temp = 1
                    } else {
                        if (num_dice != 5) {
                            chance++
                            if (chance > 1) {
                                chance = 0
                            }
                            player_chance.setTextColor(play.color)
                            player_chance.setText(play.greet)
                        }
                        play.d = play.d + num_dice + 1
                        g_win[play.d - 1].setImageResource(play.play_icon)
                    }
                    if (num_dice == 5) {
                        play.temp = 1
                    }
                }
            } else if (play.d > 0 && !play.inside_d) {
                tiles[play.d - 1].setOnClickListener {
                    if (num_dice == 5) {
                        play.temp = 1
                    }
                    if (play == green && (play.d + num_dice > 24) && (play.d_round == true)) {
                        if (num_dice != 5) {
                            chance++
                            if (chance > 1) {
                                chance = 0
                            }
                            player_chance.setTextColor(play.color)
                            player_chance.setText(play.greet)
                        }
                        if ((!play.inside_a && play.d == play.a) || (!play.inside_b && play.d == play.b) || (!play.inside_c && play.d == play.c)) {

                        } else {
                            tiles[play.d - 1].setImageResource(0)
                        }
                        if (play.d != -1 && (play.d == 1 || play.d == 14 || play.d == 27 || play.d == 40 || play.d == 9 || play.d == 22 || play.d == 35 || play.d == 48)) {
                            if (play != red && ((play.d == red.a && !red.inside_a) || (play.d == red.b && !red.inside_b) || (play.d == red.c && !red.inside_c) || (play.d == red.d && !red.inside_d))) {
                                tiles[play.d - 1].setImageResource(red.play_icon)
                            }
                            if (play != green && ((play.d == green.a && !green.inside_a) || (play.d == green.b && !green.inside_b) || (play.d == green.c && !green.inside_c) || (play.d == green.d && !green.inside_d))) {
                                tiles[play.d - 1].setImageResource(green.play_icon)
                            }
                        }
                        if (play.a > 0) {
                            tiles[play.a - 1].setOnClickListener(null)
                        }
                        if (play.b > 0) {
                            tiles[play.b - 1].setOnClickListener(null)
                        }
                        if (play.c > 0) {
                            tiles[play.c - 1].setOnClickListener(null)
                        }
                        if (play.d > 0) {
                            tiles[play.d - 1].setOnClickListener(null)
                        }
                        play.d = ((play.d + num_dice) % 24)
                        g_win[play.d - 1].setImageResource(play.play_icon)
                        play.inside_d = true

                        if (play == red) {
                            red_a.setOnClickListener(null)
                            red_b.setOnClickListener(null)
                            red_c.setOnClickListener(null)
                            red_d.setOnClickListener(null)
                        } else if (play == green) {
                            green_a.setOnClickListener(null)
                            green_b.setOnClickListener(null)
                            green_c.setOnClickListener(null)
                            green_d.setOnClickListener(null)
                        }
                    } else if (play == red && (play.d + num_dice > 50)) {
                        if (num_dice != 5) {
                            chance++
                            if (chance > 1) {
                                chance = 0
                            }
                            player_chance.setTextColor(play.color)
                            player_chance.setText(play.greet)
                        }
                        if ((!play.inside_a && play.d == play.a) || (!play.inside_b && play.d == play.b) || (!play.inside_c && play.d == play.c)) {

                        } else {
                            tiles[play.d - 1].setImageResource(0)
                        }
                        if (play.d != -1 && (play.d == 1 || play.d == 14 || play.d == 27 || play.d == 40 || play.d == 9 || play.d == 22 || play.d == 35 || play.d == 48)) {
                            if (play != red && ((play.d == red.a && !red.inside_a) || (play.d == red.b && !red.inside_b) || (play.d == red.c && !red.inside_c) || (play.d == red.d && !red.inside_d))) {
                                tiles[play.d - 1].setImageResource(red.play_icon)
                            }
                            if (play != green && ((play.d == green.a && !green.inside_a) || (play.d == green.b && !green.inside_b) || (play.d == green.c && !green.inside_c) || (play.d == green.d && !green.inside_d))) {
                                tiles[play.d - 1].setImageResource(green.play_icon)
                            }

                        }
                        if (play.a > 0) {
                            tiles[play.a - 1].setOnClickListener(null)
                        }
                        if (play.b > 0) {
                            tiles[play.b - 1].setOnClickListener(null)
                        }
                        if (play.c > 0) {
                            tiles[play.c - 1].setOnClickListener(null)
                        }
                        if (play.d > 0) {
                            tiles[play.d - 1].setOnClickListener(null)
                        }
                        play.d = ((play.d + num_dice) % 50)
                        r_win[play.d - 1].setImageResource(play.play_icon)
                        play.inside_d = true

                        if (play == red) {
                            red_a.setOnClickListener(null)
                            red_b.setOnClickListener(null)
                            red_c.setOnClickListener(null)
                            red_d.setOnClickListener(null)
                        } else if (play == green) {
                            green_a.setOnClickListener(null)
                            green_b.setOnClickListener(null)
                            green_c.setOnClickListener(null)
                            green_d.setOnClickListener(null)
                        }
                    } else if (play.d + num_dice > 51) {
                        if (num_dice != 5) {
                            chance++
                            if (chance > 1) {
                                chance = 0
                            }
                            player_chance.setTextColor(play.color)
                            player_chance.setText(play.greet)
                        }
                        play.d_round = true
                        if ((!play.inside_a && play.d == play.a) || (!play.inside_b && play.d == play.b) || (!play.inside_c && play.d == play.c)) {

                        } else {
                            tiles[play.d - 1].setImageResource(0)
                        }
                        if (play.d != -1 && (play.d == 1 || play.d == 14 || play.d == 27 || play.d == 40 || play.d == 9 || play.d == 22 || play.d == 35 || play.d == 48)) {
                            if (play != red && ((play.d == red.a && !red.inside_a) || (play.d == red.b && !red.inside_b) || (play.d == red.c && !red.inside_c) || (play.d == red.d && !red.inside_d))) {
                                tiles[play.d - 1].setImageResource(red.play_icon)
                            }
                            if (play != green && ((play.d == green.a && !green.inside_a) || (play.d == green.b && !green.inside_b) || (play.d == green.c && !green.inside_c) || (play.d == green.d && !green.inside_d))) {
                                tiles[play.d - 1].setImageResource(green.play_icon)
                            }

                        }
                        if (play.a > 0) {
                            tiles[play.a - 1].setOnClickListener(null)
                        }
                        if (play.b > 0) {
                            tiles[play.b - 1].setOnClickListener(null)
                        }
                        if (play.c > 0) {
                            tiles[play.c - 1].setOnClickListener(null)
                        }
                        if (play.d > 0) {
                            tiles[play.d - 1].setOnClickListener(null)
                        }
                        play.d = ((play.d + num_dice) % 51)
                        tiles[play.d - 1].setImageResource(play.play_icon)

                        if (play == red) {
                            red_a.setOnClickListener(null)
                            red_b.setOnClickListener(null)
                            red_c.setOnClickListener(null)
                            red_d.setOnClickListener(null)
                        } else if (play == green) {
                            green_a.setOnClickListener(null)
                            green_b.setOnClickListener(null)
                            green_c.setOnClickListener(null)
                            green_d.setOnClickListener(null)
                        }
                    } else {
                        if ((!play.inside_a && play.d == play.a) || (!play.inside_b && play.d == play.b) || (!play.inside_c && play.d == play.c)) {

                        } else {
                            tiles[play.d - 1].setImageResource(0)
                        }
                        if (play.d != -1 && (play.d == 1 || play.d == 14 || play.d == 27 || play.d == 40 || play.d == 9 || play.d == 22 || play.d == 35 || play.d == 48)) {
                            if (play != red && ((play.d == red.a && !red.inside_a) || (play.d == red.b && !red.inside_b) || (play.d == red.c && !red.inside_c) || (play.d == red.d && !red.inside_d))) {
                                tiles[play.d - 1].setImageResource(red.play_icon)
                            }
                            if (play != green && ((play.d == green.a && !green.inside_a) || (play.d == green.b && !green.inside_b) || (play.d == green.c && !green.inside_c) || (play.d == green.d && !green.inside_d))) {
                                tiles[play.d - 1].setImageResource(green.play_icon)
                            }

                        }
                        tiles[play.d + num_dice].setImageResource(play.play_icon)
                        if (num_dice != 5) {
                            chance++
                            if (chance > 1) {
                                chance = 0
                            }
                            player_chance.setTextColor(play.color)
                            player_chance.setText(play.greet)
                        }
                        if (play.a > 0) {
                            tiles[play.a - 1].setOnClickListener(null)
                        }
                        if (play.b > 0) {
                            tiles[play.b - 1].setOnClickListener(null)
                        }
                        if (play.c > 0) {
                            tiles[play.c - 1].setOnClickListener(null)
                        }
                        if (play.d > 0) {
                            tiles[play.d - 1].setOnClickListener(null)
                        }
                        play.d = play.d + num_dice + 1

                        if (play == red) {
                            red_a.setOnClickListener(null)
                            red_b.setOnClickListener(null)
                            red_c.setOnClickListener(null)
                            red_d.setOnClickListener(null)
                        } else if (play == green) {
                            green_a.setOnClickListener(null)
                            green_b.setOnClickListener(null)
                            green_c.setOnClickListener(null)
                            green_d.setOnClickListener(null)
                        }
                    }
                    if (play.d != -1 && play.d != 1 && play.d != 14 && play.d != 27 && play.d != 40 && play.d != 9 && play.d != 22 && play.d != 35 && play.d != 48) {

                        if (play != red && (play.d == red.a) && !red.inside_a) {
                            red_a.setImageResource(R.drawable.red_icon)
                            red_a.setOnClickListener(null)
                            red.a_round = false
                            chance = chance_temp
                            play.temp = 1
                            player_chance.setTextColor(play.kill_color)
                            player_chance.setText(play.kill_greet)
                            dead.start()
                        }
                        if (play != red && play.d == red.b && red.inside_b) {
                            red_b.setImageResource(R.drawable.red_icon)
                            red.b = 0
                            red_b.setOnClickListener(null)
                            red.b_round = false
                            chance = chance_temp
                            play.temp = 1
                            player_chance.setTextColor(play.kill_color)
                            player_chance.setText(play.kill_greet)
                            dead.start()
                        }
                        if (play != red && play.d == red.c && !red.inside_c) {
                            red_c.setImageResource(R.drawable.red_icon)
                            red.c = 0
                            red_c.setOnClickListener(null)
                            red.c_round = false
                            chance = chance_temp
                            play.temp = 1
                            player_chance.setTextColor(play.kill_color)
                            player_chance.setText(play.kill_greet)
                            dead.start()
                        }
                        if (play != red && play.d == red.d && !red.inside_c) {
                            red_d.setImageResource(R.drawable.red_icon)
                            red.d = 0
                            red_d.setOnClickListener(null)
                            red.d_round = false
                            chance = chance_temp
                            play.temp = 1
                            player_chance.setTextColor(play.kill_color)
                            player_chance.setText(play.kill_greet)
                            dead.start()
                        }
                        if (play != green && (play.d == green.a) && !green.inside_a) {
                            green_a.setImageResource(R.drawable.green_icon)
                            green.a = 0
                            green_a.setOnClickListener(null)
                            green.a_round = false
                            chance = chance_temp
                            play.temp = 1
                            player_chance.setTextColor(play.kill_color)
                            player_chance.setText(play.kill_greet)
                            dead.start()
                        }
                        if (play != green && play.d == green.b && !green.inside_b) {
                            green_b.setImageResource(R.drawable.green_icon)
                            green.b = 0
                            green_b.setOnClickListener(null)
                            green.b_round = false
                            chance = chance_temp
                            play.temp = 1
                            player_chance.setTextColor(play.kill_color)
                            player_chance.setText(play.kill_greet)
                            dead.start()
                        }
                        if (play != green && play.d == green.c && !green.inside_c) {
                            green_c.setImageResource(R.drawable.green_icon)
                            green.c = 0
                            green_c.setOnClickListener(null)
                            green.c_round = false
                            chance = chance_temp
                            play.temp = 1
                            player_chance.setTextColor(play.kill_color)
                            player_chance.setText(play.kill_greet)
                            dead.start()
                        }
                        if (play != green && play.d == green.d && !green.inside_d) {
                            green_d.setImageResource(R.drawable.green_icon)
                            green.d = 0
                            green_d.setOnClickListener(null)
                            green.d_round = false
                            chance = chance_temp
                            play.temp = 1
                            player_chance.setTextColor(play.kill_color)
                            player_chance.setText(play.kill_greet)
                            dead.start()
                        }
                    }
                }
            }
        }


        dice_red.setOnClickListener {
            if (chance == 0 && red.temp == 1) {
                red.temp = 0
                dice_red.startAnimation(anim)
                num_on_dice = Random.nextInt(6)
                dice_red.setBackgroundResource(dice_image[num_on_dice])
                mp.start()
                if (red.a == 0 && red.b == 0 && red.c == 0 && red.d == 0 && num_on_dice != 5) {
                    player_chance.setTextColor(Color.GREEN)
                    player_chance.setText("Green's Turn")
                }
                move(red, num_on_dice)
                if ((num_on_dice == 5 && red.a == 0) || (num_on_dice == 5 && red.b == 0) || (num_on_dice == 5 && red.c == 0) || (num_on_dice == 5 && red.d == 0)) {
                    red_a.setOnClickListener {
                        if (red.a == 0) {
                            red_a.setImageResource(R.drawable.back_no_bounds)
                            pos_1.setImageResource(R.drawable.red_icon)
                            if (red.a == -1) {

                            }
                            else if (red.a == 0) {
                                red_a.setOnClickListener(null)
                            }
                            else if (!red.inside_a) {
                                tiles[red.a - 1].setOnClickListener(null)
                            }
                            else if (red.inside_a) {
                                r_win[red.a - 1].setOnClickListener(null)
                            }
                            if (red.b == -1) {

                            }
                            else if (red.b == 0) {
                                red_b.setOnClickListener(null)
                            }
                            else if (!red.inside_b) {
                                tiles[red.b - 1].setOnClickListener(null)
                            }
                            else if (red.inside_b) {
                                r_win[red.b - 1].setOnClickListener(null)
                            }
                            if (red.c == -1) {

                            }
                            else if (red.c == 0) {
                                red_c.setOnClickListener(null)
                            }
                            else if (!red.inside_c) {
                                tiles[red.c - 1].setOnClickListener(null)
                            }
                            else if (red.inside_c) {
                                r_win[red.c - 1].setOnClickListener(null)
                            }
                            if (red.d == -1) {

                            }
                            else if (red.d == 0) {
                                red_d.setOnClickListener(null)
                            }
                            else if (!red.inside_d) {
                                tiles[red.d - 1].setOnClickListener(null)
                            }
                            else if (red.inside_d) {
                                r_win[red.d - 1].setOnClickListener(null)
                            }
                            red.a = 1
                            if (num_on_dice == 5) {
                                red.temp = 1
                            }
                        }
                    }

                    red_b.setOnClickListener {
                        if (red.b == 0) {
                            red_b.setImageResource(R.drawable.back_no_bounds)
                            pos_1.setImageResource(R.drawable.red_icon)
                            if (red.a == -1) {

                            }
                            else if (red.a == 0) {
                                red_a.setOnClickListener(null)
                            }
                            else if (!red.inside_a) {
                                tiles[red.a - 1].setOnClickListener(null)
                            }
                            else if (red.inside_a) {
                                r_win[red.a - 1].setOnClickListener(null)
                            }
                            if (red.b == -1) {

                            }
                            else if (red.b == 0) {
                                red_b.setOnClickListener(null)
                            }
                            else if (!red.inside_b) {
                                tiles[red.b - 1].setOnClickListener(null)
                            }
                            else if (red.inside_b) {
                                r_win[red.b - 1].setOnClickListener(null)
                            }
                            if (red.c == -1) {

                            }
                            else if (red.c == 0) {
                                red_c.setOnClickListener(null)
                            }
                            else if (!red.inside_c) {
                                tiles[red.c - 1].setOnClickListener(null)
                            }
                            else if (red.inside_c) {
                                r_win[red.c - 1].setOnClickListener(null)
                            }
                            if (red.d == -1) {

                            }
                            else if (red.d == 0) {
                                red_d.setOnClickListener(null)
                            }
                            else if (!red.inside_d) {
                                tiles[red.d - 1].setOnClickListener(null)
                            }
                            else if (red.inside_d) {
                                r_win[red.d - 1].setOnClickListener(null)
                            }
                            red.b = 1
                            if (num_on_dice == 5) {
                                red.temp = 1
                            }
                        }
                    }
                    red_c.setOnClickListener {
                        if (red.c == 0) {
                            red_c.setImageResource(R.drawable.back_no_bounds)
                            pos_1.setImageResource(R.drawable.red_icon)
                            if (red.a == -1) {

                            }
                            else if (red.a == 0) {
                                red_a.setOnClickListener(null)
                            }
                            else if (!red.inside_a) {
                                tiles[red.a - 1].setOnClickListener(null)
                            }
                            else if (red.inside_a) {
                                r_win[red.a - 1].setOnClickListener(null)
                            }
                            if (red.b == -1) {

                            }
                            else if (red.b == 0) {
                                red_b.setOnClickListener(null)
                            }
                            else if (!red.inside_b) {
                                tiles[red.b - 1].setOnClickListener(null)
                            }
                            else if (red.inside_b) {
                                r_win[red.b - 1].setOnClickListener(null)
                            }
                            if (red.c == -1) {

                            }
                            else if (red.c == 0) {
                                red_c.setOnClickListener(null)
                            }
                            else if (!red.inside_c) {
                                tiles[red.c - 1].setOnClickListener(null)
                            }
                            else if (red.inside_c) {
                                r_win[red.c - 1].setOnClickListener(null)
                            }
                            if (red.d == -1) {

                            }
                            else if (red.d == 0) {
                                red_d.setOnClickListener(null)
                            }
                            else if (!red.inside_d) {
                                tiles[red.d - 1].setOnClickListener(null)
                            }
                            else if (red.inside_d) {
                                r_win[red.d - 1].setOnClickListener(null)
                            }
                            red.c = 1
                            if (num_on_dice == 5) {
                                red.temp = 1
                            }
                        }
                    }
                    red_d.setOnClickListener {
                        if (red.d == 0) {
                            red_d.setImageResource(R.drawable.back_no_bounds)
                            pos_1.setImageResource(R.drawable.red_icon)
                            if (red.a == -1) {

                            }
                            else if (red.a == 0) {
                                red_a.setOnClickListener(null)
                            }
                            else if (!red.inside_a) {
                                tiles[red.a - 1].setOnClickListener(null)
                            }
                            else if (red.inside_a) {
                                r_win[red.a - 1].setOnClickListener(null)
                            }
                            if (red.b == -1) {

                            }
                            else if (red.b == 0) {
                                red_b.setOnClickListener(null)
                            }
                            else if (!red.inside_b) {
                                tiles[red.b - 1].setOnClickListener(null)
                            }
                            else if (red.inside_b) {
                                r_win[red.b - 1].setOnClickListener(null)
                            }
                            if (red.c == -1) {

                            }
                            else if (red.c == 0) {
                                red_c.setOnClickListener(null)
                            }
                            else if (!red.inside_c) {
                                tiles[red.c - 1].setOnClickListener(null)
                            }
                            else if (red.inside_c) {
                                r_win[red.c - 1].setOnClickListener(null)
                            }
                            if (red.d == -1) {

                            }
                            else if (red.d == 0) {
                                red_d.setOnClickListener(null)
                            }
                            else if (!red.inside_d) {
                                tiles[red.d - 1].setOnClickListener(null)
                            }
                            else if (red.inside_d) {
                                r_win[red.d - 1].setOnClickListener(null)
                            }
                            red.d = 1
                            if (num_on_dice == 5) {
                                red.temp = 1
                            }
                        }
                    }
                }
                green.temp = 1
            }
        }

        dice_green.setOnClickListener {
            if (chance == 1 && green.temp == 1) {
                green.temp = 0
                dice_green.startAnimation(anim)
                num_on_dice = Random.nextInt(6)
                dice_green.setBackgroundResource(dice_image[num_on_dice])
                mp.start()
                if (green.a == 0 && green.b == 0 && green.c == 0 && green.d == 0 && num_on_dice != 5) {
                    player_chance.setTextColor(Color.RED)
                    player_chance.setText("Red's Turn")
                }
                    move(green, num_on_dice)

                if ((num_on_dice == 5 && green.a == 0) || (num_on_dice == 5 && green.b == 0) || (num_on_dice == 5 && green.c == 0) || (num_on_dice == 5 && green.d == 0)) {
                    green_a.setOnClickListener {
                        if (green.a == 0) {
                            green_a.setImageResource(R.drawable.back_no_bounds)
                            pos_27.setImageResource(R.drawable.green_icon)
                            if (green.a == -1) {

                            }
                            else if (green.a == 0) {
                                green_a.setOnClickListener(null)
                            }
                            else if (!green.inside_a) {
                                tiles[green.a - 1].setOnClickListener(null)
                            }
                            else if (green.inside_a) {
                                g_win[green.a - 1].setOnClickListener(null)
                            }
                            if (green.b == -1) {

                            }
                            else if (green.b == 0) {
                                green_b.setOnClickListener(null)
                            }
                            else if (!green.inside_b) {
                                tiles[green.b - 1].setOnClickListener(null)
                            }
                            else if (green.inside_b) {
                                g_win[green.b - 1].setOnClickListener(null)
                            }
                            if (green.c == -1) {

                            }
                            else if (green.c == 0) {
                                green_c.setOnClickListener(null)
                            }
                            else if (!green.inside_c) {
                                tiles[green.c - 1].setOnClickListener(null)
                            }
                            else if (green.inside_c) {
                                g_win[green.c - 1].setOnClickListener(null)
                            }
                            if (green.d == -1) {

                            }
                            else if (green.d == 0) {
                                green_d.setOnClickListener(null)
                            }
                            else if (!green.inside_d) {
                                tiles[green.d - 1].setOnClickListener(null)
                            }
                            else if (green.inside_d) {
                                g_win[green.d - 1].setOnClickListener(null)
                            }
                            green.a = 27
                            if (num_on_dice == 5) {
                                green.temp = 1
                            }
                        }
                    }

                    green_b.setOnClickListener {
                        if (green.b == 0) {
                            green_b.setImageResource(R.drawable.back_no_bounds)
                            pos_27.setImageResource(R.drawable.green_icon)
                            if (green.a == -1) {

                            }
                            else if (green.a == 0) {
                                green_a.setOnClickListener(null)
                            }
                            else if (!green.inside_a) {
                                tiles[green.a - 1].setOnClickListener(null)
                            }
                            else if (green.inside_a) {
                                g_win[green.a - 1].setOnClickListener(null)
                            }
                            if (green.b == -1) {

                            }
                            else if (green.b == 0) {
                                green_b.setOnClickListener(null)
                            }
                            else if (!green.inside_b) {
                                tiles[green.b - 1].setOnClickListener(null)
                            }
                            else if (green.inside_b) {
                                g_win[green.b - 1].setOnClickListener(null)
                            }
                            if (green.c == -1) {

                            }
                            else if (green.c == 0) {
                                green_c.setOnClickListener(null)
                            }
                            else if (!green.inside_c) {
                                tiles[green.c - 1].setOnClickListener(null)
                            }
                            else if (green.inside_c) {
                                g_win[green.c - 1].setOnClickListener(null)
                            }
                            if (green.d == -1) {

                            }
                            else if (green.d == 0) {
                                green_d.setOnClickListener(null)
                            }
                            else if (!green.inside_d) {
                                tiles[green.d - 1].setOnClickListener(null)
                            }
                            else if (green.inside_d) {
                                g_win[green.d - 1].setOnClickListener(null)
                            }
                            green.b = 27
                            if (num_on_dice == 5) {
                                green.temp = 1
                            }
                        }
                    }

                    green_c.setOnClickListener {
                        if (green.c == 0) {
                            green_c.setImageResource(R.drawable.back_no_bounds)
                            pos_27.setImageResource(R.drawable.green_icon)
                            if (green.a == -1) {

                            }
                            else if (green.a == 0) {
                                green_a.setOnClickListener(null)
                            }
                            else if (!green.inside_a) {
                                tiles[green.a - 1].setOnClickListener(null)
                            }
                            else if (green.inside_a) {
                                g_win[green.a - 1].setOnClickListener(null)
                            }
                            if (green.b == -1) {

                            }
                            else if (green.b == 0) {
                                green_b.setOnClickListener(null)
                            }
                            else if (!green.inside_b) {
                                tiles[green.b - 1].setOnClickListener(null)
                            }
                            else if (green.inside_b) {
                                g_win[green.b - 1].setOnClickListener(null)
                            }
                            if (green.c == -1) {

                            }
                            else if (green.c == 0) {
                                green_c.setOnClickListener(null)
                            }
                            else if (!green.inside_c) {
                                tiles[green.c - 1].setOnClickListener(null)
                            }
                            else if (green.inside_c) {
                                g_win[green.c - 1].setOnClickListener(null)
                            }
                            if (green.d == -1) {

                            }
                            else if (green.d == 0) {
                                green_d.setOnClickListener(null)
                            }
                            else if (!green.inside_d) {
                                tiles[green.d - 1].setOnClickListener(null)
                            }
                            else if (green.inside_d) {
                                g_win[green.d - 1].setOnClickListener(null)
                            }
                            green.c = 27
                            if (num_on_dice == 5) {
                                green.temp = 1
                            }
                        }
                    }

                    green_d.setOnClickListener {
                        if (green.d == 0) {
                            green_d.setImageResource(R.drawable.back_no_bounds)
                            pos_27.setImageResource(R.drawable.green_icon)
                            if (green.a == -1) {

                            }
                            else if (green.a == 0) {
                                green_a.setOnClickListener(null)
                            }
                            else if (!green.inside_a) {
                                tiles[green.a - 1].setOnClickListener(null)
                            }
                            else if (green.inside_a) {
                                g_win[green.a - 1].setOnClickListener(null)
                            }
                            if (green.b == -1) {

                            }
                            else if (green.b == 0) {
                                green_b.setOnClickListener(null)
                            }
                            else if (!green.inside_b) {
                                tiles[green.b - 1].setOnClickListener(null)
                            }
                            else if (green.inside_b) {
                                g_win[green.b - 1].setOnClickListener(null)
                            }
                            if (green.c == -1) {

                            }
                            else if (green.c == 0) {
                                green_c.setOnClickListener(null)
                            }
                            else if (!green.inside_c) {
                                tiles[green.c - 1].setOnClickListener(null)
                            }
                            else if (green.inside_c) {
                                g_win[green.c - 1].setOnClickListener(null)
                            }
                            if (green.d == -1) {

                            }
                            else if (green.d == 0) {
                                green_d.setOnClickListener(null)
                            }
                            else if (!green.inside_d) {
                                tiles[green.d - 1].setOnClickListener(null)
                            }
                            else if (green.inside_d) {
                                g_win[green.d - 1].setOnClickListener(null)
                            }
                            green.d = 27
                            if (num_on_dice == 5) {
                                green.temp = 1
                            }
                        }
                    }
                }
                red.temp = 1
            }
        }
    }
    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to Exit?")
            .setCancelable(false)
            .setPositiveButton("Yes", DialogInterface.OnClickListener() { dialogInterface, i ->
                finish()
                audio.pause()
                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            })
            .setNegativeButton("No", DialogInterface.OnClickListener() { dialogInterface, i ->
                dialogInterface.cancel()
            })
        var alertdialog : AlertDialog = builder.create()
        alertdialog.show()


        //super.onBackPressed()
    }
}
