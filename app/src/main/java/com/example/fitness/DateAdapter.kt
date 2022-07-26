package com.example.fitness

import android.os.LocaleList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.fitness.databinding.CalendarCellBinding
import java.time.LocalDate
import kotlin.system.exitProcess

/*Это RecyclerView который будет отображать даты в календаре*/
/*Здесь не нужен отдельный класс, который мы будем передаватьв адаптер
* Дни месяца будут хранится в массиве строк
* Пример: Июль 2022, 1ое число в ПТ, будет хранится в виде
* "", "", "", "", "1", "2", "3"... */
class DateAdapter():RecyclerView.Adapter<DateAdapter.DateHolder>() {
    /*В этой переменной и хранятся даты
    * Если такая ситуация, что 1ое число это НЕ понедельник,
    * то тогда в массиве такой день будет хранится как ""*/
    private val dateList = ArrayList<DateItem>()


    class DateHolder(item: View): RecyclerView.ViewHolder(item){
        private val binding = CalendarCellBinding.bind(item)

        fun bind(dateItem: DateItem) = with(binding){
            cellDayText.text = dateItem.date
            /*tempDate обрабатывает ситуацию, когда dateItem.date == ""*/
            val tempDate = if (dateItem.date == "") 1 else dateItem.date.toInt()

            /*Если дата, которую мы добавляем в календаь ещё не наступила,
            * то такая дата отображается серым цветом*/
            val currentDate = LocalDate.of(dateItem.year, dateItem.month, tempDate)
            val isFutureDay = currentDate.isAfter(LocalDate.now())
            val textColor = ContextCompat.getColor(itemView.context, R.color.light_black)
            if(isFutureDay) this.cellDayText.setTextColor(textColor)
            if(currentDate == LocalDate.now()) this.cellDayText.setBackgroundResource(R.drawable.empty_ellipse)



//            cellDayText.setOnClickListener{
//                if (!dateItem.isSelected){
//                    this.cellDayText.setBackgroundResource(R.drawable.fill_ellipse)
//                    dateItem.isSelected = true
//                } else{
//                    if(currentDate == LocalDate.now()) {
//                        this.cellDayText.setBackgroundResource(R.drawable.empty_ellipse)
//                    }
//                    this.cellDayText.background = null
//                    dateItem.isSelected = false
//                }
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.calendar_cell, parent, false)
        return DateHolder(view)
    }

    override fun onBindViewHolder(holder: DateHolder, position: Int) {
        holder.bind(dateList[position])
    }

    override fun getItemCount(): Int {
        return dateList.size
    }

    public fun addDate(dateItem: DateItem){
        dateList.add(dateItem)
    }

    public fun clearDateList(){
        dateList.clear()
    }

}