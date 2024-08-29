package com.neatroots.bookymyshowadmin.data.repo

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.neatroots.bookymyshowadmin.utils.Constants
import com.neatroots.bookymyshowadmin.common.ResultState
import com.neatroots.bookymyshowadmin.domain.repo.BookMyShowAdminRepo
import com.neatroots.bookymyshowadmin.model.CategoryModel
import com.neatroots.bookymyshowadmin.model.MovieModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.io.ByteArrayOutputStream
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

    override suspend fun uploadCategoryImage(
        imageUri: Uri?,
        bitmap: Bitmap?
    ): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        if (imageUri != null) {
            firebaseStorage.reference.child(Constants.CATEGORY_IMAGE_REF + "/${System.currentTimeMillis()}")
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
        if (bitmap != null) {
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos)
            val data = baos.toByteArray()

            // Upload the ByteArray to Firebase Storage
            val uploadTask =
                firebaseStorage.reference.child(Constants.CATEGORY_IMAGE_REF + "/${System.currentTimeMillis()}")
                    .putBytes(data)
            uploadTask.addOnSuccessListener { imageUrl ->
                trySend(ResultState.Success(imageUrl.toString()))
            }.addOnFailureListener { exception ->
                // Handle unsuccessful uploads
                trySend(ResultState.Error(exception?.localizedMessage.toString()))
            }

            awaitClose {
                close()
            }

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
                database.reference.child(Constants.CATEGORY_REF).child(id).setValue(movieId)
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
}