package com.example.jewerlyproducts.ui.screens.materialslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jewerlyproducts.data.materials.MaterialsRepository
import com.example.jewerlyproducts.domain.GetMaterialsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MaterialsListViewModel @Inject constructor(
    private val repository: MaterialsRepository,
    getMaterialsUseCase: GetMaterialsUseCase
):ViewModel() {

//    private val _searchName = MutableStateFlow("")
//    val searchName:StateFlow<String> = _searchName
//
//

//    private val _materialsList = repository.getAllMaterials().stateIn(
//        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
//    )
//    val materialsList: StateFlow<List<MaterialsDataClass>> = _materialsList

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    private val _materialsList = _searchQuery
        .debounce(300)
        .flatMapLatest {query ->
            getMaterialsUseCase(query)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val materialsList = _materialsList

//    fun updateSearchName(newValue: String) {
//        _searchName.value = newValue
//    }

    fun updateQuery(newValue:String) {
        _searchQuery.value = newValue
    }

}