package jp.co.lightmarkdown_android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.noties.markwon.Markwon
import jp.co.lightmarkdown_android.databinding.ActivityPreviewBinding

class PreviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 戻るボタン
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        val mdContent = content?.trimIndent() ?:""

        binding.markdownPreviewTitle.text = title

        // markwon instance
        val markwon = Markwon.create(this)
        markwon.setMarkdown(binding.markdownPreviewContent, mdContent)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
