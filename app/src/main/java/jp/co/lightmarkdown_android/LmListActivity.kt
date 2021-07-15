package jp.co.lightmarkdown_android

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

// 画面遷移確認用(スタブ)
class LmListActivity : AppCompatActivity() {
    private lateinit var titleText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lm_list)
        val sendButton: Button = findViewById(jp.co.lightmarkdown_android.R.id.send_button)

        // タイトル
        titleText = findViewById<EditText>(R.id.titleView2)

        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        println(title)
        println(content)

        sendButton.setOnClickListener {
            val intent = Intent(application, EditActivity::class.java)
            intent.putExtra("id", titleText.text.toString())
            startActivity(intent)
        }
    }
}