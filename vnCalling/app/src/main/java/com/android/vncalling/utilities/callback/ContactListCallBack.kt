package com.android.vncalling.utilities.callback

import com.android.vncalling.data.remote.models.UserInformation

interface ContactListCallBack {
    fun onMessageClicked(item: UserInformation?)

    fun onAudioCallClicked(item: UserInformation?)

    fun onVideoCallClicked(item: UserInformation?)
}