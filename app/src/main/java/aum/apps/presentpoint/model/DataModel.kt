package aum.apps.presentpoint.model

import aum.apps.telecallers.Model.User

data class DataModel(
    var _id: String? = null,
    var title: String? = null,
    var fieldname:String? = null,
    var originalname:String? = null,
    var encoding:String? = null,
    var mimetype:String? = null,
    var destination:String? = null,
    var filename:String? = null,
    var path:String? = null,
    var size: Int = 0,
    var content:String? = null,
    var author:String? = null,
    var Category: String? = null,
    var postHeader: String? = null,
    var postDescription: String? = null,
    val imgurl: String? = null,
    var providerContact: String? = null,
    var location: String? = null,
    var tags: List<User>? = null,
    var views: Int = 0,
    var likes: Int = 0,
    var issaved: Boolean = false,
    var is_read: Boolean = false,
    var createdAt: String? = null,
    var is_shared : String? = null,
    var count:Int = 0,
    var accessCode: String? = null,
    var paytype: String? = null,
    var phone: String? = null,
    var totalFollowup: Int? = 0
)
