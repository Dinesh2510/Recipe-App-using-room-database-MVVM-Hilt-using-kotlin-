package com.example.androidflow

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidflow.adapter.FavouriteAdapter
import com.example.androidflow.databinding.ActivityFavoriteBinding
import com.example.androidflow.roomDB.database.PostDatabase
import com.example.androidflow.roomDB.entity.FavouriteEntity
import com.example.androidflow.viewmodel.FavViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class FavoriteActivity : AppCompatActivity() {
    lateinit var binding: ActivityFavoriteBinding
    private lateinit var postAdapter: FavouriteAdapter
    private val favViewModel: FavViewModel by viewModels()
    lateinit var progressBar: ProgressBar
    @Inject
    lateinit var db: PostDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = "Favourite Listing"
        binding.toolbar.setTitleTextColor(resources.getColor(com.google.android.material.R.color.m3_ref_palette_white))

        // getActionBar()?.setIcon(R.drawable.my_icon);
        setUi()

        lifecycleScope.launch {
            favViewModel.getFullListFav()
                .collect{data ->
                binding.recyclerView.visibility = View.VISIBLE
                postAdapter.setData(data as ArrayList<FavouriteEntity>, db.getPostDao())
            }
        }

    }

    private fun setUi() {
        postAdapter = FavouriteAdapter(this, ArrayList())
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            adapter = postAdapter
        }


    }
    private fun showProgress(){
        val layout = RelativeLayout(this)
        progressBar = ProgressBar(this, null, androidx.appcompat.R.attr.progressBarStyle)
        progressBar.isIndeterminate = true
        progressBar.visibility = View.VISIBLE
        val params = RelativeLayout.LayoutParams(100, 100)
        params.addRule(RelativeLayout.CENTER_IN_PARENT)
        layout.addView(progressBar, params)
        setContentView(layout)
    }
    private fun goneProgress(){
        progressBar.visibility = View.GONE
    }

}