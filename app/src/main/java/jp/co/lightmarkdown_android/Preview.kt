package jp.co.lightmarkdown_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import io.noties.markwon.Markwon
import java.security.AccessController.getContext

class Preview : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)
        val textView = findViewById<TextView>(R.id.textView)
        // markwon instance
        val markwon = Markwon.create(this)

        // markdown input
        val md = """
      # Heading
      
      > A quote
      
      **bold _italic_ bold**
    """.trimIndent()

        markwon.setMarkdown(textView, md);

    }
}