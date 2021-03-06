package pl.wedel.lab4

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class SkillAdapter(private val context: Context, private val classes: ArrayList<Skill>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.single_enum_item, null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        val skill = classes[position]
        viewHolder.lblImage?.setImageBitmap(skill.image)
        viewHolder.lblName?.text = skill.name
        viewHolder.lblDesignation?.text = skill.description

        return view
    }

    override fun getItem(position: Int): Any {
        return classes[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return classes.size
    }

    private class ViewHolder(row: View?) {
        var lblImage: ImageView? = null
        var lblName: TextView? = null
        var lblDesignation: TextView? = null

        init {
            this.lblImage = row?.findViewById(R.id.img_item)
            this.lblName = row?.findViewById<TextView>(R.id.item_name)
            this.lblDesignation = row?.findViewById<TextView>(R.id.item_desc)
        }
    }
}