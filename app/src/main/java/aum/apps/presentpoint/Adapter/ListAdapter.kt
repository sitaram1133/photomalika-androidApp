package aum.apps.presentpoint.Adapter

import android.app.Dialog
import android.content.ContentValues
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import aum.apps.presentpoint.R
import aum.apps.presentpoint.model.DataModel
import com.bumptech.glide.Glide


class ListAdapter(
    private val list: List<DataModel>
) : RecyclerView.Adapter<MovieViewHolder>() {


    private val mOnNoteListener: MovieViewHolder.OnNoteListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie: DataModel = list[position]
        /*if (list[position]==null){

        }*/
        holder.bind(movie)
    }

    override fun getItemCount(): Int = list.size

}

class MovieViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item, parent, false)),
    View.OnClickListener {
    private var mCard : CardView? = null
    private var custName: TextView? = null
    private var mDesc: TextView? = null

    private var mtimenow: TextView? = null
    private var mImageview: ImageView? = null
    private var uid:String?=null
    private var isApproved: Boolean? = false
    private var isIntrested: Boolean? = false
    private var calldatalink: String? = null
    private var list_description: TextView? = null
    private var list_Address: TextView?=null
    private var mImgurl: String? = null
    private var phones: String? = null
    private var address: String? = null
    private var custEmployer: TextView? = null
    private var datelayout: LinearLayout? = null
    private var callnowbtn: TextView? = null
    private var employer: String?=null
    private var salary: String?=null
    private  var likebtn:Button? = null
    private  var commentbtn:Button? = null
    private  var sharebtn:Button? = null

    init {
        mCard = itemView.findViewById(R.id.cardview5)
        custName = itemView.findViewById(R.id.list_title)
        mImageview = itemView.findViewById(R.id.feedimgview)
        likebtn = itemView.findViewById(R.id.likebtnid)
        commentbtn = itemView.findViewById(R.id.commentsbtnid)
        sharebtn = itemView.findViewById(R.id.sharebtnid)

        likebtn?.setOnClickListener {
            
        }
        mCard?.setOnClickListener {
        v: View? ->
            Log.d("Stack","we are here")
                // val intent = Intent(v!!.getContext(), FeeddetailActivity::class.java)
         //   val context = v.getContext()
            showZoomableImage("http://192.168.43.49:8080/${mImgurl}")
            var name = custName?.text
            Log.d("p1",uid.toString())
         //   intent.putExtra("feedId",uid)
         //   context.startActivity(intent)
        }
        callnowbtn?.setOnClickListener(this)

    }

    fun bind(customer: DataModel) {
        uid = customer._id.toString()
        Log.i("Feed id :",uid.toString())
        custName?.text = customer.originalname
        //mDesc?.text = customer.postDescription

        // Get length of file in bytes
        var fileSizeInBytes: Long = customer.size.toLong()
        Log.d("Size in",fileSizeInBytes.toString())
        // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
        var fileSizeInKB = fileSizeInBytes / 1024
        // Convert the KB to MegaBytes (1 MB = 1024 KBytes)
        var fileSizeInMB = fileSizeInKB / 1024

        mImgurl = customer.path
        Log.d("Image Url",mImgurl.toString())
        likebtn?.text = fileSizeInMB.toString() + "Mb"
        val datetimes = customer.Category
        if (datetimes != null){
           // mtimenow?.text = convertToCustomFormat(datetimes)
        }else{
            mtimenow?.visibility = View.INVISIBLE
        }
        if (isApproved == true){
            mCard!!.setBackgroundResource(R.drawable.calldonecard)
            //mPenddingStatus?.visibility = View.VISIBLE
            //mPenddingStatus?.text = "Date Over"
        }else{
            mCard!!.setBackgroundResource(R.drawable.cardviewbackground)
        }


        mImageview?.let {
            Glide
                .with(itemView)
                .load("http://192.168.151.50:8080/${mImgurl}")
                //.load(mImgurl)
                .centerCrop()
                .placeholder(R.drawable.cardshape)
                .into(it)
        }


    }






//    private fun convertToCustomFormat(dateStr: String?): String {
//        val utc = TimeZone.getTimeZone("UTC")
//        val sourceFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy")
//        val destFormat = SimpleDateFormat("dd-MMM-YYYY HH:mm aa")
//        sourceFormat.timeZone = utc
//        val convertedDate = sourceFormat.parse(dateStr)
//        return destFormat.format(convertedDate)
//    }



    override fun onClick(v: View?) {
        Log.d(ContentValues.TAG, "onCreate() Restoring previous state")
        Log.d(ContentValues.TAG, "onClick: " + getAdapterPosition())

       // val intent = Intent(v!!.getContext(), FeeddetailActivity::class.java)
       // val context = v.getContext()
        var name = custName?.text
        Log.d("p1",phones.toString())
        var phone = phones
        var address = list_description?.text

       // intent.putExtra("id",uid)
       // intent.putExtra("name",name)
      //  intent.putExtra("phone",phone)
      //  intent.putExtra("isCallDone",isCallDone)

       // context.startActivity(intent)
    }

     fun showZoomableImage(fileUrl:String?){
         val d = Dialog(itemView.context, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
         d.setCancelable(true)

         val wv = WebView(itemView.context)
         wv.loadUrl(fileUrl!!)
         wv.settings.supportZoom()
         wv.getSettings().setSupportZoom(true)
         wv.getSettings().setBuiltInZoomControls(true);
         wv.settings.builtInZoomControls
         wv.webViewClient = object : WebViewClient() {
             override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                 view.loadUrl(url)
                 return true
             }
         }
         //d.setView(wv)
         d.setContentView(wv);
         d.show();
     }


    interface OnNoteListener {
        fun onNoteClick(position: Int)

    }

    fun getDominantColor(bitmap: Bitmap?): Int {
        val newBitmap = Bitmap.createScaledBitmap(bitmap!!, 1, 1, true)
        val color = newBitmap.getPixel(0, 0)
        newBitmap.recycle()
        return color
    }

}