package com.esaudev.clipchallenge.util

interface StringDecoder {
    fun decodeString(encodedString: String): String
    fun decodeNullableString(encodedString: String?): String?
}