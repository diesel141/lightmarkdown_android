package jp.co.lightmarkdown_android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.noties.markwon.Markwon
import kotlinx.android.synthetic.main.activity_preview.*

class PreviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)
        // markwon instance
        val markwon = Markwon.create(this)

        // markdown input
        val md = """
      # Heading
      
      > A quote
      
      **bold _italic_ bold**
    """.trimIndent()

        markwon.setMarkdown(markdownPreviewContent, md);

    }
}