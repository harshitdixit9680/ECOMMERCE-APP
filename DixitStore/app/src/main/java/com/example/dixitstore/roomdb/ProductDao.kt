package com.example.dixitstore.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductDao {
    @Delete
    suspend fun deleteProduct(product : ProductMoel)
    @Insert
    suspend fun insertProduct(product : ProductMoel)

    @Query("SELECT * FROM products")
    fun getAllProducts() : LiveData<List<ProductMoel>>

    @Query("SELECT * FROM products WHERE productId = :id")
    fun isExit(id:String) : ProductMoel

}