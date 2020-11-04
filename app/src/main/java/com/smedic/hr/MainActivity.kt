package com.smedic.hr

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * @author Stevan Medic
 *
 * Created on Nov 2020
 */

class MainActivity : AppCompatActivity() {

    val TAG = "SMEDIC"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

//        var movies = getMovies()
        //Log.d(TAG, "--->: ${movies.size}");
    }

    companion object {
        init {
            System.loadLibrary("native-lib");
        }
    }
}