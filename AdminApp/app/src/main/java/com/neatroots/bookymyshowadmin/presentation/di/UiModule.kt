package com.neatroots.bookymyshowadmin.presentation.di

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.neatroots.bookymyshowadmin.data.repo.BookMyShowAdminRepoImpl
import com.neatroots.bookymyshowadmin.domain.repo.BookMyShowAdminRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class UiModule {
    @Provides
    fun provideBookMyShowRepo(firebaseDatabase: FirebaseDatabase,firebaseStorage: FirebaseStorage): BookMyShowAdminRepo {
     return BookMyShowAdminRepoImpl(firebaseDatabase,firebaseStorage)
    }
}