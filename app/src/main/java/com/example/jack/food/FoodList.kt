package com.example.jack.food

import android.content.Context
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_food_list.*
import com.example.jack.food.MainActivity.Companion.KeepFooddata
import kotlinx.android.synthetic.main.row_main.view.*

class FoodList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_list)

        val messageAdapter = myCustomAdapter(this, KeepFooddata)

        listview.setAdapter(messageAdapter)








    }
    private class myCustomAdapter(var context: Context, var objects: MutableList<KeepFoodObject>): BaseAdapter() { //Context is the root obj. Referring to any obj in the layout activity_main.xml (ex.ListView: id:lvMain, TextView: id:tvMain)
        private val names = arrayListOf<KeepFoodObject>()
        override fun getCount(): Int {
            return objects.size
        }

        override fun getItem(position: Int): Any {
            return objects[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {

            //update data into viewHolder instead of layout directly, dont need to inflate layout everytime when called
            val greyColor = Color.parseColor("#E0E0E0")

            val view: View
            val course = objects[position]

            if (convertView == null) { //If we've already called inflater once, convertView will not be null. So we call layoutInflater only when the inflater is not yet called.
                val layoutInflater = LayoutInflater.from(viewGroup!!.context)
                view = layoutInflater.inflate(R.layout.row_main, viewGroup, false)
                val viewHolder = ViewHolder(view.fooddd, view.rest)
                view.tag = viewHolder
            } else {
                view = convertView
            }

            val viewHolder = view.tag as ViewHolder
            viewHolder.foodTextView.text = course.name
            viewHolder.restaurantTextView.text = course.restaurant

            return view
        }

        private class ViewHolder(val foodTextView: TextView, val restaurantTextView: TextView) {


        }
    }
}
