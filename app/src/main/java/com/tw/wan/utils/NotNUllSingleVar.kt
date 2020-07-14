package com.tw.wan.utils

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @author thp
 * time 2020/4/24
 * desc:变量只能赋值一次
 */
class NotNUllSingleVar<T> : ReadWriteProperty<Any?, T> {
    private var value: T? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value ?: throw IllegalStateException("${property.name} require not null")
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = if (this.value == null) value else this.value

    }

}