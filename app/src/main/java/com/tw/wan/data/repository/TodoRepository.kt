package com.tw.wan.data.repository

import com.tw.wan.App

/**
 * @author thp
 * time 2020/6/18
 * desc:
 */
class TodoRepository {
    companion object {
        fun getInstance(): TodoRepository =
            Holder.mInstance
    }

    private object Holder {
        val mInstance = TodoRepository()
    }

    private val service by lazy { App.wanApi }
    suspend fun loadTodoData(pageNo: Int, map: HashMap<String, Any>) =
        service.loadTodoData(pageNo, map)

    suspend fun deleteTodo(id: Int) =
        service.deleteTodo(id)

    suspend fun doneTodo(id: Int, status: Int) =
        service.doneTodo(id, status)

    suspend fun addTodo(map: HashMap<String, Any>) =
        service.addTodo(map)

    suspend fun updateTodo(id: Int, map: HashMap<String, Any>) = service.updateTodo(id, map)

}