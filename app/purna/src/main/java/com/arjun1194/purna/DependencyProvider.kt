package com.arjun1194.purna

/**
 * A dependency provider manages the module
 * by performing reflection on the module and setting properties on it
 */
interface DependencyProvider {
    fun <T> get(clazz: Class<T>): T
}