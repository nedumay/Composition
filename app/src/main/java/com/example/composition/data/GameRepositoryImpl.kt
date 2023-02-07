package com.example.composition.data

import com.example.composition.domain.entity.GameSettings
import com.example.composition.domain.entity.Level
import com.example.composition.domain.entity.Question
import com.example.composition.domain.repository.GameRepository
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

object GameRepositoryImpl: GameRepository {

    private const val MIN_SUM_VALUE = 2
    private const val MIN_ANSWER_VALUE = 1

    // Реализация генерации вопросов
    override fun generateQuestion(maxSumValue: Int, countOfOption: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue + 1)
        val visibleNumber = Random.nextInt(MIN_ANSWER_VALUE, sum)
        val options = HashSet<Int>() // коллекция всех вариантов ответа
        val rightAnswer = sum - visibleNumber // правильный ответ
        options.add(rightAnswer) // добавить правильный ответ в HashSet
        val from = max(rightAnswer - countOfOption, MIN_ANSWER_VALUE) // установка нижней границы
        val to = min(maxSumValue, rightAnswer+countOfOption) // установка верхней границы
        while (options.size < countOfOption){
            options.add(Random.nextInt(from,to)) //заполнение HashSet
        }
        return Question(sum,visibleNumber,options.toList())
    }

    // Реализация генерации настройки программы
    override fun getGameSettings(level: Level): GameSettings {
        return when(level){
            Level.EASY ->{
                GameSettings(10,10,70,60)
            }
            Level.NORMAL ->{
                GameSettings(20,20,80,50)
            }
            Level.HARD ->{
                GameSettings(30,30,90,40)
            }
        }
    }

}