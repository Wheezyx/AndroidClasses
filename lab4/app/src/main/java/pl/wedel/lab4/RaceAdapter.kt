package pl.wedel.lab4

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class RaceAdapter(private val context: Context, private val races: Array<Race>) : BaseAdapter() {
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
        val race = races[position]
        viewHolder.lblImage?.setImageResource(race.image)
        viewHolder.lblName?.text = race.name
        viewHolder.lblDesignation?.text = race.description

        return view
    }

    override fun getItem(position: Int): Any {
        return races[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return races.size
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
