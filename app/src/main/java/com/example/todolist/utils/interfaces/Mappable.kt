package com.example.todolist.utils.interfaces

import com.example.todolist.utils.extensions.capitalizeFirstLetter
import kotlin.reflect.KProperty

interface Mappable {
    fun propertiesToMap(): Map<String, Any?> {
        val properties = this::class.members.filterIsInstance<KProperty<*>>()
        return properties.associate { property ->
            property.name to property.getter.call(this)
        }
    }
}