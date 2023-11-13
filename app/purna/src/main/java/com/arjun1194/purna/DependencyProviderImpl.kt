package com.arjun1194.purna

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.lang.reflect.Field
import java.lang.reflect.Method

// TODO(arjun)[13 Nov 2023] - refactor

class DependencyProviderImpl(
    private val module: Module
) : DependencyProvider {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T> get(clazz: Class<T>): T {
        val method = findProviderMethod(clazz)
        val propertyName = method.returnType.name.lowercase()
        return if (module.hasProperty(propertyName)) {
            Log.d(TAG, "module already had $propertyName")
            module.getProperty(propertyName)
        } else {
            val component = method.getReturnTypeObject<T>()
            module.setProperty(component!!::class.java.name.lowercase(), component)
            Log.d(TAG, " module property has been created and saved in the module $propertyName")
            component
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun <T> Method.getReturnTypeObject(): T =
        when (parameterCount) {
            0 -> call(module)

            1 -> parameters[0].let { param ->
                val paramValue = get(param.type.kotlin.java)
                call(module, paramValue)
            }
            else ->
                parameters
                    .map { get(it.type.kotlin.java) }
                    .let { call(module, it) }
        }


    private fun <T> Any.getProperty(fieldName: String): T {
        val field: Field = javaClass.getDeclaredField(fieldName)
        field.isAccessible = true
        return field.get(this) as T
    }

    private fun Any.hasProperty(fieldName: String): Boolean {
        val properties = getAllFields()
        for (field in properties) {
            if (field.name.equals(fieldName)) {
                return true
            }
        }
        return false
    }

    private fun Any.getAllFields(): List<Field> {
        val fields: MutableList<Field> = ArrayList()
        // no need to check super class declared fields
        for (field in this::class.java.declaredFields) {
            fields.add(field)
        }
        return fields
    }


    private fun Any.setProperty(fieldName: String, fieldValue: Any?): Boolean {
        var clazz: Class<*>? = javaClass
        while (clazz != null) {
            clazz = try {
                val field: Field = clazz.getDeclaredField(fieldName)
                field.isAccessible = true
                field.set(this, fieldValue)
                return true
            } catch (e: NoSuchFieldException) {
                clazz.superclass
            } catch (e: Exception) {
                throw IllegalStateException(e)
            }
        }
        return false
    }


    private fun <T> Method.call(obj: Any, vararg params: Any): T {
        return when (parameterCount) {
            0 -> invoke(obj) as T
            1 -> invoke(obj, params[0]) as T
            else -> invoke(obj, *params) as T
        }
    }

    private fun <T> findProviderMethod(clazz: Class<T>): Method {
        val providerMethod = module::class.java.methods.find {
            it.returnType == clazz
        }

        return providerMethod ?: throw DependencyInjectionMethodNotFoundError()
    }

    companion object {
        private const val TAG = "File"
    }

}