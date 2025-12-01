package com.travelgo.app.presentation.destinationlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Modelo simple de destino / lugar para reservar.
 * Ajusta los campos según tu API real.
 */
data class Destination(
    val id: Int,
    val name: String,
    val city: String,
    val country: String,
    val category: String,
    val pricePerNight: Double,
    val imageUrl: String? = null,
    val weatherSummary: String? = null
)

enum class DestinationCategoryFilter {
    ALL, BEACH, CITY, MOUNTAIN
}

data class DestinationListUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val searchQuery: String = "",
    val selectedCategory: DestinationCategoryFilter = DestinationCategoryFilter.ALL,
    val destinations: List<Destination> = emptyList()
) {
    val isEmpty: Boolean get() = !isLoading && destinations.isEmpty() && !isError
}

class DestinationListViewModel : ViewModel() {

    var uiState by mutableStateOf(DestinationListUiState())
        private set

    init {
        loadDestinations()
    }

    fun onSearchQueryChange(query: String) {
        uiState = uiState.copy(searchQuery = query)
        applyFilters()
    }

    fun onCategorySelected(category: DestinationCategoryFilter) {
        uiState = uiState.copy(selectedCategory = category)
        applyFilters()
    }

    /**
     * Simula una carga desde API/microservicio.
     * Reemplaza este método con tu repositorio real.
     */
    fun loadDestinations() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, isError = false, errorMessage = "")
            try {
                delay(500) // Simula carga
                val all = fakeDestinations()
                uiState = uiState.copy(
                    isLoading = false,
                    destinations = all
                )
                applyFilters()
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    isError = true,
                    errorMessage = e.message ?: "Error desconocido"
                )
            }
        }
    }

    private var originalList: List<Destination> = emptyList()

    private fun fakeDestinations(): List<Destination> {
        // Aquí puedes mapear datos reales de tu API/microservicio
        originalList = listOf(
            Destination(
                id = 1,
                name = "Hotel Playa Azul",
                city = "Cancún",
                country = "México",
                category = "BEACH",
                pricePerNight = 120.0,
                weatherSummary = "Soleado 29°"
            ),
            Destination(
                id = 2,
                name = "Depto Centro",
                city = "Santiago",
                country = "Chile",
                category = "CITY",
                pricePerNight = 55.0,
                weatherSummary = "Parcialmente nublado 22°"
            ),
            Destination(
                id = 3,
                name = "Cabaña Bosque",
                city = "Pucón",
                country = "Chile",
                category = "MOUNTAIN",
                pricePerNight = 80.0,
                weatherSummary = "Lluvioso 15°"
            )
        )
        return originalList
    }

    private fun applyFilters() {
        val base = originalList.ifEmpty { uiState.destinations }
        val byCategory = when (uiState.selectedCategory) {
            DestinationCategoryFilter.ALL -> base
            DestinationCategoryFilter.BEACH -> base.filter { it.category == "BEACH" }
            DestinationCategoryFilter.CITY -> base.filter { it.category == "CITY" }
            DestinationCategoryFilter.MOUNTAIN -> base.filter { it.category == "MOUNTAIN" }
        }

        val bySearch = if (uiState.searchQuery.isBlank()) {
            byCategory
        } else {
            byCategory.filter {
                it.name.contains(uiState.searchQuery, ignoreCase = true) ||
                        it.city.contains(uiState.searchQuery, ignoreCase = true) ||
                        it.country.contains(uiState.searchQuery, ignoreCase = true)
            }
        }

        uiState = uiState.copy(destinations = bySearch)
    }
}

@Composable
fun DestinationListScreen(
    viewModel: DestinationListViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onDestinationClick: (Destination) -> Unit = {}
) {
    val state = viewModel.uiState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Explora destinos",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = viewModel::onSearchQueryChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Buscar por nombre, ciudad o país") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        CategoryFilterRow(
            selected = state.selectedCategory,
            onSelected = viewModel::onCategorySelected
        )

        Spacer(modifier = Modifier.height(8.dp))

        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.isError -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = state.errorMessage.ifBlank { "Ocurrió un error al cargar los destinos." }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { viewModel.loadDestinations() }) {
                            Text("Reintentar")
                        }
                    }
                }
            }

            state.isEmpty -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No se encontraron destinos para los filtros aplicados.")
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.destinations) { destination ->
                        DestinationItem(
                            destination = destination,
                            onClick = { onDestinationClick(destination) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryFilterRow(
    selected: DestinationCategoryFilter,
    onSelected: (DestinationCategoryFilter) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterChipCompact(
            text = "Todos",
            selected = selected == DestinationCategoryFilter.ALL,
            onClick = { onSelected(DestinationCategoryFilter.ALL) }
        )
        FilterChipCompact(
            text = "Playa",
            selected = selected == DestinationCategoryFilter.BEACH,
            onClick = { onSelected(DestinationCategoryFilter.BEACH) }
        )
        FilterChipCompact(
            text = "Ciudad",
            selected = selected == DestinationCategoryFilter.CITY,
            onClick = { onSelected(DestinationCategoryFilter.CITY) }
        )
        FilterChipCompact(
            text = "Montaña",
            selected = selected == DestinationCategoryFilter.MOUNTAIN,
            onClick = { onSelected(DestinationCategoryFilter.MOUNTAIN) }
        )
    }
}

@Composable
private fun FilterChipCompact(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    AssistChip(
        onClick = onClick,
        label = { Text(text) },
        trailingIcon = if (selected) {
            { Icon(Icons.Default.Check, contentDescription = null) }
        } else null
    )
}

@Composable
private fun DestinationItem(
    destination: Destination,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = destination.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "${'$'}{destination.city}, ${'$'}{destination.country}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Categoría: ${'$'}{destination.category}",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Desde ${'$'}{'%'.format(2, destination.pricePerNight)} / noche",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.SemiBold
            )
            destination.weatherSummary?.let {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Clima: ${'$'}it",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
