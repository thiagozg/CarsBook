package br.com.carsbook.domain.dao

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import br.com.carsbook.domain.model.Carro

/**
 * Created by thiagozg on 10/12/2017.
 */
@Database(entities = arrayOf(Carro::class), version = 1)
abstract class CarrosDatabase : RoomDatabase() {
    abstract fun carroDAO(): CarrosDAO
}