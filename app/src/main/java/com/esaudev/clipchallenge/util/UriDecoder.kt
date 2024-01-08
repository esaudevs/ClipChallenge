package com.esaudev.househealth.util

import android.net.Uri
import com.esaudev.clipchallenge.util.StringDecoder
import javax.inject.Inject

class UriDecoder @Inject constructor() : StringDecoder {
    override fun decodeNullableString(encodedString: String?): String? = encodedString.let {
        Uri.decode(
            encodedString
        )
    } ?: null
    override fun decodeString(encodedString: String): String = Uri.decode(encodedString)
}