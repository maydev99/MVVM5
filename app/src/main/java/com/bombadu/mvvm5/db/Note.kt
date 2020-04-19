package com.bombadu.mvvm5.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "notes_table")
data class Note(

    @PrimaryKey

    @ColumnInfo(name = "title") var title: String,

    @ColumnInfo(name = "description") var description: String,

    @ColumnInfo(name = "priority") var priority: Int)













