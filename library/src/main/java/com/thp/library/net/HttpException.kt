package com.thp.library.net


/**
 * @author thp
 * time 2020/4/24
 * desc:
 */
class HttpException(msg: String, val code: Int) : Throwable(msg, null) {
}