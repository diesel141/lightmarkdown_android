package jp.co.lightmarkdown_android.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import jp.co.lightmarkdown_android.provider.LmDataBase
import jp.co.lightmarkdown_android.provider.LmDataBase.RawTextColumns.Companion.TEXT
import jp.co.lightmarkdown_android.provider.LmDataBase.RawTextColumns.Companion.TITLE
import jp.co.lightmarkdown_android.provider.LmDataBase.RawTextColumns.Companion.UPDATE_TIME
import jp.co.lightmarkdown_android.provider.LmDataBase.Tables.Companion.RAW_TEXT


class RawTextDao(context: Context) : LmDataBase.Tables, LmDataBase.RawTextColumns,
    LmDataBase.BaseColumns {
    private val context = context

    /**
     * 登録情報を登録します
     *
     * @param title タイトル
     * @param text 本文
     * @return 登録件数
     */
    fun insert(
        title: String,
        text: String?
    ): Long {
        val db = LmDataBase(context)
        val values = ContentValues()
        values.put(TITLE, title)
        values.put(TEXT, text)
        values.put(UPDATE_TIME, System.currentTimeMillis())
        var result = 0L
        try {
            result = db.writableDatabase.insert(RAW_TEXT, null, values)
        } finally {
            db.close()
            return result
        }
    }

    /**
     * 登録情報を更新します
     *
     * @param id ID
     * @param title タイトル
     * @param text 本文
     * @return 更新件数
     */
    fun update(
        id: Int,
        title: String?,
        text: String?
    ): Int {
        val db = LmDataBase(context)
        val values = ContentValues()
        values.put(TITLE, title)
        values.put(TEXT, text)
        values.put(UPDATE_TIME, System.currentTimeMillis())
        val whereClause =
            LmDataBase.BaseColumns._ID + " = ?"
        val whereArgs = arrayOfNulls<String>(1)
        whereArgs[0] = id.toString()
        var result = 0
        try {
            result = db.writableDatabase.update(RAW_TEXT, values, whereClause, whereArgs)
        } finally {
            db.close()
            return result
        }
    }

    /**
     * 登録情報を削除します
     *
     * @param id ID
     * @return 削除件数
     */
    fun delete(id: Int): Int {
        val db = LmDataBase(context)
        val whereClause = LmDataBase.BaseColumns._ID + " = ?"
        val whereArgs = arrayOfNulls<String>(1)
        whereArgs[0] = id.toString()
        var result = 0
        try {
            result = db.writableDatabase.delete(RAW_TEXT, whereClause, whereArgs)
        } finally {
            db.close()
            return result
        }
    }

    /**
     * IDに紐づく登録情報を返却します
     *
     * @param id ID
     * @return 登録情報カーソル
     */
    fun selectById(id: Int): Cursor? {
        val db = LmDataBase(context)
        val cols = arrayOf(
            LmDataBase.BaseColumns._ID,
            TITLE,
            TEXT,
            UPDATE_TIME
        )
        val selection =
            LmDataBase.BaseColumns._ID + " = ?"
        val selectionArgs = arrayOf(id.toString())
        val groupBy: String? = null
        val having: String? = null
        val orderBy: String? = "$UPDATE_TIME DESC"
        return db.readableDatabase
            .query(RAW_TEXT, cols, selection, selectionArgs, groupBy, having, orderBy)
    }

    /**
     * 全ての登録情報を返却します
     *
     * @return 登録情報カーソル
     */
    fun selectAll(): Cursor? {
        val db = LmDataBase(context)
        val cols = arrayOf(
            LmDataBase.BaseColumns._ID,
            TITLE,
            TEXT,
            UPDATE_TIME
        )
        val orderBy: String? = "$UPDATE_TIME DESC"
        return db.readableDatabase
            .query(RAW_TEXT, cols, null, null, null, null, orderBy)
    }
}
