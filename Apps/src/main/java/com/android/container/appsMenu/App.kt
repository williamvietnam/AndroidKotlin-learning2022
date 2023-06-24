package com.android.container.appsMenu

/**
 * Author: William Vietnam |
 * 28/03/2023
 * */
data class App(
    val id: String,
    val logo: Int,
    val name: String,
) {
    companion object {
        // Apps ID
        const val APP_CAMERA_ID = "app.camera.id"
        const val APP_STEP_COUNTER_ID = "app.step.counter.id"
        const val APP_PRANK_SOUND_ID = "app.prank.sound.id"
        const val APP_FAKE_CALL_ID = "app.fake.call.id"
        const val APP_LIE_DETECTOR_ID = "app.lie.detector.id"
        const val APP_AI_CHAT_ID = "app.ai.chat.id"
        const val APP_WHATSAPP_STICKERS_ID = "app.whatsapp.stickers.id"
        const val APP_PAINT_ID = "app.paint.id"
        const val APP_IMAGE_FILTERS_ID = "app.image.filters.id"
        const val APP_WALLPAPER_ID = "app.wallpaper.id"
        const val APP_CHARTS_ID = "app.charts.id"
        const val APP_SHORTS_ID = "app.shorts.id"

        // Apps Name
        const val APP_CAMERA_NAME = "My Camera"
        const val APP_STEP_COUNTER_NAME = "Step Counter"
        const val APP_PRANK_SOUND_NAME = "Prank Sounds"
        const val APP_FAKE_CALL_NAME = "Fake Call"
        const val APP_LIE_DETECTOR_NAME = "Lie Detector"
        const val APP_AI_CHAT_NAME = "AI Chat"
        const val APP_WHATSAPP_STICKERS_NAME = "WA Stickers"
        const val APP_PAINT_NAME = "Paint"
        const val APP_IMAGE_FILTERS_NAME = "Image Filters"
        const val APP_WALLPAPER_NAME = "Wallpaper"
        const val APP_CHARTS_NAME = "Charts"
        const val APP_SHORTS_NAME = "Video Shorts"
    }
}