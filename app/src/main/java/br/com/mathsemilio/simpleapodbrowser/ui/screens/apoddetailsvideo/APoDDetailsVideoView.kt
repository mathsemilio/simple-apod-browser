package br.com.mathsemilio.simpleapodbrowser.ui.screens.apoddetailsvideo

import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.core.content.res.ResourcesCompat
import br.com.mathsemilio.simpleapodbrowser.R
import br.com.mathsemilio.simpleapodbrowser.common.formatAPoDDate
import br.com.mathsemilio.simpleapodbrowser.domain.model.APoD
import br.com.mathsemilio.simpleapodbrowser.ui.common.view.BaseView

class APoDDetailsVideoView(layoutInflater: LayoutInflater, container: ViewGroup?) :
    BaseView(),
    APoDDetailsVideoContract.View {

    private lateinit var videoViewApodDetailVideo: VideoView
    private lateinit var imageViewApodDetailVideoPlayIcon: ImageView
    private lateinit var textViewApodDetailWithVideoTitle: TextView
    private lateinit var textViewApodDetailWithVideoDate: TextView
    private lateinit var textViewApodDetailWithVideoExplanation: TextView
    private lateinit var textViewApodDetailWithVideoCredit: TextView

    private lateinit var mediaController: MediaController

    init {
        rootView = layoutInflater.inflate(R.layout.apod_detail_with_video, container, false)
        initializeViews()
        setupMediaController()
        attachVideoViewOnCompleteListener()
        attachPlayIconOnClickListener()
    }

    private fun initializeViews() {
        videoViewApodDetailVideo =
            findViewById(R.id.video_view_apod_detail_video)
        imageViewApodDetailVideoPlayIcon =
            findViewById(R.id.image_view_apod_detail_video_play_icon)
        textViewApodDetailWithVideoTitle =
            findViewById(R.id.text_view_apod_detail_with_video_title)
        textViewApodDetailWithVideoDate =
            findViewById(R.id.text_view_apod_detail_with_video_date)
        textViewApodDetailWithVideoExplanation =
            findViewById(R.id.text_view_apod_detail_with_video_explanation)
        textViewApodDetailWithVideoCredit =
            findViewById(R.id.text_view_apod_detail_with_video_credit)
    }

    private fun setupMediaController() {
        mediaController = MediaController(context, true)
        mediaController.setAnchorView(videoViewApodDetailVideo)
    }

    private fun attachVideoViewOnCompleteListener() {
        videoViewApodDetailVideo.setOnCompletionListener {
            imageViewApodDetailVideoPlayIcon.apply {
                visibility = View.VISIBLE
                setImageDrawable(
                    ResourcesCompat.getDrawable(
                        context.resources,
                        R.drawable.ic_baseline_replay_24,
                        null
                    )
                )
            }
        }
    }

    private fun attachPlayIconOnClickListener() {
        imageViewApodDetailVideoPlayIcon.setOnClickListener { playIcon ->
            videoViewApodDetailVideo.start()
            playIcon.visibility = View.GONE
        }
    }

    private fun getAudioAttributes(): AudioAttributes {
        return AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_MOVIE)
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .build()
    }

    override fun bindAPoDDetails(aPoD: APoD) {
        videoViewApodDetailVideo.apply {
            setVideoURI(Uri.parse(aPoD.url))
            setMediaController(mediaController)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                setAudioAttributes(getAudioAttributes())
        }
        textViewApodDetailWithVideoTitle.text = aPoD.title
        textViewApodDetailWithVideoDate.text = aPoD.date.formatAPoDDate(context)
        textViewApodDetailWithVideoExplanation.text = aPoD.explanation
        setImageCreditTextViewText(aPoD.copyright)
    }

    private fun setImageCreditTextViewText(imageCopyright: String?) {
        if (imageCopyright != null)
            textViewApodDetailWithVideoCredit.visibility = View.INVISIBLE
        else
            textViewApodDetailWithVideoCredit.text = context.getString(
                R.string.image_credit, imageCopyright
            )
    }
}