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
 * @param gpxData 
 * @param type 
 */
data class AddTrackRecordRequest (

    val gpxData: Gpx? = null,
    val type: TrackType? = null
) {
}