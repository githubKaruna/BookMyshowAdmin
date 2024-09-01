package com.neatroots.bookymyshowadmin.domain.repo

import android.graphics.Bitmap
import android.net.Uri
import com.neatroots.bookymyshowadmin.common.ResultState
import com.neatroots.bookymyshowadmin.model.BookingModel
import com.neatroots.bookymyshowadmin.model.CategoryModel
import com.neatroots.bookymyshowadmin.model.LoginModel
import com.neatroots.bookymyshowadmin.model.MovieModel
import com.neatroots.bookymyshowadmin.model.NotificationModel
import com.neatroots.bookymyshowadmin.model.SliderModel
import kotlinx.coroutines.flow.Flow

interface BookMyShowAdminRepo {

    suspend fun addCategory(categoryModel: CategoryModel): Flow<ResultState<String>>
    suspend fun uploadCategoryImage(imageUri: Uri?,data: ByteArray?): Flow<ResultState<String>>
    suspend fun getAllCategory(): Flow<ResultState<List<CategoryModel>>>
    suspend fun getAllMovies(): Flow<ResultState<List<MovieModel>>>
    suspend fun addMovie(movieModel: MovieModel): Flow<ResultState<String>>
    suspend fun uploadMovieCover(imageUri: Uri?,data: ByteArray?): Flow<ResultState<String>>
    suspend fun uploadMovieImages(imageUri: Uri?,data: ByteArray?): Flow<ResultState<String>>
    suspend fun getAllBooking(): Flow<ResultState<List<BookingModel>>>
    suspend fun uploadImage(imageUri: Uri?,data: ByteArray?,type:String): Flow<ResultState<String>>
    suspend fun createSlider(note: String, sliderImgUrl: String): Flow<ResultState<String>>
    suspend fun getAllSliders(): Flow<ResultState<List<SliderModel>>>
    suspend fun getAllNotificationList(): Flow<ResultState<List<NotificationModel>>>

    suspend fun addNotification(notificationModel: NotificationModel): Flow<ResultState<String>>
    suspend fun checkLoginRegister(): Flow<ResultState<LoginModel>>
    suspend fun adminRegistration(loginModel: LoginModel): Flow<ResultState<String>>
}