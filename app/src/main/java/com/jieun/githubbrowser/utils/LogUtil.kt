package com.jieun.githubbrowser.utils

import android.util.Log

import java.io.*
import java.text.MessageFormat
import com.jieun.githubbrowser.BuildConfig
import java.util.*

/**
 * date 2021-11-13
 * create by jieun
 */
object LogUtil {
    private const val TAG = "LogUtil"
    private val isLog = BuildConfig.DEBUG

    fun v(vararg text: Any?) {
        doLog(Log.VERBOSE, *text)
    }

    fun d(vararg text: Any?) {
        doLog(Log.DEBUG, *text)
    }

    fun i(vararg text: Any?) {
        doLog(Log.INFO, *text)
    }

    fun w(vararg text: Any?) {
        doLog(Log.WARN, *text)
    }

    fun e(vararg text: Any?) {
        doLog(Log.ERROR, *text)
    }

    private fun doLog(logLevel: Int, vararg logText: Any?) {


        val stb = StringBuilder()
        for (i in logText.indices) {
            stb.append(logText[i])
            stb.append(", \t")
        }

        var log = stb.toString()
        val stackTrace = Thread.currentThread().stackTrace
        if (stackTrace.size > 4) {
            val element = stackTrace[4]
            val fullClassName = element.className
            val simpleClassName = fullClassName.substring(fullClassName.lastIndexOf(".") + 1)

            log = MessageFormat.format(
                "{0} {1}() {2}|" + "\n{3}\n",
                simpleClassName,
                element.methodName,
                element.lineNumber,
                stb.toString()
            )
        }
        if (isLog) {
            Log.println(logLevel, "Github Browser", log)
        }
    }

}