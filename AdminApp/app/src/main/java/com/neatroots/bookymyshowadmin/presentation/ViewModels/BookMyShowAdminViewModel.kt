package com.neatroots.bookymyshowadmin.presentation.ViewModels

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neatroots.bookymyshowadmin.common.ResultState
import com.neatroots.bookymyshowadmin.domain.repo.BookMyShowAdminRepo
import com.neatroots.bookymyshowadmin.model.CategoryModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.lang.Error
import javax.inject.Inject

@HiltViewModel
class BookMyShowAdminViewModel @Inject constructor(var bookMyShowAdminRepo: BookMyShowAdminRepo) : ViewModel() {
 private val categoryState: MutableState<CategoryState> = mutableStateOf(CategoryState())
    val _categoryState = categoryState
    val categoryModel :MutableState<CategoryModel> = mutableStateOf(CategoryModel())

    private val _uploadCategoryImageState  = mutableStateOf(UploadCategoryImageState())
    val uploadCategoryImageState : MutableState<UploadCategoryImageState> = _uploadCategoryImageState



    fun addCategory()
    {
        viewModelScope.launch {
            bookMyShowAdminRepo.addCategory(categoryModel.value).collectLatest {
                when (it) {
                    is ResultState.Success -> {
                        categoryState.value = CategoryState(data = it.data, isLoading = false)
                    }

                    is ResultState.Error -> {
                        categoryState.value = CategoryState(error = it.message, isLoading = false)
                    }

                    ResultState.Loading -> categoryState.value =  CategoryState(isLoading = true)
                }
            }

        }

    }
    fun uploadCategoryImage(imageUri : Uri){
        viewModelScope.launch {
            bookMyShowAdminRepo.uploadCategoryImage(imageUri).collectLatest {
                when(it){
                    is ResultState.Error -> {
                        _uploadCategoryImageState.value = _uploadCategoryImageState.value.copy(
                            loading = false,
                            error = it.message
                        )
                    }
                    is ResultState.Loading -> {
                        _uploadCategoryImageState.value = _uploadCategoryImageState.value.copy(
                            loading = true
                        )

                    }
                    is ResultState.Success -> {
                        _uploadCategoryImageState.value = _uploadCategoryImageState.value.copy(
                            loading = false,
                            success = it.data
                        )
                        categoryModel.value.imgUrl=it.data
                    }
                }
            }
        }
    }

}

data class CategoryState(
    val data:String="",
    val isLoading: Boolean = false,
    val error: String=""
)

data class UploadCategoryImageState(
    var loading: Boolean = false,
    var success: String = "",
    var error: String = ""
)