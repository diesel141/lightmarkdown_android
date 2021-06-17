package jp.co.lightmarkdown_android

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

/*
 * 編集画面
 */
class EditActivity : AppCompatActivity() {
    // タイトル
    private lateinit var titleText: EditText
    // 本文
    private lateinit var editText: EditText
    // 登録番号
    private lateinit var regId: String

    // TODO プリファレンス保存用のキー定義(DB共通処理置き換え後削除)
    companion object {
        private const val FILENAME = "sample"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        // 一覧画面から選択された登録IDを取得
        regId = intent.getStringExtra("id")
        // 登録IDが取得できなかった場合
        if (regId.isNullOrEmpty()) {
            // 新規登録なので、なにもせず画面表示
            println("新規登録")
            return
        }

        println("登録有り")
        // TODO プリファレンスに保存されている値取得(DB検索処理に置き換え予定：登録IDで検索)
        val pref: SharedPreferences = getSharedPreferences(FILENAME, Context.MODE_PRIVATE)
        var str = pref.getString(regId, "NONE")

        // TODO DB検索取得結果から反映に置き換え予定
        // タイトル
        titleText = findViewById<EditText>(R.id.titleView)
        // 本文
        editText = findViewById<EditText>(R.id.editView)
        // 画面反映
        editText.setText(str)
        titleText.setText(regId)
    }

    override fun onPause() {
        super.onResume();
        println("onPause")
        // 入力内容取得
        var titleVal = titleText.text.toString()
        var editVal = editText.text.toString()

        // TODO DB登録更新処理(DB登録処理置き換え予定 キー：登録ID)
        println(regId)
        println("DB登録")
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
            R.id.menu_list -> {
                println("リストボタン押下")
                // 一覧画面へ戻る
                val intent = Intent(application, ListActivity::class.java)
                startActivity(intent)
                return true
            }
            // プレビューボタン押下時
            R.id.menu_preview -> {
                println("プレビューボタン押下")
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
}