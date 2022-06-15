package aum.apps.presentpoint.services
import aum.apps.presentpoint.model.DataModel
import aum.apps.telecallers.Model.*
import retrofit2.Call
import retrofit2.http.*

interface DestinationService {

    @GET("/api/find/{feedId}")
    fun getDestinationList(@Path("feedId") id: String, @QueryMap filter: HashMap<String, String>): Call<DataModel>

    @GET("/api/listurl")
    fun getAllList(@QueryMap filter: HashMap<String, String>): Call<List<DataModel>>

    @GET("/api/findname/{name}")
    fun searchBynameList(@Path("name") name: String?,@QueryMap filter: HashMap<String, String>): Call<List<DataModel>>

    @GET("/api/findphone/{phone}")
    fun searchByphoneList(@QueryMap filter: HashMap<String, String>): Call<List<DataModel>>

    @GET("api/customer/sort/{quary}")
    fun getSortedcust(@Path("quary") quary: String?,@QueryMap filter: HashMap<String, String>): Call<List<DataModel>>

    @FormUrlEncoded
    @PUT("/api/updateBycall/{custId}")
    fun updateDestination(
        @Path("custId") id: String,
        @Field("callDate") callDate: String?,
        @Field("callDuration") callDuration: Int?,
        @Field("callstatus") callstatus: String?,
        @Field("calledby") calledby: String?,
        @Field("isCallDone") isCallDone: Boolean?,
        @Field("isIntrested") isIntrested: Boolean?,
        @Field("isApproved") isApproved: Boolean?,
        @Field("assignedto") assignedto: String?,
        @Field("calldatalink") calldatalink: String?,
    ): Call<DataModel>


    @DELETE("/api/delete/{custId}")
    fun deleteCustomer(@Path("custId") id: String): Call<Unit>


}