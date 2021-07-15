package jp.co.lightmarkdown_android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import jp.co.lightmarkdown_android.dao.RawTextDao
import jp.co.lightmarkdown_android.provider.LmDataBase
import jp.co.lightmarkdown_android.provider.LmDataBase.RawTextColumns.Companion.TEXT
import jp.co.lightmarkdown_android.provider.LmDataBase.RawTextColumns.Companion.TITLE
import kotlin.properties.Delegates

/*
 * 編集画面
 */
class EditActivity : AppCompatActivity(), LmDataBase.RawTextColumns {

    /** DB登録ID */
    private var id by Delegates.notNull<Int>()

    // タイトル
    private lateinit var titleText: EditText

    // 本文
    private lateinit var editText: EditText

    // TODO プリファレンス保存用のキー定義(DB共通処理置き換え後削除)
    companion object {
        const val INTENT_KEY_ID = "id"
        private const val FILENAME = "sample"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator( R.drawable.ic_baseline_format_list_bulleted_24 )

        titleText = findViewById(R.id.titleView)
        editText = findViewById(R.id.editView)

        // 一覧画面から選択された登録IDを取得
        id = intent.getIntExtra(INTENT_KEY_ID, -1)

        // 登録IDが取得できなかった場合
        if (id == -1) {
            // 新規登録なので、なにもせず画面表示
            Log.d("EditActivity", "新規登録")
        } else {
            Log.d("EditActivity", "登録有り")
            initMarkDown()
        }
    }

    override fun onPause() {
        super.onPause();
        saveMarkDown()
    }

    /*
    * メニューボタン押下時の処理
    */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 初期化＆DB保存の為、このタイミングで入力値設定
        // タイトル
        titleText = findViewById<EditText>(R.id.titleView)
        // 本文
        editText = findViewById<EditText>(R.id.editView)

        // どのボタンが押されたか判定
        when (item.itemId) {
            // リストボタン押下時
            android.R.id.home -> {
                Log.d("EditActivity", "リストボタン押下")
                finish()
                return true
            }
            // プレビューボタン押下時
            R.id.menu_preview -> {
                Log.d("EditActivity", "プレビューボタン押下")
                // プレビュー画面へ遷移(タイトルと本文を遷移先へ渡す)
                val intent = Intent(application, PreviewActivity::class.java)
                intent.putExtra("title", titleText.text.toString())
                intent.putExtra("content", editText.text.toString())
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /*
    * メニュー
    */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        // 編集画面のメニューレイアウト読み込み
        inflater.inflate(R.menu.menu_edit, menu)
        return true
    }

    /**
     * マークダウン情報をDBから取得し、反映します
     */
    private fun initMarkDown() {
        RawTextDao(applicationContext).selectById(id).let {
            if (it?.moveToFirst()!!) {
                titleText.setText(it?.getString(it.getColumnIndex(TITLE)))
                editText.setText(it?.getString(it.getColumnIndex(TEXT)))
            }
        }
    }

    /**
     * マークダウン情報をDBに保存します
     */
    private fun saveMarkDown() {
        val title = titleText.text.toString()
        val text = editText.text.toString()
        if (title.isNullOrEmpty() && text.isNullOrEmpty()) return
        if (id != -1) {
            RawTextDao(applicationContext).update(
                id,
                titleText.text.toString(),
                editText.text.toString()
            )
        } else {
            RawTextDao(applicationContext).insert(
                titleText.text.toString(),
                editText.text.toString()
            )
        }
        Toast.makeText(applicationContext, getString(R.string.message_save), Toast.LENGTH_LONG)
            .show()
    }
}
