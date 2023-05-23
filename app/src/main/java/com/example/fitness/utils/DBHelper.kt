package com.example.filemanager.usable

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private val DATABASE_NAME = "FITNESSDB"
        private val DATABASE_VERSION = 1
        val MAIN_TABLE_NAME = "tokens"
        val ID_COL = "_id"
        val FILE_COl = "token"
    }
    override fun onCreate(db: SQLiteDatabase) {
        var query = ("CREATE TABLE " + MAIN_TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                FILE_COl + " TEXT " +")")
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + MAIN_TABLE_NAME)
        onCreate(db)
    }
}