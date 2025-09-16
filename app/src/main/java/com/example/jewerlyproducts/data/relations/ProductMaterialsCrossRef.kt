package com.example.jewerlyproducts.data.relations

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Junction
import androidx.room.Relation
import com.example.jewerlyproducts.data.materials.MaterialsEntity
import com.example.jewerlyproducts.data.products.ProductsEntity

@Entity(
    primaryKeys = ["productName", "materialName"],
    foreignKeys = [
        ForeignKey(
            entity = ProductsEntity::class,
            parentColumns = ["productName"],
            childColumns = ["productName"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = MaterialsEntity::class,
            parentColumns = ["materialName"],
            childColumns = ["materialName"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ProductMaterialsCrossRef(
    val productName:String,
    val materialName:String,
    val quantity:Int
)

data class MaterialWithQuantity(
    @Embedded val material:MaterialsEntity,
    val quantity: Int
)


//data class ProductWithMaterials(
//    @Embedded val product: ProductsEntity,
//    @Relation(
//        parentColumn = "id",
//        entityColumn = "id",
//        associateBy = Junction(
//            ProductMaterialsCrossRef::class,
//            parentColumn = "productId",
//            entityColumn = "materialId"
//        )
//    )
//    val materials: List<MaterialsEntity>
//)
//
//
//data class MaterialWithQuantity(
//    @Embedded val material: MaterialsEntity,
//    val quantity: Int
//)
//
//data class ProductWithMaterialsAndQuantity(
//    @Embedded val product: ProductsEntity,
//    val materials: List<MaterialWithQuantity>
//)