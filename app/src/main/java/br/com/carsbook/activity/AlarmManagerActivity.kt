package br.com.carsbook.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import br.com.carsbook.R
import br.com.carsbook.alarmmanager.LembreMeReceiver
import br.com.carsbook.extensions.onClick
import br.com.carsbook.utils.AlarmUtil
import java.util.*

/**
 * Created by thiagozg on 31/01/2018.
 */
class AlarmManagerActivity : BaseActivity() {

    // Data/Tempo para agendar o alarme
    fun getTime() : Long {
        val c = Calendar.getInstance()
        c.timeInMillis = System.currentTimeMillis()
        c.add(Calendar.SECOND, 5)
        return c.timeInMillis
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_manager)

        onClick(R.id.btAgendar) { agendar() }
        onClick(R.id.btAgendarComRepeat) { agendarComRepeat() }
        onClick(R.id.btAgendarDtHr) { agendarParaHorarioEDataEspecifico() }
        onClick(R.id.btCancelar) { cancelar() }
    }

    fun agendar() {
        val intent = Intent(LembreMeReceiver.ACTION)
        // Agenda para daqui a 5 seg
        AlarmUtil.schedule(this, intent, getTime())
        Toast.makeText(this, "Alarme agendado.", Toast.LENGTH_SHORT).show()
    }

    fun agendarComRepeat() {
        val intent = Intent(LembreMeReceiver.ACTION)
        // Agenda para daqui a 5 seg, repete a cada 2 seg
        AlarmUtil.scheduleRepeat(this, intent, getTime(), (2 * 1000).toLong())
        Toast.makeText(this, "Alarme agendado com repetir.", Toast.LENGTH_SHORT).show()
    }

    fun agendarParaHorarioEDataEspecifico() {
        // agendando par ao horario das 11:?? em 31/01/2018
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
//        calendar.set(Calendar.YEAR, 2018)
//        calendar.set(Calendar.MONTH, 1)
        calendar.set(Calendar.DAY_OF_MONTH, 31)
        calendar.set(Calendar.HOUR_OF_DAY, 11)
        calendar.set(Calendar.MINUTE, 16)

        val time = calendar.timeInMillis
        val intent = Intent(LembreMeReceiver.ACTION)
        AlarmUtil.schedule(this, intent, time)
        Toast.makeText(this, "Alarme agendado com data e hora.", Toast.LENGTH_SHORT).show()
    }

    fun cancelar() {
        val intent = Intent(LembreMeReceiver.ACTION)
        AlarmUtil.cancel(this, intent)
        Toast.makeText(this, "Alarme cancelado", Toast.LENGTH_SHORT).show()
    }

}