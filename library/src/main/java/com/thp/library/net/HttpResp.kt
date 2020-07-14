package com.thp.library.net

/**
 * @author thp
 * time 2020/4/24
 * desc:
 */
data class HttpResp<T>(
    var data: T?,
    var errorCode: Int = -1,
    var errorMessage: String = ""
)

