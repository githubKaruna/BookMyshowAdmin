package com.neatroots.bookymyshowadmin.domain.repo

import android.graphics.Bitmap
import android.net.Uri
import com.neatroots.bookymyshowadmin.common.ResultState
import com.neatroots.bookymyshowadmin.model.CategoryModel
import com.neatroots.bookymyshowadmin.model.MovieModel
import kotlinx.coroutines.flow.Flow

interface BookMyShowAdminRepo {

    suspend fun addCategory(categoryModel: CategoryModel): Flow<ResultState<String>>
    suspend fun uploadCategoryImage(imageUri: Uri?,bitmap: Bitmap?): Flow<ResultState<String>>
    suspend fun getAllCategory(): Flow<ResultState<List<CategoryModel>>>
    suspend fun getAllMovies(): Flow<ResultState<List<MovieModel>>>
    suspend fun addMovie(movieModel: MovieModel): Flow<ResultState<String>>
}