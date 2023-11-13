package com.arjun1194.purnadiexample.di

import android.content.Context
import com.arjun1194.purna.Module
import com.arjun1194.purnadiexample.MyRepository
import com.arjun1194.purnadiexample.MyService
import com.arjun1194.purnadiexample.MyViewModel

class MyModule(
    val applicationContext: Context,
) : Module {

    fun provideMyViewModel(myRepository: MyRepository) = MyViewModel(myRepository)

    fun provideMyRepository(myService: MyService) = MyRepository(myService)

    fun provideMyService() = MyService()
}