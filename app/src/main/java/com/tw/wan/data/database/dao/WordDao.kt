package com.tw.wan.data.database.dao

import androidx.room.*
import com.tw.wan.data.database.bean.Word

/**
 * @author thp
 * time 2020/5/13
 * desc:
 */
@Dao
interface WordDao {
    //重复数据会自动替换
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: Word)

    @Delete
    suspend fun delete(word: Word)

    @Query("DELETE FROM word_table")
    suspend fun deleteAll()

    @Query("SELECT * from word_table")
    suspend fun getAllWords(): List<Word>

}