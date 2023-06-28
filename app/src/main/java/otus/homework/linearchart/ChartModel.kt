package otus.homework.linearchart

import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import otus.homework.linearchart.MyApp.Companion.myResource
import java.nio.charset.Charset

class ChartModel : ViewModel() {
    private val myData: List<PayLoad>
    var grafData: Map<Long, Int>
    var maxAmount = 0
    var maxTime = 0L
    var minTime = 0L

    init {
        myData = loadData()
        grafData = myData
            .groupBy { it.time }
            .mapValues {
                it.value.map { it.amount }
                    .fold(0) { summ, time -> summ + time }
            }
        maxAmount = grafData.maxOf { it.value }
        maxTime = grafData.maxOf { it.key }
        minTime = grafData.minOf { it.key }
    }

    fun loadData(): List<PayLoad> {
        val gson = Gson()
        val type = object : TypeToken<List<PayLoad>>() {}.type
        myResource.reset()
        val myJson = myResource.bufferedReader(Charset.defaultCharset())
        return gson.fromJson(myJson, type)
    }
}
