/**
 * Tracker API
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: v1
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */
package com.example.fitness.client.models


/**
 * 
 * @param accessToken 
 * @param refreshToken 
 * @param refreshTokenExpiration 
 */
data class AuthResponse (

    val accessToken: kotlin.String? = null,
    val refreshToken: kotlin.String? = null,
    val refreshTokenExpiration: String? = null
) {
}