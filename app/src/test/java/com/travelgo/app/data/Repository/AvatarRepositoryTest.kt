package com.travelgo.app.data.repository

class AvatarRepository {

    private var avatarUri: String? = null

    fun saveAvatarUri(uri: String?) {
        avatarUri = uri
    }

    fun getAvatarUri(): String? = avatarUri

    fun clearAvatar() {
        avatarUri = null
    }
}
