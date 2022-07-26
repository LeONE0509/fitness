package com.example.fitness

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.fitness.databinding.UserItemBinding

class UserAdapter: RecyclerView.Adapter<UserAdapter.UserHolder>() {
    private val userList = ArrayList<User>()
    class UserHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = UserItemBinding.bind(item)
        fun bind(user: User) = with(binding) {
            tvRatePlace.text = user.place.toString()
            imAvatar.setImageResource(user.imageId)
            tvName.text = user.name

            lateinit var stepsString: String
            if((user.steps >= 1000000) or (user.steps < 1000)){
                stepsString = user.steps.toString()
            }
            else{
                var remainderOfDivisionInt = user.steps%1000
                val remainderOfDivisionStr: String
                if (remainderOfDivisionInt < 10){
                    remainderOfDivisionStr = "00" + remainderOfDivisionInt.toString()
                }
                else if ((remainderOfDivisionInt >= 10) and (remainderOfDivisionInt < 100)){
                    remainderOfDivisionStr = "0" + remainderOfDivisionInt.toString()
                }
                else{
                    remainderOfDivisionStr = remainderOfDivisionInt.toString()
                }

                stepsString = "${user.steps/1000} ${remainderOfDivisionStr}"
            }

            tvSteps.text = stepsString

            if (user.itsMe){
                val backgroundColorForCard = ContextCompat.getColor(itemView.context, R.color.orange)
                cvUserItem.backgroundTintList = ColorStateList.valueOf(backgroundColorForCard)
            }
            else{
                val backgroundColorForCard = ContextCompat.getColor(itemView.context, R.color.light_black)
                cvUserItem.backgroundTintList = ColorStateList.valueOf(backgroundColorForCard)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return UserHolder(view)
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    public fun addUser(user: User){
        userList.add(user)
        notifyDataSetChanged()
    }

    public fun clearUserList(){
        userList.clear()
    }





}