package com.smedic.hr

import android.os.Bundle
import android.widget.Toast
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.smedic.hr.tools.Config
import kotlinx.android.synthetic.main.yt_player_activity.*

/**
 * @author Stevan Medic
 *
 * Created on Nov 2020
 */

class YTPlayerActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.yt_player_activity)

        youtube_view.initialize(Config.YOUTUBE_API_KEY, this);
    }

    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?,
        player: YouTubePlayer?,
        wasRestored: Boolean
    ) {
        if (!wasRestored) {
            val bundle = intent.extras
            bundle?.let {
                player?.cueVideo(it.getString(TRAILER_YT_ID))
            }
        }
    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider?,
        errorReason: YouTubeInitializationResult?
    ) {
        val error = String.format(getString(R.string.player_error), errorReason.toString())
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    companion object {
        const val TRAILER_YT_ID = "trailer_yt_id"
    }
}