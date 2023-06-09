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
 * @param ticks 
 * @param days 
 * @param hours 
 * @param milliseconds 
 * @param microseconds 
 * @param nanoseconds 
 * @param minutes 
 * @param seconds 
 * @param totalDays 
 * @param totalHours 
 * @param totalMilliseconds 
 * @param totalMicroseconds 
 * @param totalNanoseconds 
 * @param totalMinutes 
 * @param totalSeconds 
 */
data class TimeSpan (

    val ticks: kotlin.Long? = null,
    val days: kotlin.Int? = null,
    val hours: kotlin.Int? = null,
    val milliseconds: kotlin.Int? = null,
    val microseconds: kotlin.Int? = null,
    val nanoseconds: kotlin.Int? = null,
    val minutes: kotlin.Int? = null,
    val seconds: kotlin.Int? = null,
    val totalDays: kotlin.Double? = null,
    val totalHours: kotlin.Double? = null,
    val totalMilliseconds: kotlin.Double? = null,
    val totalMicroseconds: kotlin.Double? = null,
    val totalNanoseconds: kotlin.Double? = null,
    val totalMinutes: kotlin.Double? = null,
    val totalSeconds: kotlin.Double? = null
) {
}