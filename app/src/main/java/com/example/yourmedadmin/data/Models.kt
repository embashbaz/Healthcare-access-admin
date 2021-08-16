package com.example.yourmedadmin.data


data class CareAdmin(
  var uId: String? = "",
  var name: String? = "",
  var phone: Long = 0L,
  var email: String = "",
  var license: String ="",
  var document: String = "",
  var country: String ="",
  var town: String = "",
  var adress: String = "",
  var serviceOffered: String = "",
  var details: String ="",

)

data class Service(
    var uId: String? = "",
    var name: String? = "",
    var identifier : String? = "",
    var details: String ="",
    var price: Double =0.0,
    var priceDescription: String = "",
    var availability: String = ""
)

data class Medicine(
   var scientificName: String? = "",
   var genericName: String = "",
   var countryMade: String = "",
   var currency: String = "",
   var detailsMed: String = "",
   var availability: String = "",
   var medUid: String = "",
   var price: Double = 0.0

)

