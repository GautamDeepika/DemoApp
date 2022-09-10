package com.example.myapplication.utils

import android.content.Context
import android.os.Message
import android.widget.Toast
import java.lang.Error

class ToastUtils private constructor() {
    init {
        throw Error("You will not able to instantiate it")
    }

    companion object {
        lateinit var mToast: Toast

        /**
         * @param context
         * @param message
         * @return
         */
        fun showToastShort(context: Context?, message: String?) {
            if (!message.isNullOrEmpty() && context != null)
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        /**
         * @param context
         * @param message
         * @return
         */
        @JvmStatic
        fun showToastLong(context: Context?, message: String?) {
            if (!message.isNullOrEmpty() && context != null)
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }

        /**
         * @param context
         * @param message
         * @return
         */
        fun showCancelableToastShort(context: Context?, message: String?) {
            if (!message.isNullOrEmpty() && context != null) {
                mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
                mToast.show()
            }
        }

        /**
         * @param context
         * @param message
         * @return
         */
        fun showCancelableToastLong(context: Context?, message: String?) {
            if (!message.isNullOrEmpty() && context != null) {
                mToast = Toast.makeText(context, message, Toast.LENGTH_LONG)
                mToast.show()
            }
        }

        /**
         * @param context
         * @param message
         * @return
         */
        fun showCancelableToastLong(context: Context?, message: Int) {
            if (context != null && message > 0) {
                mToast = Toast.makeText(context, context.getString(message), Toast.LENGTH_LONG)
                mToast.show()
            }
        }

        /**
         * call this CancelToast, when your Activity or Fragment Destroy
         */
        fun cancelToast() {
            if (::mToast.isInitialized) {
                mToast.cancel()
            }
        }

        /**
         * @param context
         * @param messageId
         * @return
         */
        fun showToastShort(context: Context?, messageId: Int) {
            val message = context?.resources?.getString(messageId)
            if (!message.isNullOrEmpty())
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        /**
         * @param context
         * @param messageId
         * @return
         */
        fun showToastLong(context: Context?, messageId: Int) {
            val message = context?.resources?.getString(messageId)
            if (!message.isNullOrEmpty())
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }


    }
}