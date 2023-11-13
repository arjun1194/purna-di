package com.arjun1194.purna

import android.os.Build
import androidx.annotation.RequiresApi


@RequiresApi(Build.VERSION_CODES.O)
inline fun <reified T> inject(module: Module) = lazy {
    DependencyProviderImpl(module).get(T::class.java)
}