package com.example.shoppinglist.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ShoppingViewModel : ViewModel() {
    val items = mutableStateListOf<String>()

    var query by mutableStateOf("")
        private set

    fun addItem(name: String) {
        if (name.isNotBlank()) items.add(0, name.trim())
    }
    fun updateQuery(q: String) { query = q }
    fun clearAll() { items.clear() }
}
