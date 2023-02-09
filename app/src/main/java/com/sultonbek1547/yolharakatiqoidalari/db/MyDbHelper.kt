package com.sultonbek1547.yolharakatiqoidalari.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.sultonbek1547.yolharakatiqoidalari.MyModel

class MyDbHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION),
    MyDbInterface {
    companion object {
        const val DB_NAME = "db_nameE"
        const val DB_VERSION = 1
        const val ID = "id"
        const val USER_TABLE = "contact_table_3"
        const val USER_NAME = "contact_name"
        const val USER_ABOUT = "contact_surname"
        const val USER_WHICH_TYPE = "contact_number"
        const val LIKED_STATE = "contact_state"
        const val PHOTO_PATH = "contact_path"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val contactQuery =
            "create table $USER_TABLE(ID integer not null primary key autoincrement unique, $USER_NAME text not null, $USER_ABOUT text not null, $USER_WHICH_TYPE integer not null, $LIKED_STATE integer not null, $PHOTO_PATH text not null)"
        p0?.execSQL(contactQuery)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}
    override fun addUser(user: MyModel) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(USER_NAME, user.name)
        contentValues.put(USER_ABOUT, user.about)
        contentValues.put(USER_WHICH_TYPE, user.whichType)
        contentValues.put(LIKED_STATE, user.likedState)
        contentValues.put(PHOTO_PATH, user.photoPath)
        database.insert(USER_TABLE, null, contentValues)
        database.close()

    }

    override fun getAllUsers(): ArrayList<MyModel> {
        val database = this.writableDatabase
        val cursor = database.rawQuery("select * from $USER_TABLE", null)
        val list = ArrayList<MyModel>()
        if (cursor.moveToFirst()) {
            do {
                list.add(
                    MyModel(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getInt(4),
                        cursor.getString(5)
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    override fun deleteUser(user: MyModel) {
        val database = this.writableDatabase
        database.delete(USER_TABLE, "id=?", arrayOf(user.id.toString()))
        database.close()
        database.close()
    }

    override fun editUser(user: MyModel): Int {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(USER_NAME, user.name)
        contentValues.put(USER_ABOUT, user.about)
        contentValues.put(USER_WHICH_TYPE, user.whichType)
        contentValues.put(PHOTO_PATH, user.photoPath)
        contentValues.put(LIKED_STATE, user.likedState)

        return database.update(
            USER_TABLE,
            contentValues,
            "$ID=?",
            arrayOf(user.id.toString())
        )
    }

    override fun getLikedUsers(): ArrayList<MyModel> {
        val database = this.writableDatabase
        val cursor = database.rawQuery("select * from $USER_TABLE", null)
        val list = ArrayList<MyModel>()
        var user: MyModel? = null
        if (cursor.moveToFirst()) {
            do {
                user = MyModel(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getString(5)
                )
                if (user.likedState == 1) {
                    list.add(user)
                }
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }
}


interface MyDbInterface {
    fun addUser(user: MyModel)
    fun getAllUsers(): ArrayList<MyModel>
    fun deleteUser(user: MyModel)
    fun editUser(user: MyModel): Int
    fun getLikedUsers(): ArrayList<MyModel>

}