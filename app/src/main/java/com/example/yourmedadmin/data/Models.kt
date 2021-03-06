package com.example.yourmedadmin.data

import android.os.Parcelable
import com.google.firebase.firestore.GeoPoint
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class CareAdmin(
  var uId: String? = "",
  var name: String? = "",
  var phone: Long = 0L,
  var email: String = "",
  var license: String ="",
  var document: String = "",
  var country: String ="",
  var town: String = "",
 var coordinate:  @RawValue GeoPoint? = null,
  var serviceOffered: String = "",
  var details: String ="",

): Parcelable

data class LoginCareAdminObject(
    var status: String="",
    var statusValue: String="",
    var careAdmin: CareAdmin? = null
)

@Parcelize
data class Service(
    var serviceId: String = "",
    var serviceName: String? = "",
    var identifier : String? = "",
    var serviceDetails: String ="",
    var price: Double =0.0,
    var priceDescription: String = "",
    var availability: String = "",
    var provider: CareAdmin? = null

): Parcelable

@Parcelize
data class Medicine(
   var scientificName: String? = "",
   var genericName: String = "",
   var countryMade: String = "",
   var detailsMed: String = "",
   var availability: String = "",
   var medUid: String = "",
   var price: Double = 0.0,
   var provider: CareAdmin? = null
): Parcelable

data class PredictionAutoComplete(
    var primaryText: String = "",
    var secondaryText: String = ""
)

data class InDemand(
    var numberRequest: Int = 0,
    var docId: String = "",
    var serviceName: String = "",
    var countyName: String = "",
    var countryName: String = "",
    var status: Int = 1,
    var indemandDeviceId: MutableList<String> = mutableListOf(),
    var indemandContact :MutableList<String> = mutableListOf()
)

data class InDemandQueryResults(
    var status: String ="",
    var statusValue: String = "",
    var inDemadList: MutableList<InDemand> = mutableListOf()

)

