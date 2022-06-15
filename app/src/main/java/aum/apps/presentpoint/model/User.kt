package aum.apps.telecallers.Model

data class User  (
    var id: String? = null,
    var title: String? = null,
    var firstname: String? = null,
    var lastname: String? = null,
    var email: String? = null,
    var password: String? = null,
    var password2: String? = null,
    val imgurl: String? = null,
    var address: String? = null,
    var fulladd: String? = null,
    var receiver:String? = null,
    var receiverphone: String? = null,
    var landmark:String? = null,
    var total: Int = 0,
    var account : String? = null,
    var count:Int = 0,
    var paytype: String? = null,
    var isAuth: Boolean = false,
    var message: String?=null,
    var name: String? = null,
    var succes:Boolean? = false,
    var user:String? = null,
    var phone: String? = null)