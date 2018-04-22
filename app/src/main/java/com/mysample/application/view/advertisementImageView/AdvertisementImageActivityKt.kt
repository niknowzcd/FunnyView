package com.mysample.application.view.advertisementImageView

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.mysample.application.R

/**
 * Created by a01 on 2018/4/11.
 */
class AdvertisementImageActivityKt : AppCompatActivity() {

    val list by lazy { findViewById(R.id.rv_content) as RecyclerView }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_window)

        list.layoutManager = LinearLayoutManager(this)
        list.adapter = MyAdapter()
    }


    inner class MyAdapter : RecyclerView.Adapter<MyAdapter.MyHolder>() {

        override fun onBindViewHolder(holder: MyHolder, position: Int) {
            if (position == 10) {
                holder.windowImageView!!.bindRecyclerView(list)
                holder.windowImageView!!.setImageResourceId(R.drawable.timg)
            } else {
                holder.itemView.setBackgroundColor(Color.rgb((Math.random() * 255).toInt(), (Math.random() * 255).toInt(), (Math.random() * 255).toInt()))
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyHolder {
            val view: View
            if (viewType == LIST_TYPE_AD) {
                view = View.inflate(this@AdvertisementImageActivityKt, R.layout.item1, null)
            } else {
                view = View.inflate(this@AdvertisementImageActivityKt, R.layout.item0, null)
                val lp = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                view.layoutParams = lp
            }
            return MyHolder(view)
        }

        override fun getItemViewType(position: Int): Int {
            return if (position == 10) {
                LIST_TYPE_AD
            } else LIST_TYPE_NORMAL
        }

        override fun getItemCount(): Int = 20

        inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var windowImageView: AdvertisementImageView2Kt? = null

            init {
                windowImageView = itemView.findViewById(R.id.wiv)
            }
        }
    }

    companion object {
        val LIST_TYPE_AD = 0x11
        val LIST_TYPE_NORMAL = LIST_TYPE_AD + 1
    }
}