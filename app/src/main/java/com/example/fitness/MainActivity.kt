package com.example.fitness

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitness.databinding.ActivityMainBinding
import com.example.fitness.databinding.LeaderboardBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    // Переменная, которая несёт в себе ссылки на все элементы экрана
    lateinit var bindingClass: ActivityMainBinding

    private val userAdapter = UserAdapter()

    private val monthAdapter = MonthAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        val bottomSheetDialog = BottomSheetDialog(
            this@MainActivity,
            R.style.BottomSheetDialogTheme
        )

        // CalendarBottomSheet
        val CalendarBottomSheetView = LayoutInflater.from(applicationContext).inflate(
            R.layout.calendar_bottom_sheet,
            // Сюда писать id layout, которое мы создали
            findViewById(R.id.bottomSheet) as LinearLayout?
        )
        val b_calendar = findViewById<Button>(R.id.openCalendarButton)
        b_calendar.setOnClickListener {

            CalendarBottomSheetView.findViewById<View>(R.id.close_ic_button).setOnClickListener {
                bottomSheetDialog.dismiss()
            }

            bottomSheetDialog.setContentView(CalendarBottomSheetView)
            bottomSheetDialog.show()

            val calendar = CalendarBottomSheetView.findViewById<RecyclerView>(R.id.rc_calendar)
            calendar.adapter = monthAdapter
            calendar.layoutManager = LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false)

            monthAdapter.clearMonthList()

            val January2022 = MonthItem(5, 2022)
            val February2022 = MonthItem(6, 2022)
            val March2022 = MonthItem(7, 2022)
            monthAdapter.addMonth(January2022)
            monthAdapter.addMonth(February2022)
            monthAdapter.addMonth(March2022)

        }

        // LeaderBottomSheet
        val LeaderboardBottomSheetView = LayoutInflater.from(applicationContext).inflate(
            R.layout.leaderboard_bottom_sheet,
            // Сюда писать id layout, которое мы создали
            findViewById(R.id.leaderboard) as LinearLayout?
        )
        val b_leaderboard = findViewById<Button>(R.id.openLeaderboardButton)
        b_leaderboard.setOnClickListener {
            LeaderboardBottomSheetView.findViewById<View>(R.id.return_ic_button)
                .setOnClickListener {
                    bottomSheetDialog.dismiss()
                }
            bottomSheetDialog.setContentView(LeaderboardBottomSheetView)

            val losers = LeaderboardBottomSheetView.findViewById<RecyclerView>(R.id.rc_winners)
            losers.adapter = userAdapter
            losers.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

            bottomSheetDialog.show()

            /*HardCode устанавливаю все значения как в figma
            https://www.figma.com/file/sosLpZVaVP0cQiJbOB7GgP/Sport-v3-Practice?node-id=401%3A22615*/
            // Объявляем всех участников топа
            userAdapter.clearUserList()
            val place1: User = User(1, R.drawable.place1, "Матвей", 938981)
            val place2: User = User(2, R.drawable.place2, "Павел", 301001)
            val place3: User = User(3, R.drawable.place3, "Кристина", 291811)
            val place4: User = User(4, R.drawable.place4, "Сергей", 103981)
            val place5: User = User(5, R.drawable.place5, "Костя", 90981)
            val place6: User = User(6, R.drawable.place6, "Оля", 88930)
            val place7: User = User(7, R.drawable.place7, "Юля", 77031)
            val place8: User = User(8, R.drawable.place8, "Юля", 62001)
            val place9: User = User(9, R.drawable.place9, "Юля", 51988)
            val place10: User = User(10, R.drawable.place10, "Юля", 40981)

            // Добабляем их всех в массив
            var topUsers = arrayListOf<User>()
            topUsers.add(place1)
            topUsers.add(place2)
            topUsers.add(place3)
            topUsers.add(place4)
            topUsers.add(place5)
            topUsers.add(place6)
            topUsers.add(place7)
            topUsers.add(place8)
            topUsers.add(place9)
            topUsers.add(place10)

            // 1ое место
            var avatar = LeaderboardBottomSheetView.findViewById<ImageView>(R.id.firstPlace_avatar)
            avatar.setImageResource(R.drawable.place1)
            var name = LeaderboardBottomSheetView.findViewById<TextView>(R.id.firstPlace_name)
            name.text = topUsers[0].name
            var steps = LeaderboardBottomSheetView.findViewById<TextView>(R.id.firstPlace_steps)
            steps.text = topUsers[0].steps.toString()

            // 2oe место
            avatar = LeaderboardBottomSheetView.findViewById<ImageView>(R.id.secondPlace_avatar)
            avatar.setImageResource(R.drawable.place2)
            name = LeaderboardBottomSheetView.findViewById<TextView>(R.id.secondPlace_name)
            name.text = topUsers[1].name
            steps = LeaderboardBottomSheetView.findViewById<TextView>(R.id.secondPlace_steps)
            steps.text = topUsers[1].steps.toString()

            // 3е место
            avatar = LeaderboardBottomSheetView.findViewById<ImageView>(R.id.thirdPlace_avatar)
            avatar.setImageResource(R.drawable.place3)
            name = LeaderboardBottomSheetView.findViewById<TextView>(R.id.thirdPlace_name)
            name.text = topUsers[2].name
            steps = LeaderboardBottomSheetView.findViewById<TextView>(R.id.thirdPlace_steps)
            steps.text = topUsers[2].steps.toString()
            userAdapter.addUser(topUsers[3])
            userAdapter.addUser(topUsers[4])
            userAdapter.addUser(topUsers[5])
            userAdapter.addUser(topUsers[6])
            userAdapter.addUser(topUsers[7])
            userAdapter.addUser(topUsers[8])
            userAdapter.addUser(topUsers[9])

            // Моя карточка
            avatar = LeaderboardBottomSheetView.findViewById(R.id.imAvatar)
            avatar.setImageResource(R.drawable.me)
            name = LeaderboardBottomSheetView.findViewById(R.id.tvName)
            name.text = "Я"
            steps = LeaderboardBottomSheetView.findViewById((R.id.tvSteps))
            steps.text = "14 981"
            val place = LeaderboardBottomSheetView.findViewById<TextView>(R.id.tvRatePlace)
            place.text = "23"
            val myCardView = LeaderboardBottomSheetView.findViewById<CardView>(R.id.cv_userItem)

            val backgroundColorOrangeForMyCard = ContextCompat.getColor(this, R.color.orange)
            myCardView.backgroundTintList = ColorStateList.valueOf(backgroundColorOrangeForMyCard)
        }
    }
}

