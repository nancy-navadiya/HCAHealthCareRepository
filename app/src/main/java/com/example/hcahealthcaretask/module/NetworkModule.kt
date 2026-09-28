package com.example.hcahealthcaretask.module

import com.example.hcahealthcaretask.service.GitHubApiService
import com.example.hcahealthcaretask.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.nerdythings.okhttp.profiler.OkHttpProfilerInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
/** Provides the application-wide HTTP client, Retrofit instance, and GitHub API. */
object NetworkModule {

    /** Logs request and response bodies while debugging network calls. */
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    /** Creates the shared HTTP client with diagnostics and conservative timeouts. */
    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor (OkHttpProfilerInterceptor())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

    /** Configures Retrofit to use the GitHub base URL and Gson response conversion. */
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /** Builds the typed API implementation used by the repository layer. */
    @Provides
    @Singleton
    fun provideGitHubApiService(retrofit: Retrofit): GitHubApiService =
        retrofit.create(GitHubApiService::class.java)
}