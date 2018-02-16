package br.com.carsbook.domain.dao

import android.arch.persistence.room.Room
import br.com.carsbook.CarrosApplication

object DatabaseManager {
    private var dbInstance: CarrosDatabase

    init {
        val appContext = CarrosApplication.getInstance().applicationContext
        dbInstance = Room.databaseBuilder(
                appContext,
                CarrosDatabase::class.java,
                "carros.sqlite")
                .build()
    }

    fun getCarroDAO(): CarrosDAO {
        return dbInstance.carroDAO()
    }
}
