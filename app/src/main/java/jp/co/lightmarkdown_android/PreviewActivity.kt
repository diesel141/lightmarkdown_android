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

        // markwon instance
        val markwon = Markwon.create(this)

        // markdown input
        val md = """
      # Heading
      
      > A quote
      
      **bold _italic_ bold**
    """.trimIndent()

        markwon.setMarkdown(binding.markdownPreviewContent, md);

    }
}
