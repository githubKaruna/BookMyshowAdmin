package com.neatroots.bookymyshowadmin.data.repo

import android.net.Uri
import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.neatroots.bookymyshowadmin.utils.Constants
import com.neatroots.bookymyshowadmin.common.ResultState
import com.neatroots.bookymyshowadmin.domain.repo.BookMyShowAdminRepo
import com.neatroots.bookymyshowadmin.model.CategoryModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class BookMyShowAdminRepoImpl @Inject constructor(private val database: FirebaseDatabase,private val firebaseStorage: FirebaseStorage) : BookMyShowAdminRepo {
    override suspend fun addCategory(categoryModel: CategoryModel): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        val categoryId = database.reference.child(Constants.CATEGORY_REF).push().key
        categoryModel.categoryId = categoryId.toString()
        categoryId?.let { id ->
            categoryModel.categoryId = id
            database.reference.child(Constants.CATEGORY_REF).child(id).setValue(categoryModel)
                .addOnSuccessListener {
                    Log.d("TAG", "addCategory: "+categoryId)
                  trySend(ResultState.Success(" Added"))
                }.addOnFailureListener {
                    trySend(ResultState.Error(it.localizedMessage?:"Something went wrong"))
                }
            awaitClose(){
                close()

            }
        }

    }
    override suspend fun uploadCategoryImage(imageUri: Uri): Flow<ResultState<String>> = callbackFlow{
        trySend(ResultState.Loading)

        firebaseStorage.reference.child(Constants.CATEGORY_IMAGE_REF +"/${System.currentTimeMillis()}")
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

}