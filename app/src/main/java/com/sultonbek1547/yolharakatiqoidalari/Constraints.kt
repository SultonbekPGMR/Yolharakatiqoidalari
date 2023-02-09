package com.sultonbek1547.yolharakatiqoidalari

object Constraints {

    val WHICH_TYPE_ARRAY = arrayOf(
        "Ogohlantiruvchi",
        "Imtiyozli",
        "Ta'qiqlovchi",
        "Buyuruvchi"
    )

    var USER_ADDED_STATE = false
    var USER_EDITED_STATE = false
    var TEMP_USER = MyModel("", "", 3, 0, "")
    var VIEW_PAGER_ITEM_POSITION = 0
}