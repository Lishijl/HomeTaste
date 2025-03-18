package com.example.hometaste

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.hometaste.databinding.ActivityMyRecipiesBinding
import com.example.hometaste.databinding.ActivityRecipeFormBinding
import com.example.hometaste.recipies.Recipe
import com.example.hometaste.recipies.RecipeAdapter

class RecipeForm : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.createRecipe.setOnClickListener {
            val name = binding.title.text.toString()
            val description = binding.description.text.toString()
            val time = binding.time.text.toString()
            val skillLvl = binding.skillLvl.text.toString()
            val serving = binding.serving.text.toString()
            val imageUrl = binding.imageUrl.text.toString()

            val intentResult = Intent()
            //intentResult.putExtra("newRecipe", newRecipe)
            setResult(Activity.RESULT_OK, intentResult)
            finish()
        }
    }
}