package aum.apps.presentpoint


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import aum.apps.presentpoint.Adapter.ListAdapter
import aum.apps.presentpoint.Adapter.MyCustomPagerAdapter
import aum.apps.presentpoint.Dialogs.ViewserverDialog
import aum.apps.presentpoint.model.DataModel
import aum.apps.presentpoint.services.DestinationService
import aum.apps.presentpoint.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private val sharedPrefFile = "userAuthentication"
    var viewPager: ViewPager? = null
    var images =
        intArrayOf(R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4)
    var myCustomPagerAdapter: MyCustomPagerAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val actionBar: ActionBar? = supportActionBar
        actionBar!!.hide()

        val sharedPreferences: SharedPreferences = this.getSharedPreferences(
            sharedPrefFile,
            Context.MODE_PRIVATE
        )

        val userId = sharedPreferences.getString("user_id","nc")
        loadDestinations(userId)
    }

    private fun loadDestinations(userId: String?): Boolean {
        val destinationService = ServiceBuilder.buildService(DestinationService::class.java)
        val filter = HashMap<String, String>()

        val requestCall = destinationService.getAllList(filter)
        requestCall.enqueue(object : Callback<List<DataModel>> {
            // If you receive a HTTP Response, then this method is executed
            // Your STATUS Code will decide if your Http Response is a Success or Error
            override fun onResponse(
                call: Call<List<DataModel>>,
                response: Response<List<DataModel>>
            ) {
                if (response.isSuccessful) {
                    // Your status code is in the range of 200's
                    var index = 0
                    val destinationList = response.body()!!

                    Log.d("total size", "${destinationList.size}")
                    var totalpay = 0
                    var totalpaid = 0
                    var totalpeding = 0
                    var count = 0
                    //totalpay = destinationList.size
                    while (count < destinationList.size) {
                        count++
                        //totalpay += destinationList[index].payment
                        //totalpaid += destinationList[index].advance
                        //totalpeding += destinationList[index].pending
                        if (count == destinationList.size) {
                            /* val typeface = Typeface.createFromAsset(context!!.assets,
                                                      R.font.rupee.toString())
                                                  paymentid.typeface = typeface*/
                            //gt print to text
                            val COUNTRY = "IN"
                            val LANGUAGE = "en"
                            val pay: String =
                                NumberFormat.getCurrencyInstance(Locale(LANGUAGE, COUNTRY))
                                    .format(totalpay)
                            val paid: String =
                                NumberFormat.getCurrencyInstance(Locale(LANGUAGE, COUNTRY))
                                    .format(totalpaid)

                            val calpending = totalpay - totalpaid
                            val pending: String =
                                NumberFormat.getCurrencyInstance(Locale(LANGUAGE, COUNTRY))
                                    .format(calpending)

                        }else{
                            index++
                        }
                        val linearLayoutManager = LinearLayoutManager(this@MainActivity)
                        linearLayoutManager.reverseLayout = true
                        linearLayoutManager.stackFromEnd = true
                        list_recycler_view.setLayoutManager(linearLayoutManager)
                        //list_recycler_view.adapter = destinationList
                        val adapter = ListAdapter(destinationList)
                        list_recycler_view.adapter = adapter
                        // destiny_recycler_view.adapter = DestinationAdapter(destinationList)


                        // destiny_recycler_view.adapter = DestinationAdapter(destinationList)
                    }

                    //list_recycler_view.adapter = destinationList
                    // adapter = GymAdapter(destinationList)
                    // can_recycler_view.adapter = adapter
                    // destiny_recycler_view.adapter = DestinationAdapter(destinationList)
                } else if (response.code() == 401) {
                    Log.d("fail", "Application Level failure")
                    // Toast.makeText(this@HomeFragment,
                    //  "Your session has expired. Please Login again.", Toast.LENGTH_LONG).show()
                } else { // Application-level failure
                    // Your status code is in the range of 300's, 400's and 500's
                    // Toast.makeText(this@HomeFragment, "Failed to retrieve items", Toast.LENGTH_LONG).show()

                }
            }

            // Invoked in case of Network Error or Establishing connection with Server
            // or Error Creating Http Request or Error Processing Http Response
            override fun onFailure(call: Call<List<DataModel>>, t: Throwable) {
                val samdialog = ViewserverDialog().showMessageDialog(this@MainActivity,"404 failure")
            }

        })
        return true
    }
}