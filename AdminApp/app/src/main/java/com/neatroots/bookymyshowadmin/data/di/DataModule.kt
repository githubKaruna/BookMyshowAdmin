package com.neatroots.bookymyshowadmin.data.di

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
object DataModule {

    @Provides
    fun provideDataRepository(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }
    @Provides
    fun provideFirebaseStorage(): FirebaseStorage
    {
        return FirebaseStorage.getInstance()
    }
}
