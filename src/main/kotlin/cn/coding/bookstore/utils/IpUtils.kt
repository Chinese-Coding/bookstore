package cn.coding.bookstore.utils

import jakarta.servlet.http.HttpServletRequest

val headers =
    arrayOf(
        "X-Forwarded-For",
        "Proxy-Client-IP",
        "WL-Proxy-Client-IP",
        "X-Real-IP"
    )

/**
 * 获取 IP
 * */
fun getIpAddr(request: HttpServletRequest): String {
    var ip: String? = ""
    for (header in headers) {
        ip = request.getHeader(header)
        if (!(ip.isNullOrEmpty() || "unknown" == ip))
            break
    }
    if (ip == null)
        ip = request.remoteAddr
    return if ("0:0:0:0:0:0:0:1" == ip) "127.0.0.1" else ip!!
}