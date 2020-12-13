package com.kakaot.pocketd.common

import android.util.Log
import com.kakaot.pocketd.BuildConfig

class Logger {
    companion object {
        private const val TAG_PREFIX = "[PocketD]"
        fun d(tag: String, message: String) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG_PREFIX + tag, message);
            }
        }
        fun e(tag: String, message: String) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG_PREFIX + tag, message);
            }
        }
    }
}