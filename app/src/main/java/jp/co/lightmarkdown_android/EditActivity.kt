package jp.co.lightmarkdown_android

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView;
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditActivity : AppCompatActivity() {
    private lateinit var titleText: EditText
    private lateinit var editText: EditText

    companion object {
        private const val FILENAME = "sample"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val title = intent.getStringExtra("TITLE")

        // プリファレンス使ってるのでNG

        // データ書き込み
        val pref: SharedPreferences = getSharedPreferences(FILENAME, Context.MODE_PRIVATE)
        if (pref != null) {
            val editor = pref.edit()
            // タイトル
            titleText = findViewById<EditText>(R.id.titleView)
            // 本文
            editText = findViewById<EditText>(R.id.editView)

            if (title != "") {
                // 保存
                editor.putString(title, "さしすせそ")
            }
            editor.commit()
        }

        // データ読み込み
        if (pref != null) {
//            val title = intent.getStringExtra("TITLE")
            // タイトル
            titleText = findViewById<EditText>(R.id.titleView)
            // 本文
            editText = findViewById<EditText>(R.id.editView)

            // 取得
            var str = pref.getString(title, "NONE")
            titleText.setText(title)
            editText.setText(str)

            println("正常")
        } else {
            println("あいうえお")
        }
    }

    fun onPause(v: View?) {
        println("かきこみ実行")
        // データ書き込み
        val pref: SharedPreferences = getSharedPreferences(FILENAME, Context.MODE_PRIVATE)
        if (pref != null) {
            val editor = pref.edit()
            // タイトル
            titleText = findViewById<EditText>(R.id.titleView)
            // 本文
            editText = findViewById<EditText>(R.id.editView)
            var titleVal = titleText.text.toString()
            var editVal = editText.text.toString()
            if (titleVal != "" && editVal != "") {
                // 保存
                editor.putString(titleVal, editVal)
            }
            editor.commit()
        }
    }
}