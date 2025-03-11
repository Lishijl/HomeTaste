package com.example.hometaste.data

import com.example.hometaste.recipies.Recipe
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

interface RecipeService {
    @GET("recipes/get")
    suspend fun recipeList():List<Recipe>

    @GET("recipes/get/{id}")
    suspend fun recipesByID(@Path("id") idRecipe: Int): List<Recipe>

    @POST("recipes/crea")
    suspend fun insertRecipe(@Body recipe:Recipe): Response<Void>

    @DELETE("recipes/delete/{id}")
    suspend fun deleteRecipe(@Path("id") idRecipe: Int): Response<Void>

    @PUT("recipes/update/{id}")
    suspend fun updateRecipe(@Path("id") idRecipe: Int, @Body recipe: Recipe): Response<Void>
}

class RecipeAPI {
    companion object {
        private var mAPI: RecipeService? = null

        //L'anotador per a que sigui thread-safe
        //https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.jvm/-synchronized/
        @Synchronized
        fun API(): RecipeService {
            if (mAPI == null) {
                val client: OkHttpClient = getUnsafeOkHttpClient()
                mAPI = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://54.197.191.50/")
                    .client(getUnsafeOkHttpClient())
                    .build()
                    .create(RecipeService::class.java)
            }
            return mAPI!!
        }
    }
}

private fun getUnsafeOkHttpClient(): OkHttpClient {
    try {
        // Create a trust manager that does not validate certificate chains
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        } )

        // Install the all-trusting trust manager
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())
        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory

        val builder = OkHttpClient.Builder()
        builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
        builder.hostnameVerifier { hostname, session -> true }

        val okHttpClient = builder.build()
        return okHttpClient
    } catch (e: Exception) {
        throw RuntimeException(e)
    }
}