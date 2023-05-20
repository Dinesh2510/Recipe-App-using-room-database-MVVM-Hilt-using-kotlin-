package com.example.androidflow


import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidflow.adapter.PostAdapter
import com.example.androidflow.databinding.ActivityMainBinding
import com.example.androidflow.pagining.MainLoadStateAdapter
import com.example.androidflow.roomDB.database.PostDatabase
import com.example.androidflow.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
open class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var postAdapter: PostAdapter
    private val postViewModel: PostViewModel by viewModels()
    lateinit var binding: ActivityMainBinding
    var pDialog :ProgressDialog? = null

    @Inject
    lateinit var db: PostDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        title = "";
        binding.toolbar.title = "Home"
        binding.toolbar.setTitleTextColor(resources.getColor(com.google.android.material.R.color.m3_ref_palette_white))
        binding.floatingActionButton.setOnClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))
        }
        showProgress()
        setUi()
        postViewModel.getPost()
        postViewModel.response.observe(this, Observer { response ->
            postViewModel.deleteAllPostTable()
            postViewModel.insertData(response)
            hideProgress()
        })


        /*lifecycleScope.launch {
            postViewModel.getAllPost.collect { response ->
                binding.recyclerView.visibility = View.VISIBLE
                postAdapter.setData(response as ArrayList<PostEntity>, db.getPostDao())
            }
        }*/


        lifecycleScope.launch {
            postViewModel.getDataFromRoomWithOffset.collectLatest {
                postAdapter.setData(db.getPostDao())
                postAdapter.submitData(it)

            }
        }
        binding.recyclerView.adapter = postAdapter.withLoadStateFooter(
            MainLoadStateAdapter()
        )
    }

    private fun setUi() {
        recyclerView = findViewById(R.id.recyclerView)
        postAdapter = PostAdapter(this, ArrayList())
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = postAdapter
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.new_activity -> {
                startActivity(Intent(this, FavoriteActivity::class.java))
                return false
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        exitByBackKey()
    }

    fun appExit() {
        finish()
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    protected fun exitByBackKey() {
        val alertbox: AlertDialog = AlertDialog.Builder(this)
            .setMessage("Do you want to exit application?")
            .setPositiveButton("Yes", DialogInterface.OnClickListener { arg0, arg1 ->
                appExit()
            })
            .setNegativeButton("No", // do something when the button is clicked
                DialogInterface.OnClickListener { arg0, arg1 ->

                })
            .show()
    }

    private fun showProgress() {
        pDialog= ProgressDialog(this)
        pDialog!!.setMessage("please wait...")
        pDialog?.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        pDialog?.setCancelable(false)
        pDialog?.show()
    }

    private fun hideProgress() {
        pDialog?.dismiss();

    }
}