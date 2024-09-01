package com.neatroots.bookymyshowadmin.data.repo

import android.net.Uri
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.neatroots.bookymyshowadmin.utils.Constants
import com.neatroots.bookymyshowadmin.common.ResultState
import com.neatroots.bookymyshowadmin.domain.repo.BookMyShowAdminRepo
import com.neatroots.bookymyshowadmin.model.BookingModel
import com.neatroots.bookymyshowadmin.model.CategoryModel
import com.neatroots.bookymyshowadmin.model.LoginModel
import com.neatroots.bookymyshowadmin.model.MovieModel
import com.neatroots.bookymyshowadmin.model.NotificationModel
import com.neatroots.bookymyshowadmin.model.SliderModel
import com.neatroots.bookymyshowadmin.utils.SharedPref
import com.neatroots.bookymyshowadmin.utils.Utils
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class BookMyShowAdminRepoImpl @Inject constructor(private val database: FirebaseDatabase,private val firebaseStorage: FirebaseStorage) : BookMyShowAdminRepo {
    override suspend fun addCategory(categoryModel: CategoryModel): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)
            val categoryId = database.reference.child(Constants.CATEGORY_REF).push().key
            categoryModel.categoryId = categoryId.toString()
            categoryId?.let { id ->
                categoryModel.categoryId = id
                database.reference.child(Constants.CATEGORY_REF).child(id).setValue(categoryModel)
                    .addOnSuccessListener {
                        Log.d("TAG", "addCategory: " + categoryId)
                        trySend(ResultState.Success(" Added"))
                    }.addOnFailureListener {
                        trySend(ResultState.Error(it.localizedMessage ?: "Something went wrong"))
                    }
                awaitClose() {
                    close()

                }
            }

        }

    override suspend fun checkLoginRegister(): Flow<ResultState<LoginModel>> =
        callbackFlow {
           database.reference.child(Constants.LOGIN_REF).addValueEventListener(object : ValueEventListener {
               override fun onDataChange(snapshot: DataSnapshot) {
                   if(snapshot.exists()) {
                       val loginModel : LoginModel = (snapshot.getValue(LoginModel::class.java) as LoginModel?)!!
                       trySend(ResultState.Success(loginModel))
                   }
                   else
                       trySend(ResultState.Error(Constants.NO_DATA_FOUND))

           }

               override fun onCancelled(error: DatabaseError) {
                   trySend(ResultState.Error(Constants.NO_DATA_FOUND))
               }
           })
        }


    override suspend fun adminRegistration(loginModel: LoginModel): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)
           // val loginId = database.reference.child(Constants.CATEGORY_REF).push().key
            loginModel.id = Constants.LOGIN_REF
            database.reference.child(Constants.LOGIN_REF).setValue(loginModel).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    trySend(ResultState.Success(" Added"))
                    Log.d("TAG", "adminRegistration: "+task.result.toString())
                } else {
                    trySend(ResultState.Error(task.exception?.localizedMessage ?: "Something went wrong"))
                }
            }



        }

    override suspend fun uploadCategoryImage(
        imageUri: Uri?,
        data: ByteArray?
    ): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        if (imageUri != null) {
            firebaseStorage.reference.child(Constants.CATEGORY_IMAGE_REF + "/${Constants.CATEGORY_IMAGE_REF+System.currentTimeMillis()}")
                .putFile(imageUri ?: Uri.EMPTY).addOnCompleteListener {
                    it.result.storage.downloadUrl.addOnSuccessListener { imageUrl ->
                        trySend(ResultState.Success(imageUrl.toString()))
                    }
                    if (it.exception != null) {
                        trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                    }

                }
            awaitClose {
                close()
            }
        }

             if(data!=null) {
                 // Upload the ByteArray to Firebase Storage
                 val uploadTask =
                     firebaseStorage.reference.child(Constants.CATEGORY_IMAGE_REF + "/${Constants.CATEGORY_IMAGE_REF+System.currentTimeMillis()}")
                         .putBytes(data)
                 uploadTask.addOnSuccessListener { imageUrl ->
                     trySend(ResultState.Success(imageUrl.toString()))
                 }.addOnFailureListener { exception ->
                     // Handle unsuccessful uploads
                     trySend(ResultState.Error(exception?.localizedMessage.toString()))
                 }
             }
            awaitClose {
                close()
            }


    }

    override suspend fun getAllCategory(): Flow<ResultState<List<CategoryModel>>> =
        callbackFlow {
            trySend(ResultState.Loading)


            database.reference.child(Constants.CATEGORY_REF).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val categories = snapshot.children.mapNotNull {
                        it.getValue(CategoryModel::class.java)
                    }
                    Log.d("catlist", "CategoryScreen: "+categories.toString())
                    trySend(ResultState.Success(categories))

                }

                override fun onCancelled(error: DatabaseError) {
                    trySend(ResultState.Error(error.message))
                }
            })

            awaitClose {
                close()
            }

        }

    override suspend fun getAllMovies(): Flow<ResultState<List<MovieModel>>> =
        callbackFlow {
            trySend(ResultState.Loading)


            database.reference.child(Constants.MOVIES_REF).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val movies = snapshot.children.mapNotNull {
                        it.getValue(MovieModel::class.java)
                    }
                    Log.d("movielist", "MovieScreen: "+movies.toString())
                    trySend(ResultState.Success(movies))

                }

                override fun onCancelled(error: DatabaseError) {
                    trySend(ResultState.Error(error.message))
                }
            })

            awaitClose {
                close()
            }

        }

    override suspend fun addMovie(movieModel: MovieModel): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)
            val movieId = database.reference.child(Constants.MOVIES_REF).push().key
            movieModel.movieId = movieId.toString()
            movieId?.let { id ->
                movieModel.movieId = id
                database.reference.child(Constants.MOVIES_REF).child(id).setValue(movieModel)
                    .addOnSuccessListener {
                        Log.d("TAG", "addmovie: " + movieModel)
                        trySend(ResultState.Success(" movie Added"))
                    }.addOnFailureListener {
                        trySend(ResultState.Error(it.localizedMessage ?: "Something went wrong"))
                    }
                awaitClose() {
                    close()

                }
            }

        }




    override suspend fun uploadMovieImages(
        imageUri: Uri?,
        data: ByteArray?
    ): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        if (imageUri != null) {
            firebaseStorage.reference.child(Constants.MOVIE_IMAGE_REF + "/${Constants.MOVIE_IMAGE_REF+System.currentTimeMillis()}")
                .putFile(imageUri ?: Uri.EMPTY).addOnCompleteListener {
                    it.result.storage.downloadUrl.addOnSuccessListener { imageUrl ->
                        trySend(ResultState.Success(imageUrl.toString()))
                    }
                    if (it.exception != null) {
                        trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                    }

                }
            awaitClose {
                close()
            }
        }

        if(data!=null) {
            // Upload the ByteArray to Firebase Storage
            val uploadTask =
                firebaseStorage.reference.child(Constants.MOVIE_IMAGE_REF + "/${Constants.MOVIE_IMAGE_REF+System.currentTimeMillis()}")
                    .putBytes(data)
            uploadTask.addOnSuccessListener { imageUrl ->
                trySend(ResultState.Success(imageUrl.uploadSessionUri.toString()))
            }.addOnFailureListener { exception ->
                // Handle unsuccessful uploads
                trySend(ResultState.Error(exception?.localizedMessage.toString()))
            }
        }
        awaitClose {
            close()
        }


    }

    override suspend fun uploadMovieCover(
        imageUri: Uri?,
        data: ByteArray?
    ): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        if (imageUri != null) {
            firebaseStorage.reference.child(Constants.MOVIE_COVER_REF + "/${Constants.MOVIE_COVER_REF+System.currentTimeMillis()}")
                .putFile(imageUri ?: Uri.EMPTY).addOnCompleteListener {
                    it.result.storage.downloadUrl.addOnSuccessListener { imageUrl ->
                        trySend(ResultState.Success(imageUrl.toString()))
                    }
                    if (it.exception != null) {
                        trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                    }

                }
            awaitClose {
                close()
            }
        }

        if(data!=null) {
            // Upload the ByteArray to Firebase Storage
            val uploadTask =
                firebaseStorage.reference.child(Constants.MOVIE_COVER_REF + "/${Constants.MOVIE_COVER_REF+System.currentTimeMillis()}")
                    .putBytes(data).addOnCompleteListener {
                        it.result.storage.downloadUrl.addOnSuccessListener { imageUrl ->
                            trySend(ResultState.Success(imageUrl.toString()))
                        }
                        if (it.exception != null) {
                            trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                        }
                        }
                    }
                awaitClose {
                    close()
                }
        }


    override suspend fun getAllBooking(): Flow<ResultState<List<BookingModel>>> =
        callbackFlow {
            trySend(ResultState.Loading)


            database.reference.child(Constants.BOOKING_REF).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val bookings = snapshot.children.mapNotNull {
                        it.getValue(BookingModel::class.java)
                    }
                    Log.d("booking", "bokkinglist: "+bookings.toString())
                    trySend(ResultState.Success(bookings))

                }

                override fun onCancelled(error: DatabaseError) {
                    trySend(ResultState.Error(error.message))
                }
            })

            awaitClose {
                close()
            }

        }

    override suspend fun uploadImage( imageUri: Uri?, data: ByteArray?,type:String): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        if (imageUri != null) {
            firebaseStorage.reference.child(type + "/${type+System.currentTimeMillis()}")
                .putFile(imageUri ?: Uri.EMPTY).addOnCompleteListener {
                    it.result.storage.downloadUrl.addOnSuccessListener { imageUrl ->
                        trySend(ResultState.Success(imageUrl.toString()))
                    }
                    if (it.exception != null) {
                        trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                    }

                }
            awaitClose {
                close()
            }
        }

        if(data!=null) {
            // Upload the ByteArray to Firebase Storage
            val uploadTask =
                firebaseStorage.reference.child(type + "/${type+System.currentTimeMillis()}")
                    .putBytes(data).addOnCompleteListener {
                        it.result.storage.downloadUrl.addOnSuccessListener { imageUrl ->
                            trySend(ResultState.Success(imageUrl.toString()))
                        }
                        if (it.exception != null) {
                            trySend(ResultState.Error(it.exception?.localizedMessage.toString()))
                        }
                    }
        }
        awaitClose {
            close()
        }
    }

    override suspend fun createSlider(note: String, sliderImgUrl: String):Flow<ResultState<String>> =
        callbackFlow{
        trySend(ResultState.Loading)
        val reference = database.reference.child(Constants.SLIDER_DOCUMENT)
        val sliderId = reference.push().key
        val timestamp = System.currentTimeMillis()

        val slider = SliderModel(sliderId!!, sliderImgUrl, note, timestamp)
        reference.child(sliderId).setValue(slider).addOnSuccessListener {
            trySend(ResultState.Success("Slider created"))
        }.addOnFailureListener {
            trySend(ResultState.Error(it.localizedMessage ?: "Something went wrong"))

        }

         awaitClose(){
             close()
         }
    }

    override suspend fun getAllSliders(): Flow<ResultState<List<SliderModel>>> =
        callbackFlow {
            trySend(ResultState.Loading)


            database.reference.child(Constants.SLIDER_DOCUMENT)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val sliders = snapshot.children.mapNotNull {
                            it.getValue(SliderModel::class.java)
                        }
                        Log.d("sliders", "sliderList: " + sliders.toString())
                        trySend(ResultState.Success(sliders))

                    }

                    override fun onCancelled(error: DatabaseError) {
                        trySend(ResultState.Error(error.message))
                    }
                })

            awaitClose {
                close()
            }
        }

    override suspend fun getAllNotificationList(): Flow<ResultState<List<NotificationModel>>> =
        callbackFlow {
            trySend(ResultState.Loading)


            database.reference.child(Constants.NOTIFICATION_REF).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val notifications = snapshot.children.mapNotNull {
                        it.getValue(NotificationModel::class.java)
                    }
                    Log.d("notification", "notification list: "+notifications.toString())
                    trySend(ResultState.Success(notifications))

                }

                override fun onCancelled(error: DatabaseError) {
                    trySend(ResultState.Error(error.message))
                }
            })

            awaitClose {
                close()
            }

        }

    override suspend fun addNotification(notificationModel: NotificationModel): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)
            val notificationId = database.reference.child(Constants.NOTIFICATION_REF).push().key
            notificationModel.notificationId = notificationId.toString()
            notificationId?.let { id ->
                notificationModel.notificationId = id
                database.reference.child(Constants.NOTIFICATION_REF).child(id).setValue(notificationModel)
                    .addOnSuccessListener {
                        Log.d("TAG", "notification: " + notificationModel)
                        trySend(ResultState.Success(" noti Added"))
                    }.addOnFailureListener {
                        trySend(ResultState.Error(it.localizedMessage ?: "Something went wrong"))
                    }
                awaitClose() {
                    close()

                }
            }

        }

}


