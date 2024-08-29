package com.neatroots.bookymyshowadmin.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


@Parcelize
data class MovieModel (
    var movieId: String = "",
    var coverImg: String = "",
    var title: String = "",
    var rating: String = "",
    var description: String = "",
    var price: String = "",
    var fullPrice: String = "",
    var selectCategory: String = "",
    var language: String = "",
    var theater: String = "",
    @field:JvmField
    var images: @RawValue ArrayList<MovieImageUrlModel> = ArrayList()
): Parcelable