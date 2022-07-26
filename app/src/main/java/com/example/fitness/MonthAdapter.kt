package com.example.fitness

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitness.databinding.MonthItemBinding
import java.time.LocalDate
import kotlin.collections.ArrayList

/*Это RecyclerView, который будет отобрать месяц полностью
* Сначала идет название месяца, далее год и ниже даты согласно\
* https://www.figma.com/file/sosLpZVaVP0cQiJbOB7GgP/Sport-v3-Practice?node-id=401%3A23844
* Это RecyclerView, в котором содержится другой RecyclerView -> DateAdapter*/
class MonthAdapter(): RecyclerView.Adapter<MonthAdapter.MonthHolder>() {
    /*В этой переменной будут хранится месяца, которые следует отобразить
    * в окончательной форме. См. class MonthItem*/
    private val monthList = ArrayList<MonthItem>()

    class MonthHolder(item: View):RecyclerView.ViewHolder(item) {
        private val binding = MonthItemBinding.bind(item)
        val rc_dates = binding.rcDatesOfMonth
        /*Эта переменная для работы с DateAdapter, чтобы заполнить даты*/

        fun bind(monthItem: MonthItem) = with(binding){

            /*В этом участке кода мы работаем с заголовком месяца*/
            tvMonthPlusYear.text =  getCurrentMonthByNumber(monthItem.month) + " ${monthItem.year}"

        }

        private fun getCurrentMonthByNumber(monthNumber: Int): String{
            return when(monthNumber){
                1 -> "Январь"
                2 -> "Февраль"
                3 -> "Март"
                4 -> "Апрель"
                5 -> "Май"
                6 -> "Июнь"
                7 -> "Июль"
                8 -> "Август"
                9 -> "Сентябрь"
                10 -> "Октябрь"
                11 -> "Ноябрь"
                12 -> "Декабрь"
                else -> "INVALID MONTH NUMBER"
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.month_item, parent, false)
        return MonthHolder(view)
    }

    override fun onBindViewHolder(holder: MonthHolder, position: Int) {
        holder.bind(monthList[position])

        /*Здесь мы управляем другим RecyclerView, а конкретно DataAdapter,
         * который отвечает за заполение дат в календаре*/
        val dateAdapter = DateAdapter()
        holder.rc_dates.adapter = dateAdapter
        holder.rc_dates.isNestedScrollingEnabled = false

        /*В этом участке кода мы работает с датами и сразу заполняем наш календарь датами
        * ourDate - 1 число месяца и года, которые хранятся в массиве месяцев monthList
        * quantityDays - количество дней в месяце, к которому относится дата
        * firstDayOfWeek - строка, в которой хранится день недели 1го числа месяца
        * lastDayOfWeek - дата, в которой хранится 31 число месяца, с которым мы работаем
        * lastDayOfWeek - строка, в которой хранится день недели 31го числа месяца*/
        val ourDate = LocalDate.of(monthList[position].year,
                                monthList[position].month, 1)
        val quantityDays = ourDate.lengthOfMonth()
        val firstDayOfWeek = ourDate.dayOfWeek

        // Здесь мы заполняем наш календарь датами
        // Если 1ое число не ПН, то следует заполнить эти места пустой строкой
        val numberOfDayOfFirstWeekDay = getNumberOfDaysOfWeek(firstDayOfWeek.toString())
        for (i in 1 until numberOfDayOfFirstWeekDay){
            dateAdapter.addDate((DateItem("",
                                        monthList[position].month,
                                        monthList[position].year)))
        }

        // Заполняем наш календарь датами
        for (i in 1..quantityDays){
            dateAdapter.addDate(DateItem(i.toString(),
                                        monthList[position].month,
                                        monthList[position].year))
        }
    }

    override fun getItemCount(): Int {
        return monthList.size
    }

    public fun addMonth(monthItem: MonthItem){
        monthList.add(monthItem)
    }

    public fun clearMonthList(){
        monthList.clear()
    }

    private fun getNumberOfDaysOfWeek(dayOfWeek: String): Int{
        return when(dayOfWeek){
            "MONDAY" -> 1
            "TUESDAY" -> 2
            "WEDNESDAY" -> 3
            "THURSDAY" -> 4
            "FRIDAY" -> 5
            "SATURDAY" -> 6
            "SUNDAY" -> 7
            else -> 0
        }
    }

}