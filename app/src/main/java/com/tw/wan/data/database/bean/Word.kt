package com.tw.wan.data.database.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author thp
 * time 2020/5/13
 * desc:
 */
@Entity(tableName = "word_table")
data class Word(
    @PrimaryKey
    var word: String = ""
)