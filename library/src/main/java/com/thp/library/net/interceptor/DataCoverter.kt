package com.thp.library.net.interceptor

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.thp.library.net.HttpException
import com.thp.library.net.HttpResp
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import okio.Buffer
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.reflect.Type
import java.nio.charset.Charset


/**
 * @author thp
 * time 2020/4/15
 * desc:将服务器的数据做统一的处理
 */
class DataCoverter private constructor(private val gson: Gson) : Converter.Factory() {
    companion object {
        fun create(gson: Gson = Gson()): DataCoverter {
            return DataCoverter(gson)
        }
    }

    /**
     * 上传得数据
     */
    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        return DataRequestBodyConverter(gson, gson.getAdapter(TypeToken.get(type)))
    }

    /**
     * 服务器返回数据
     */
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        return DataResponseBodyConverter(gson, gson.getAdapter(TypeToken.get(type)))
    }

    class DataRequestBodyConverter<T>(
        private val gson: Gson,
        private val adapter: TypeAdapter<T>
    ) :
        Converter<T, RequestBody> {
        @Throws(IOException::class)
        override fun convert(value: T): RequestBody? {
            val buffer = Buffer()
            val writer = OutputStreamWriter(buffer.outputStream(), UTF_8)
            val jsonWriter = gson.newJsonWriter(writer)
            adapter.write(jsonWriter, value)
            jsonWriter.close()
            return buffer.readByteString().toRequestBody(MEDIA_TYPE)
        }

        companion object {
            private val MEDIA_TYPE = "application/json; charset=UTF-8".toMediaType()
            private val UTF_8 = Charset.forName("UTF-8")
        }
    }

    class DataResponseBodyConverter<T>(
        private val gson: Gson,
        private val adapter: TypeAdapter<T>
    ) : Converter<ResponseBody, T> {
        @Throws(IOException::class)
        override fun convert(value: ResponseBody): T? {
            var response = value.string().trimEnd()
            val httpResp = gson.fromJson(response, HttpResp::class.java)
            //errorCode = 0 代表执行成功，不建议依赖任何非0的 errorCode.
            //errorCode = -1001 代表登录失效，需要重新登录。
            var code = httpResp.errorCode
            when (code) {
                0 -> {
                    val contentType = value.contentType()
                    val charset =
                        if (contentType != null) contentType.charset(Charsets.UTF_8) else Charsets.UTF_8
                    val inputStream = ByteArrayInputStream(response.toByteArray())
                    val reader = InputStreamReader(inputStream, charset)
                    val jsonReader = gson.newJsonReader(reader)
                    //use函数会自动关闭调用者（无论中间是否出现异常）
                    value.use {
                        return adapter.read(jsonReader)
                    }
                }
                -1001 -> {
                    throw HttpException("登录失效,请重新登录", code)
                }
                else -> {
                    throw HttpException("业务处理失败,后台未返回具体原因!", code)
                }
            }


        }
    }
}