package com.noob.ludo

import android.content.DialogInterface
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.media.AsyncPlayer
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.sound
import kotlinx.android.synthetic.main.two_activity.*
import java.util.Collections.rotate
import kotlin.random.Random




class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var settings : MediaPlayer = MediaPlayer.create(this, R.raw.setting)
        var sound_is_playing : Boolean = true
        twoplayer.setOnClickListener {
            if (sound_is_playing) {
                settings.start()
            }
        }
        fourplayer.setOnClickListener {
            if (sound_is_playing) {
                settings.start()
            }
        }
        start_button.setOnClickListener {
            if (twoplayer.isChecked()) {
                var intent = Intent(this, TwoActivity::class.java)
                intent.putExtra("my_boolean_key", sound_is_playing)
                startActivity(intent)
            }
            if (fourplayer.isChecked()) {
                var intent = Intent(this, FourActivity::class.java)
                intent.putExtra("my_boolean_key", sound_is_playing)
                startActivity(intent)
            }
        }
        sound.setOnClickListener {
            settings.start()
            if (sound_is_playing) {
                sound_is_playing = false
                sound.setBackgroundResource(R.drawable.sound_is_off)
            }
            else if (!sound_is_playing) {
                sound_is_playing = true
                sound.setBackgroundResource(R.drawable.sound_is_on)

            }
        }
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to Exit?")
            .setCancelable(false)
            .setPositiveButton("Yes", DialogInterface.OnClickListener() { dialogInterface, i ->
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            })
            .setNegativeButton("No", DialogInterface.OnClickListener() { dialogInterface, i ->
                dialogInterface.cancel()
            })
        var alertdialog : AlertDialog = builder.create()
        alertdialog.show()
        // \super.onBackPressed()
    }

}