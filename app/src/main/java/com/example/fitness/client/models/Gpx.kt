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
 * @param id 
 * @param points 
 * @param creator 
 * @param version 
 * @param minLon 
 * @param minLat 
 * @param maxLon 
 * @param maxLat 
 * @param allTime 
 * @param distance2D 
 * @param distance3D 
 * @param maxSpeed 
 * @param avgSpeed 
 * @param _limb 
 * @param maxAlt 
 */
data class Gpx (

    val id: kotlin.Int? = null,
    val points: kotlin.Array<Point>? = null,
    val creator: kotlin.String? = null,
    val version: kotlin.String? = null,
    val minLon: kotlin.Float? = null,
    val minLat: kotlin.Float? = null,
    val maxLon: kotlin.Float? = null,
    val maxLat: kotlin.Float? = null,
    val allTime: TimeSpan? = null,
    val distance2D: kotlin.Float? = null,
    val distance3D: kotlin.Float? = null,
    val maxSpeed: kotlin.Float? = null,
    val avgSpeed: kotlin.Float? = null,
    val _limb: kotlin.Float? = null,
    val maxAlt: kotlin.Float? = null
) {
}