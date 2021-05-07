package com.github.vitormbgoncalves.todolist.oauth.client

import arrow.core.Either
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result

/**
 * Htpp client Authentication file
 *
 * @author Vitor Goncalves
 * @since 28.04.2021, qua, 18:23
 */

interface IOAuthClient {
    fun getClientCredentialEx(
        tokenEndpoint: String,
        clientId: String,
        clientSecret: String,
        scopes: List<String>
    ): Either<Int, TokenResponse>

    fun callApiEx(apiEndpoint: String, tokenType: String, token: String): Either<Int, String>

    fun getClientCredential(
        tokenEndpoint: String,
        clientId: String,
        clientSecret: String,
        username: String,
        password: String,
        scopes: List<String>

    ): TokenResponse?

    fun callApi(apiEndpoint: String, tokenType: String, token: String): String?
}

class OAuthClient : IOAuthClient {
    override fun callApi(apiEndpoint: String, tokenType: String, token: String): String? {
        val (_, _, result) = apiEndpoint.httpGet().header(Pair("Authorization", "$tokenType $token")).responseString()

        return when (result) {
            is Result.Success -> {
                result.value
            }
            is Result.Failure -> {
                return null
            }
        }
    }

    override fun getClientCredential(
        tokenEndpoint: String,
        clientId: String,
        clientSecret: String,
        username: String,
        password: String,
        scopes: List<String>
    ): TokenResponse? {
        val (_, _, result) = tokenEndpoint.httpPost(
            listOf(
                "username" to username,
                "password" to password,
                "scope" to scopes.joinToString(" "),
                "grant_type" to "password",
            )
        ).authenticate(clientId, clientSecret)
            .responseString()

        return when (result) {
            is Result.Success -> {
                val parser = Parser.default()
                val json = parser.parse(StringBuilder(result.value)) as JsonObject

                TokenResponse(
                    json["access_token"].toString(),
                    json["expires_in"].toString().toInt(),
                    json["token_type"].toString()
                )
            }
            is Result.Failure -> {
                null
            }
        }
    }

    override fun getClientCredentialEx(
        tokenEndpoint: String,
        clientId: String,
        clientSecret: String,
        scopes: List<String>
    ): Either<Int, TokenResponse> {
        val (_, _, result) = tokenEndpoint.httpPost(
            listOf(
                "grant_type" to "client_credentials",
                "scope" to scopes.joinToString(" ")
            )
        ).authenticate(clientId, clientSecret)
            .responseString()

        return when (result) {
            is Result.Success -> {
                val parser = Parser.default()
                val json = parser.parse(StringBuilder(result.value)) as JsonObject

                Either.Right(
                    com.github.vitormbgoncalves.todolist.oauth.client.TokenResponse(
                        json["access_token"].toString(),
                        json["expires_in"].toString().toInt(),
                        json["token_type"].toString()
                    )
                )
            }
            is Result.Failure -> {
                Either.Left(result.error.response.statusCode)
            }
        }
    }

    override fun callApiEx(apiEndpoint: String, tokenType: String, token: String): Either<Int, String> {
        val (_, _, result) = apiEndpoint.httpGet().header(Pair("Authorization", "$tokenType $token")).responseString()

        return when (result) {
            is Result.Success -> {
                Either.Right(result.value)
            }
            is Result.Failure -> {
                return Either.Left(result.error.response.statusCode)
            }
        }
    }
}

class TokenResponse(val token: String, val expiresIn: Int, val tokenType: String)