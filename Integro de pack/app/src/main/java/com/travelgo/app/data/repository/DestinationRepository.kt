
package com.travelgo.app.data.repository

import com.travelgo.app.data.remote.ApiService
import com.travelgo.app.data.remote.dto.*

class DestinationRepository(
    private val api: ApiService
) {

    suspend fun getDestinations(category: String? = null, search: String? = null): List<DestinationDto> {
        return api.getDestinations(category, search)
    }

    suspend fun getDestinationById(id: Long) = api.getDestinationById(id)

    suspend fun createDestination(req: DestinationCreateRequest) =
        api.createDestination(req)

    suspend fun updateDestination(id: Long, req: DestinationCreateRequest) =
        api.updateDestination(id, req)

    suspend fun deleteDestination(id: Long) = api.deleteDestination(id)
}
