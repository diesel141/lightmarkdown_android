package jp.co.lightmarkdown_android.adapter

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cursoradapter.widget.CursorAdapter
import jp.co.lightmarkdown_android.EditActivity
import jp.co.lightmarkdown_android.LmListActivity
import jp.co.lightmarkdown_android.R
import jp.co.lightmarkdown_android.provider.LmDataBase
import jp.co.lightmarkdown_android.provider.LmDataBase.BaseColumns.Companion._ID
import jp.co.lightmarkdown_android.provider.LmDataBase.RawTextColumns.Companion.TITLE

/**
 * LightMarkdownのカスタムカーソルアダプター
 */
class LmCursorAdapter(private val context: Context, c: Cursor?, flags: Int) :
    CursorAdapter(context, c, flags), LmDataBase.BaseColumns, LmDataBase.RawTextColumns {

    internal class ViewHolder {
        var title: TextView? = null
        var delete: ImageView? = null
    }

    override fun newView(
        context: Context?,
        cursor: Cursor?,
        viewGroup: ViewGroup?
    ): View? {
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.item_markdown_cassette, null)
        ViewHolder().apply {
            title = view.findViewById(R.id.title)
            delete = view.findViewById(R.id.delete)
            view.tag = this
        }
        return view
    }

    override fun bindView(
        view: View,
        context: Context?,
        cursor: Cursor
    ) {
        val holder = view.tag as ViewHolder
        val id = cursor.getInt(cursor.getColumnIndex(_ID))
        holder.title?.text = cursor.getString(cursor.getColumnIndex(TITLE))

        holder.title?.setOnClickListener {
            val intent = Intent(context, EditActivity::class.java)
            intent.putExtra(EditActivity.INTENT_KEY_ID, id)
            context?.startActivity(intent)
        }

        holder.delete?.setOnClickListener {
            (context as LmListActivity).deleteTarget(id, cursor.count == 1)
        }
    }
}
