package com.udacity.asteroidradar.api

import com.squareup.moshi.JsonClass
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.DatabaseAsteroid


/**
 * DataTransferObjects go in this file. These are responsible for parsing responses from the server
 * or formatting objects to send to the server. You should convert these to domain objects before
 * using them.
 */

/**
 * VideoHolder holds a list of Videos.
 *
 * This is to parse first level of our network result which looks like
 *
 * {
 *   "videos": []
 * }
 */
@JsonClass(generateAdapter = true)
data class NetworkAsteroidContainer(val asteroids: List<NetworkAsteroid>)

/**
 * Videos represent a devbyte that can be played.
 */
@JsonClass(generateAdapter = true)
data class NetworkAsteroid(
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
    )


fun NetworkAsteroidContainer.asDomainModel(): List<Asteroid> {
    return asteroids.map {
        Asteroid(
            id=it.id,
            codename=it.codename,
            closeApproachDate=it.closeApproachDate,
            absoluteMagnitude=it.absoluteMagnitude,
            estimatedDiameter=it.estimatedDiameter,
            relativeVelocity=it. relativeVelocity,
            distanceFromEarth=it.distanceFromEarth,
            isPotentiallyHazardous=it.isPotentiallyHazardous)
    }
}
/**
 * Convert Network results to database objects
 */
fun NetworkAsteroidContainer.asDatabaseModel(): Array<DatabaseAsteroid> {
    return asteroids.map {
        DatabaseAsteroid(
            id=it.id,
            codename=it.codename,
            closeApproachDate=it.closeApproachDate,
            absoluteMagnitude=it.absoluteMagnitude,
            estimatedDiameter=it.estimatedDiameter,
            relativeVelocity=it. relativeVelocity,
            distanceFromEarth=it.distanceFromEarth,
            isPotentiallyHazardous=it.isPotentiallyHazardous)
    }.toTypedArray()
}

//@JsonClass(generateAdapter = true)
//data class NetworkPictureOfDay(
//    val mediaType: String,
//    val title: String,
//    val url: String
//)


