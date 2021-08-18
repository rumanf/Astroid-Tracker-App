package com.udacity.asteroidradar.api


import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.DatabaseAsteroid


//fun List<DatabaseAsteroid>.asDomainModel(): List<Asteroid> {
//    return map {
//        Asteroid(
//            id=it.id,
//            codename=it.codename,
//            closeApproachDate=it.closeApproachDate,
//            absoluteMagnitude=it.absoluteMagnitude,
//            estimatedDiameter=it.estimatedDiameter,
//            relativeVelocity=it. relativeVelocity,
//            distanceFromEarth=it.distanceFromEarth,
//            isPotentiallyHazardous=it.isPotentiallyHazardous)
//    }
//}
/**
 * Convert Network results to database objects
 */
fun List<Asteroid>.asDatabaseModel(): Array<DatabaseAsteroid> {
    return map {
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


