package aum.apps.presentpoint.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.viewpager.widget.PagerAdapter
import aum.apps.presentpoint.R
import aum.apps.presentpoint.model.DataModel


class MyCustomPagerAdapter(context: Context, images:IntArray) : PagerAdapter() {
    var context: Context
    var images: IntArray
    var layoutInflater: LayoutInflater
    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView: View = layoutInflater.inflate(R.layout.imgdraw, container, false)
        val imageView: ImageView = itemView.findViewById(R.id.imageView) as ImageView
        imageView.setImageResource(images[position])
        container.addView(itemView)



        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }

    init {
        this.context = context
        this.images = images
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }
}