package com.example.jewerlyproducts.domain

import com.example.jewerlyproducts.data.products.ProductRepository
import com.example.jewerlyproducts.data.relations.ProductMaterialsCrossRef
import com.example.jewerlyproducts.ui.dataclasses.MaterialInProductWithQuantityDataClass
import com.example.jewerlyproducts.ui.dataclasses.ProductsDataClass
import javax.inject.Inject

class UpdateProductUseCase @Inject constructor(private val repository: ProductRepository) {
    suspend operator fun invoke(product: ProductsDataClass, materialList: List<MaterialInProductWithQuantityDataClass>) {
        repository.updateProduct(product)
        repository.deleteProductMaterialCrossRef(product.productName)
        materialList.forEach { item ->
            repository.addProductMaterialCrossRef(
                ProductMaterialsCrossRef(
                    productName = product.productName,
                    materialName = item.material.materialName,
                    quantity = item.quantity
                )
            )
        }
    }
}