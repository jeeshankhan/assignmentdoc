package com.video.docquityandroidassignment.ui.utils

object Prefs {

//
//    private val GSON = Gson()
//    val prefs: SharedPreferences = LoanApplication.getINSTANCE().getSharedPreferences(
//        PREFS_FILENAME, 0)!!
//
//
//    fun clear(){
//        prefs.edit().clear().apply()
//    }
//    fun saveLogin(resp: User){
//        putObject(LOGIN_KEY,resp)
//    }
//    fun getLogin() = getObject(LOGIN_KEY,User::class.java)
//
//    var mobileNumber: String?
//        get() = prefs.getString("mobile","")
//        set(value) = prefs.edit().putString("mobile", value).apply()
//
//
//
//    private fun putObject(key: String?, `object`: Any?) {
//        requireNotNull(`object`) { "Object is null" }
//        require(!(key == null || key == "")) { "Key is empty or null" }
//        val editor: SharedPreferences.Editor = prefs.edit()
//        editor.putString(
//            key,
//            GSON.toJson(`object`)
//        )
//        editor.apply()
//    }
//
//    fun <T> getObject(key: String, a: Class<T>?): T? {
//        val gson: String? = prefs.getString(key, null)
//        return if (gson == null) {
//            null
//        } else {
//            try {
//                GSON.fromJson(
//                    gson,
//                    a
//                )
//            } catch (e: Exception) {
//                throw IllegalArgumentException(
//                    "Object stored with key "
//                            + key + " is instance of other class"
//                )
//            }
//        }
//    }
}