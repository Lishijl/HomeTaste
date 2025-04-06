package com.example.hometaste

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.example.hometaste.data.DataExperience
import com.example.hometaste.data.dataStore
import com.example.hometaste.databinding.ActivityGraphicsBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.graphics.Color
// import com.github.mikephil.charting.formatter.DefaultValueFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class Graphics : AppCompatActivity() {
    private lateinit var binding: ActivityGraphicsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityGraphicsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch(Dispatchers.IO) {
            getDataExperience().collect { data ->
                withContext(Dispatchers.Main) {
                    // assignar valors a les columnes de data en el context del UI
                    UpdateBarGraph(data)
                    UpdatePieGraph(data)
                }
            }
        }
    }
    private fun UpdatePieGraph(data: DataExperience) {
        val entries = listOf(
            PieEntry(data.create.toFloat() , "Crear Recetas"), // Valor % menor
            PieEntry(data.edit.toFloat(), "Buscar Recetas")  // Valor busqueda superior %
        )
        val pieDataSet = PieDataSet(entries, "Distribució")
        pieDataSet.colors = listOf(Color.rgb(179, 226, 255), Color.rgb(171, 235, 205)) // blau i verd clar
        pieDataSet.valueTextColor = Color.BLACK // Color del text
        pieDataSet.valueTextSize = 16f // Mida del text
        // Afegeix el DataSet al PieData
        binding.graficaPie.apply {
            this.data = PieData(pieDataSet)
            binding.graficaPie.data = PieData(pieDataSet)
            description.isEnabled = false // no descripció
            isDrawHoleEnabled = true // forat del centre
            holeRadius = 40f // tamany del forat
            setHoleColor(Color.WHITE) // Color del forat
            animateY(1000) // Animació en Y
            setEntryLabelColor(Color.BLACK) // Color de les etiquetes
            setEntryLabelTextSize(14f) // Mida del text de les etiquetes
            invalidate()
        }
    }
    private fun UpdateBarGraph(data: DataExperience){
        val entries = listOf(
            BarEntry(0f, data.create.toFloat()),
            BarEntry(1f, data.edit.toFloat()),
            BarEntry(2f, data.delete.toFloat()),
            BarEntry(3f, data.search.toFloat())
        )
        val labels = listOf("Crear", "Editar", "Borrar", "Buscar")
        val barDataSet = BarDataSet(entries, "Resultados de interacciones")

        val actionColors = listOf(
            Color.rgb(156, 250, 135), // verd crear
            Color.rgb(250, 215, 135), // taronja editar
            Color.rgb(250, 135, 135), // vermell borrar
            Color.rgb(206, 135, 250), // lila buscar
        )
        barDataSet.colors = actionColors
        barDataSet.valueTextColor = Color.BLACK
        barDataSet.valueTextSize = 16f
        barDataSet.valueFormatter = IndexAxisValueFormatter(labels)

        binding.graficaBarra.apply {
            this.data= BarData(barDataSet)
            xAxis.setDrawGridLines(false)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            axisLeft.setDrawGridLines(false)
            axisRight.setDrawAxisLine(false)
            axisRight.setDrawGridLines(false)
            axisRight.setDrawLabels(false)
            description.text="Interacciones mas frecuentes"
            description.isEnabled = true // Activa la descripció
            setFitBars(true) // Ajusta les barres al gràfic
            animateY(1000) // Animació en Y
            invalidate()
        }
    }
    private fun getDataExperience() = dataStore.data.map { data ->
        DataExperience(
            create = data[intPreferencesKey("crear")] ?: 0,
            edit = data[intPreferencesKey("editar")] ?: 0,
            delete = data[intPreferencesKey("borrar")] ?: 0,
            search = data[intPreferencesKey("buscar")] ?: 0
        )
    }
}