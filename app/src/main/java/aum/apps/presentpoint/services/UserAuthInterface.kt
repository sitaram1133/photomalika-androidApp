package aum.apps.presentpoint.services

import aum.apps.telecallers.Model.User
import retrofit2.Call
import retrofit2.http.*

interface UserAuthInterface {
    @POST("api/login")
    fun LoginUser(@Body newDestination: User): Call<User>

    @POST("api/register")
    fun SignupUser(@Body newDestination: User): Call<User>

    @GET("api/logout/{id}")
    fun LogoutUser(@QueryMap filter: HashMap<String, String>): Call<List<User>>

    @GET("api/profile/{userId}")
    fun UserProfile(@Path("userId") id: String?, @QueryMap filter: HashMap<String, String>): Call<User>

}
