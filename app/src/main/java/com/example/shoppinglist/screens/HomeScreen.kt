package com.example.shoppinglist.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import com.example.shoppinglist.components.ItemInput
import com.example.shoppinglist.components.SearchInput
import com.example.shoppinglist.components.ShoppingList
import com.example.shoppinglist.viewmodel.ShoppingViewModel

@Composable
fun HomeScreen(vm: ShoppingViewModel) {
    var newItemText by rememberSaveable { mutableStateOf("") }

    val listState: LazyListState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    val filteredItems by remember(vm.query, vm.items) {
        derivedStateOf {
            if (vm.query.isBlank()) vm.items
            else vm.items.filter { it.contains(vm.query, ignoreCase = true) }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        ItemInput(
            text = newItemText,
            onTextChange = { newItemText = it },
            onAddItem = {
                vm.addItem(newItemText)
                newItemText = ""
                scope.launch { listState.animateScrollToItem(0) }
            }
        )
        Spacer(Modifier.height(16.dp))
        SearchInput(query = vm.query, onQueryChange = vm::updateQuery)
        Spacer(Modifier.height(16.dp))
        ShoppingList(items = filteredItems, listState = listState)
    }
}
