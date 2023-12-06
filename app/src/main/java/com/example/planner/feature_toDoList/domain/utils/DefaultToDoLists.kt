package com.example.planner.feature_toDoList.domain.utils

import com.example.planner.common.domain.utils.PlannerIcons
import com.example.planner.feature_toDoList.domain.models.ToDoList

object DefaultToDoLists {

    private const val FAVORITE_LIST_ID = -1
    private const val TODAY_LIST_ID = -2
    private const val YESTERDAY_LIST_ID = -3

    val FAVORITES_LIST = ToDoList(id = FAVORITE_LIST_ID,"Favorites", icon = PlannerIcons.HEART)

    val TODAY_LIST =  ToDoList(id = TODAY_LIST_ID,"Today", icon = PlannerIcons.CALENDAR)

    val YESTERDAY_LIST = ToDoList(id = YESTERDAY_LIST_ID,"Yesterday", icon = PlannerIcons.CALENDAR)
}