package com.example.planner.feature_taskRepeat.domain.use_cases

import com.example.planner.feature_quotes.domain.models.Quote
import com.example.planner.feature_quotes.domain.useCases.QuotesUseCases
import com.example.planner.feature_taskRepeat.domain.models.RepeatTaskWithTask
import com.example.planner.feature_taskRepeat.domain.repository.RepeatTaskRepository
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class GetRepeatTaskData(
    private val repository: RepeatTaskRepository,
    private val quotesUseCases: QuotesUseCases
) {

    operator fun invoke(date: LocalDate) =
        repository.getRepeatTasksWithTask(date).map { tasks ->

            val list = mutableListOf<DataType>()

            val completedTask = mutableListOf<DataType>()
            val inCompletedTask = mutableListOf<DataType>()

            for (task in tasks){
                if(task.repeatTask.taskDone){
                    completedTask.add(DataType.RepeatTask(task))
                }else {
                    inCompletedTask.add(DataType.RepeatTask(task))
                }
            }

            if(inCompletedTask.isNotEmpty()){
                list.add(DataType.Divider(title = "In-completed Tasks"))
                list.addAll(inCompletedTask)
            }

            if(completedTask.isNotEmpty()){
                list.add(DataType.Divider(title = "Completed Tasks"))
                list.addAll(completedTask)
            }

            val quote = quotesUseCases.getQuote(date)

            RepeatTaskData(
                list = list,
                quote = quote.getOrNull()
            )
        }

    data class RepeatTaskData(
        val list: List<DataType>,
        val quote: Quote? = null
    )
}

sealed class DataType {

    data class Divider(val title: String) : DataType()

    data class RepeatTask(val repeatTaskWithTask: RepeatTaskWithTask): DataType()

}