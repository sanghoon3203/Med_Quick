package com.example.quick_med

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MedicineDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_MEDICINE_TABLE = ("CREATE TABLE $TABLE_MEDICINE ("
                + "$COLUMN_ID INTEGER PRIMARY KEY,"
                + "$COLUMN_NAME TEXT,"
                + "$COLUMN_DESCRIPTION TEXT,"
                + "$COLUMN_IMAGE_URL TEXT,"
                + "$COLUMN_DOSAGE TEXT,"
                + "$COLUMN_SIDE_EFFECTS TEXT" + ")")
        db.execSQL(CREATE_MEDICINE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_MEDICINE")
        onCreate(db)
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "medicineDB.db"
        const val TABLE_MEDICINE = "medicines"
        const val COLUMN_ID = "_id"
        const val COLUMN_NAME = "name"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_IMAGE_URL = "imageUrl"
        const val COLUMN_DOSAGE = "dosage"
        const val COLUMN_SIDE_EFFECTS = "sideEffects"
    }
}
