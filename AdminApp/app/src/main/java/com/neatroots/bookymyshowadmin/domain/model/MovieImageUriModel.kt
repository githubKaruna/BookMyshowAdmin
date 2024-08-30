package com.neatroots.bookymyshowadmin.model

import android.net.Uri

data class MovieImageUriModel(
    val imageId: String = "",
    val imageUri: Uri? = null,
    val imageByteArray: ByteArray?=null


) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MovieImageUriModel

        if (imageId != other.imageId) return false
        if (imageUri != other.imageUri) return false
        if (imageByteArray != null) {
            if (other.imageByteArray == null) return false
            if (!imageByteArray.contentEquals(other.imageByteArray)) return false
        } else if (other.imageByteArray != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = imageId.hashCode()
        result = 31 * result + (imageUri?.hashCode() ?: 0)
        result = 31 * result + (imageByteArray?.contentHashCode() ?: 0)
        return result
    }
}