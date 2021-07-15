package jp.co.lightmarkdown_android.provider

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * DB接続のヘルパクラス
 */
class LmDataBase(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    /** テーブル名定義 */
    interface Tables {
        companion object {
            /** 登録情報テーブル */
            const val RAW_TEXT = "raw_text"
        }
    }

    /**
     * 標準列名定義
     */
    interface BaseColumns {
        companion object {
            /** 標準のユニークID */
            const val _ID = "_id"
        }
    }

    /**
     * 登録情報の列定義
     */
    interface RawTextColumns {
        companion object {
            /** タイトルの列名 */
            const val TITLE = "title"

            /** 本文の列名 */
            const val TEXT = "text"

            /** 更新日時の列名 */
            const val UPDATE_TIME = "update_time"
        }
    }

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        createRawText(sqLiteDatabase)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase?, i: Int, i1: Int) {
        // ignore
    }


    /**
     * サイト情報テーブルを作成します.
     *
     * @param db
     */
    private fun createRawText(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS "
                    + Tables.RAW_TEXT
                    + " ("
                    + BaseColumns._ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                    + RawTextColumns.TITLE + " TEXT, "
                    + RawTextColumns.TEXT + " TEXT, "
                    + RawTextColumns.UPDATE_TIME + " INTEGER"
                    + ");"
        )
    }

    companion object {
        private const val DB_NAME = "lightmarkdown_android.db"
        private const val DB_VERSION = 1
    }
}
