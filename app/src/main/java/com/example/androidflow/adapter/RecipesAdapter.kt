package com.example.androidflow.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.androidflow.R
import com.example.androidflow.Utlis.RecipesDiffUtil
import com.example.androidflow.databinding.RecipesRowLayoutBinding
import com.example.androidflow.models.ResultListing
import com.example.androidflow.roomDB.dao.FoodRecipeDao
import com.example.androidflow.roomDB.entity.FavoritesEntity
import com.example.androidflow.viewmodel.RecipesViewModel
import com.google.android.material.snackbar.Snackbar
import org.jsoup.Jsoup


class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.MyViewHolder>() {

    private var recipes = emptyList<ResultListing>()
    private lateinit var mainViewModel: RecipesViewModel
    private lateinit var mcontext: Context
    private var recipeSaved = false
    private var favoriteDao: FoodRecipeDao? = null

    class MyViewHolder(private val binding: RecipesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {


        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecipesRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }

        fun bind(
            result: ResultListing,
            mainViewModel: RecipesViewModel,
            holder: MyViewHolder,
            favoriteDao: FoodRecipeDao?,
        ) {
            binding.titleTextView.text = result.title
            binding.clockTextView.text = result.readyInMinutes.toString() + " min"
            binding.recipeImageView.load(result.image) {
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
            }
            applyVeganColor(binding.leafTextView, result.vegan)
            applyVeganColor(binding.leafImageView, result.vegan)
            parseHtml(binding.descriptionTextView, result.summary)

            if (favoriteDao?.isFavorite(result.recipeId) == true) {
                binding.heartImageView.load(R.drawable.ic_favorite)
                binding.heartImageView.setOnClickListener {
                    val favoritesEntity = FavoritesEntity(
                        0,
                        result.aggregateLikes,
                        result.cheap,
                        result.dairyFree,
                        result.glutenFree,
                        result.recipeId,
                        result.image,
                        result.readyInMinutes,
                        result.sourceName,
                        result.sourceUrl,
                        result.summary,
                        result.title,
                        result.vegan,
                        result.vegetarian,
                        result.veryHealthy
                    )
                    mainViewModel.deleteFavoriteRecipe(favoritesEntity)
                    showSnackBar("Remove from Favorite")
                    binding.heartImageView.load(R.drawable.ic_favorite_border)
                }

            } else {
                binding.heartImageView.load(R.drawable.ic_favorite_border)
                binding.heartImageView.setOnClickListener {
                    val favoritesEntity = FavoritesEntity(
                        0,
                        result.aggregateLikes,
                        result.cheap,
                        result.dairyFree,
                        result.glutenFree,
                        result.recipeId,
                        result.image,
                        result.readyInMinutes,
                        result.sourceName,
                        result.sourceUrl,
                        result.summary,
                        result.title,
                        result.vegan,
                        result.vegetarian,
                        result.veryHealthy
                    )
                    mainViewModel.insertFavoriteRecipe(favoritesEntity)
                    showSnackBar("Added to Favorite")
                    binding.heartImageView.load(R.drawable.ic_favorite)
                }

            }

          /*  CoroutineScope(Dispatchers.IO).launch {
                Log.e("TAG_BOOLEAN", "bind: "+mainViewModel.isFavorite(result.recipeId) )
            }*/
/*            mainViewModel.isFavorite(result.recipeId)
                .observe(holder.itemView.context as LifecycleOwner, Observer {
                    try {
                        Log.e("TAG_isFavorite", "bind: " + it)
                    } catch (e: Exception) {
                        Log.e("TAG_isFavorite_ERROR", "bind: " + e.toString())
                    }

                    // checkFav = it
                })*/

        }

        private fun showSnackBar(message: String) {
            Snackbar.make(this.binding.cardView, message, Snackbar.LENGTH_SHORT)
                .setAction("Okay") {}
                .show()
        }

        private fun parseHtml(textView: TextView, description: String?) {
            if (description != null) {
                val desc = Jsoup.parse(description).text()
                textView.text = desc
            }
        }

        private fun applyVeganColor(view: View, vegan: Boolean) {
            if (vegan) {
                when (view) {
                    is TextView -> {
                        view.setTextColor(
                            ContextCompat.getColor(
                                view.context,
                                R.color.green
                            )
                        )
                    }

                    is ImageView -> {
                        view.setColorFilter(
                            ContextCompat.getColor(
                                view.context,
                                R.color.green
                            )
                        )
                    }
                }
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        mcontext = holder.itemView.context
        val currentRecipe = recipes[position]

        holder.bind(currentRecipe, mainViewModel, holder, favoriteDao)

    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    fun setData(
        foodRecipe: List<ResultListing>,
        recipesViewModel: RecipesViewModel,
        foodRecipeDao: FoodRecipeDao,
    ) {
        val recipesDiffUtil = RecipesDiffUtil(recipes, (foodRecipe))
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        recipes = (foodRecipe)
        diffUtilResult.dispatchUpdatesTo(this)
        this.mainViewModel = recipesViewModel
        this.favoriteDao= foodRecipeDao
    }


}