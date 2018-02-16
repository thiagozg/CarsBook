package br.com.carsbook.domain.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import br.com.carsbook.domain.model.Carro

/**
 * Created by thiagozg on 10/12/2017.
 */
@Dao
interface CarrosDAO {
    @Query("SELECT * FROM carro where id = :arg0")
    fun getById(id: Long): Carro?

    @Query("SELECT * FROM carro")
    fun findAll(): List<Carro>

    @Insert
    fun insert(carro: Carro)

    @Delete
    fun delete(carro: Carro)
}