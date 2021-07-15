package jp.co.lightmarkdown_android

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import jp.co.lightmarkdown_android.adapter.LmCursorAdapter
import jp.co.lightmarkdown_android.dao.RawTextDao
import jp.co.lightmarkdown_android.provider.LmDataBase

/**
 * 一覧画面
 */
class LmListActivity : AppCompatActivity(), LmDataBase.BaseColumns, LmDataBase.RawTextColumns {
    private lateinit var titleText: EditText
    private lateinit var listView: ListView
    private lateinit var adapter: LmCursorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lm_list)

        // タイトル
        titleText = findViewById(R.id.titleView2)

        // 新規作成ボタン
        findViewById<ImageView>(R.id.add).setOnClickListener {
            transitionEdit()
        }
    }

    override fun onResume() {
        super.onResume()
        initlist()
    }

    /**
     * 対象マークダウン情報を削除します
     * @param id ID
     */
    fun deleteTarget(id: Int, isEmpty: Boolean) {
        RawTextDao(applicationContext).delete(id)
        Toast.makeText(applicationContext, getString(R.string.message_delete), Toast.LENGTH_LONG)
            .show()
        if (isEmpty) transitionEdit() else adapter.swapCursor(RawTextDao(applicationContext).selectAll())
    }

    private fun initlist() {
        RawTextDao(applicationContext).selectAll()?.let {
            if (it.count < 1) {
                transitionEdit()
                return
            }
            adapter = LmCursorAdapter(
                this,
                it,
                0
            )
            listView = findViewById(R.id.list_view)
            listView.adapter = adapter
        }
    }

    /**
     * 編集画面に繊維します
     * @param id ID
     */
    private fun transitionEdit(id: Int? = null) {
        val intent = Intent(applicationContext, EditActivity::class.java)
        id?.let {
            intent.putExtra(EditActivity.INTENT_KEY_ID, it)
        }
        startActivity(intent)
    }
}
