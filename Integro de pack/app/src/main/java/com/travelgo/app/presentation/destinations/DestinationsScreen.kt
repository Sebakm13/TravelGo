
package com.travelgo.app.presentation.destinations

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.travelgo.app.data.remote.dto.DestinationDto

@Composable
fun DestinationsScreen(vm: DestinationsViewModel) {

    val state by vm.state.collectAsState()

    Column(Modifier.fillMaxSize().padding(16.dp)) {

        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = { vm.search(it) },
            label = { Text("Buscar") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(10.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip("ALL", state.categoryFilter == "ALL") { vm.changeCategory("ALL") }
            FilterChip("BEACH", state.categoryFilter == "BEACH") { vm.changeCategory("BEACH") }
            FilterChip("CITY", state.categoryFilter == "CITY") { vm.changeCategory("CITY") }
            FilterChip("MOUNTAIN", state.categoryFilter == "MOUNTAIN") { vm.changeCategory("MOUNTAIN") }
        }

        Spacer(Modifier.height(10.dp))

        when {
            state.isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            state.isError -> Text("Error: " + state.errorMessage)
            state.destinations.isEmpty() -> Text("No hay destinos")
            else -> LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(state.destinations) { d ->
                    DestinationItem(d)
                }
            }
        }
    }
}

@Composable
fun FilterChip(text:String, selected:Boolean, onClick:()->Unit){
    AssistChip(onClick=onClick, label={ Text(text) },
        trailingIcon= if(selected){{ Icon(Icons.Default.Check, null)}} else null)
}

@Composable
fun DestinationItem(d: DestinationDto){
    Card(Modifier.fillMaxWidth().clickable{}){
        Column(Modifier.padding(12.dp)){
            Text(d.name, style=MaterialTheme.typography.titleMedium)
            Text("${d.city}, ${d.country}")
            Text("Desde $${d.pricePerNight}")
        }
    }
}
