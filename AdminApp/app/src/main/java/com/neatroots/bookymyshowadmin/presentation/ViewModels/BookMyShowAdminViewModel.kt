package com.neatroots.bookymyshowadmin.presentation.ViewModels

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neatroots.bookymyshowadmin.common.ResultState
import com.neatroots.bookymyshowadmin.domain.repo.BookMyShowAdminRepo
import com.neatroots.bookymyshowadmin.model.BookingModel
import com.neatroots.bookymyshowadmin.model.CategoryModel
import com.neatroots.bookymyshowadmin.model.MovieModel
import com.neatroots.bookymyshowadmin.model.SliderModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookMyShowAdminViewModel @Inject constructor(var bookMyShowAdminRepo: BookMyShowAdminRepo) : ViewModel() {
    private val categoryState: MutableState<CategoryState> = mutableStateOf(CategoryState())
    val _categoryState = categoryState
    val categoryModel :MutableState<CategoryModel> = mutableStateOf(CategoryModel())
    val movieModel :MutableState<MovieModel> = mutableStateOf(MovieModel())
    val sliderModel :MutableState<SliderModel> = mutableStateOf(SliderModel())


    private val _uploadCategoryImageState  = mutableStateOf(UploadCategoryImageState())
    val uploadCategoryImageState : MutableState<UploadCategoryImageState> = _uploadCategoryImageState

    private val _getAllCategoryState = MutableStateFlow(GetAllCategoryState())
    val getAllCategoryState = _getAllCategoryState.asStateFlow()

    private val _getAllMoviesState = MutableStateFlow(GetAllMoviesState())
    val getAllMoviesState = _getAllMoviesState.asStateFlow()

    private val _movieState = mutableStateOf(MovieState())
    val moviesState = _movieState

    private val _uploadMovieCover  = mutableStateOf(MovieCoverState())
    val uploadMovieCover : MutableState<MovieCoverState> = _uploadMovieCover

    private val _uploadMovieImages  = mutableStateOf(MovieImagesState())
    val uploadMovieImages : MutableState<MovieImagesState> = _uploadMovieImages

    private val _getAllBookingsState = MutableStateFlow(GetAllBookingState())
    val getAllBookingsState = _getAllBookingsState.asStateFlow()

    private val _uploadImageState  = mutableStateOf(UploadImageState())
    val uploadImageState : MutableState<UploadImageState> = _uploadImageState

    private val _addSliderState  = mutableStateOf(AddSliderState())
    val addSliderState = _addSliderState

    private val _getSliderState  = MutableStateFlow(GetSliderState())
    val getSliderState = _getSliderState.asStateFlow()


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
    fun uploadCategoryImage(imageUri : Uri?=null,data: ByteArray?=null){
        viewModelScope.launch {
            bookMyShowAdminRepo.uploadCategoryImage(imageUri,data).collectLatest {
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

    fun getAllCategories(){
        viewModelScope.launch {
            bookMyShowAdminRepo.getAllCategory().collect{
                when(it){
                    is ResultState.Loading -> {
                        _getAllCategoryState.value = _getAllCategoryState.value.copy(
                            isLoading = true
                        )
                    }
                    is ResultState.Error -> {
                        _getAllCategoryState.value = _getAllCategoryState.value.copy(
                            isLoading = false,
                            errorMessage = it.message
                        )
                    }
                    is ResultState.Success ->{
                        _getAllCategoryState.value = _getAllCategoryState.value.copy(
                            isLoading = false,
                            categories = it.data
                        )
                    }
                }
            }

        }
    }


    fun getAllMovies(){
        viewModelScope.launch {
            bookMyShowAdminRepo.getAllMovies().collect{
                when(it){
                    is ResultState.Loading -> {
                        _getAllMoviesState.value = _getAllMoviesState.value.copy(
                            isLoading = true
                        )
                    }
                    is ResultState.Error -> {
                        _getAllMoviesState.value = _getAllMoviesState.value.copy(
                            isLoading = false,
                            errorMessage = it.message
                        )
                    }
                    is ResultState.Success ->{
                        _getAllMoviesState.value = _getAllMoviesState.value.copy(
                            isLoading = false,
                            movies = it.data
                        )
                    }
                }
            }

        }
    }


    fun uploadMovieCover(
        imageUri: Uri? = null,
        data: ByteArray? = null,
        onCompleted: () -> Unit
    ){
        viewModelScope.launch {
            bookMyShowAdminRepo.uploadMovieCover(imageUri,data).collectLatest {
                when(it){
                    is ResultState.Error -> {
                        _uploadMovieCover.value = _uploadMovieCover.value.copy(
                            isLoading = false,
                            errorMessage = it.message

                        )
                        onCompleted()

                    }
                    is ResultState.Loading -> {
                        _uploadMovieCover.value = _uploadMovieCover.value.copy(
                            isLoading = true
                        )

                    }
                    is ResultState.Success -> {
                        _uploadMovieCover.value = _uploadMovieCover.value.copy(
                            isLoading = false,
                            success = it.data
                        )
                        movieModel.value.coverImg = it.data
                        onCompleted()
                    }
                }
            }
        }
    }


    fun uploadMovieImages(imageUri:Uri?=null, data: ByteArray?=null, onCompleted: (url:String?, isError:Boolean?) -> Unit){

        viewModelScope.launch {
            bookMyShowAdminRepo.uploadMovieImages(imageUri,data).collectLatest {
                when(it){
                    is ResultState.Error -> {
                        _uploadMovieImages.value = _uploadMovieImages.value.copy(
                            isLoading = false,
                            success = it.message
                        )
                        onCompleted(null,true)

                    }
                    is ResultState.Loading -> {
                        _uploadMovieImages.value = _uploadMovieImages.value.copy(
                            isLoading = true
                        )

                    }
                    is ResultState.Success -> {
                        _uploadMovieImages.value = _uploadMovieImages.value.copy(
                            isLoading = false,
                            success = it.data

                        )
                        onCompleted(it.data,false)
                    }
                }
            }
        }
    }

    fun addMovie()
    {
        viewModelScope.launch {
            bookMyShowAdminRepo.addMovie(movieModel.value).collectLatest {
                when (it) {
                    is ResultState.Success -> {
                        moviesState.value= MovieState(success = it.data, isLoading = false)
                    }

                    is ResultState.Error -> {
                        moviesState.value = MovieState(success = it.message, isLoading = false)
                    }

                    ResultState.Loading -> moviesState.value =  MovieState(isLoading = true)
                }
            }

        }

    }

    fun getAllBookings(){
        viewModelScope.launch {
            bookMyShowAdminRepo.getAllBooking().collect{
                when(it){
                    is ResultState.Loading -> {
                        _getAllBookingsState.value = _getAllBookingsState.value.copy(
                            isLoading = true
                        )
                    }
                    is ResultState.Error -> {
                        _getAllBookingsState.value = _getAllBookingsState.value.copy(
                            isLoading = false,
                            errorMessage = it.message
                        )
                    }
                    is ResultState.Success ->{
                        _getAllBookingsState.value = _getAllBookingsState.value.copy(
                            isLoading = false,
                            bookings = it.data
                        )
                    }
                }
            }

        }
    }
    fun uploadImage(
        imageUri: Uri? = null,
        data: ByteArray? = null,
        type: String,
        onCompleted: () -> Unit
    ){
        viewModelScope.launch {
            bookMyShowAdminRepo.uploadImage(imageUri,data,type).collectLatest {
                when(it){
                    is ResultState.Error -> {
                        _uploadImageState.value = _uploadImageState.value.copy(
                            loading = false,
                            error = it.message
                        )
                        onCompleted()
                    }
                    is ResultState.Loading -> {
                        _uploadImageState.value = _uploadImageState.value.copy(
                            loading = true
                        )

                    }
                    is ResultState.Success -> {
                        _uploadImageState.value = _uploadImageState.value.copy(
                            loading = false,
                            success = it.data
                        )
                        onCompleted()

                    }
                }
            }
        }
    }
    fun createslider(note: String, sliderImgUrl: String)
    {
        viewModelScope.launch {
            bookMyShowAdminRepo.createSlider(note,sliderImgUrl).collectLatest {
                when (it) {
                    is ResultState.Success -> {
                        addSliderState.value = AddSliderState(success = it.data, isLoading = false)
                    }

                    is ResultState.Error -> {
                        addSliderState.value = AddSliderState(errorMessage = it.message, isLoading = false)
                    }

                    ResultState.Loading -> addSliderState.value =  AddSliderState(isLoading = true)
                }
            }

        }

    }
    fun getAllSliderList(){
        viewModelScope.launch {
            bookMyShowAdminRepo.getAllSliders().collect{
                when(it){
                    is ResultState.Loading -> {
                        _getSliderState.value = _getSliderState.value.copy(
                            isLoading = true
                        )
                    }
                    is ResultState.Error -> {
                        _getSliderState.value = _getSliderState.value.copy(
                            isLoading = false,
                            errorMessage = it.message
                        )
                    }
                    is ResultState.Success ->{
                        _getSliderState.value = _getSliderState.value.copy(
                            isLoading = false,
                            sliders = it.data
                        )
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

data class GetAllCategoryState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val categories: List<CategoryModel?> = emptyList()

)

data class GetAllMoviesState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val movies: List<MovieModel?> = emptyList()

)
data class MovieState(
    val isLoading: Boolean=false,
    val errorMessage: String?=null,
    val success: String?=null
)

data class MovieCoverState(
    val isLoading: Boolean=false,
    val errorMessage: String?=null,
    val success: String?=null
)
data class MovieImagesState(
    val isLoading: Boolean=false,
    val errorMessage: String?=null,
    val success: String?=null
)
data class GetAllBookingState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val bookings: List<BookingModel?> = emptyList()
)
data class UploadImageState(
    var loading: Boolean = false,
    var success: String = "",
    var error: String = ""

)
data class AddSliderState(
    val isLoading: Boolean=false,
    val errorMessage: String?=null,
    val success: String?=null
)

data class GetSliderState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val sliders: List<SliderModel?> = emptyList()
)