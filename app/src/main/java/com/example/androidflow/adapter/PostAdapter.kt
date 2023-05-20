package com.example.androidflow.adapter


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.androidflow.R
import com.example.androidflow.databinding.ItemRowBinding
import com.example.androidflow.roomDB.dao.PostDao
import com.example.androidflow.roomDB.entity.FavouriteEntity
import com.example.androidflow.roomDB.entity.PostEntity

class PostAdapter(private val context: Context, private var postList: ArrayList<PostEntity>) :
    PagingDataAdapter<PostEntity, PostAdapter.PostViewHolder>(DIFF_CALLBACK) {

    private var favoriteDao: PostDao? = null

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PostEntity>() {
            override fun areItemsTheSame(
                oldItem: PostEntity, newItem: PostEntity
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: PostEntity, newItem: PostEntity
            ): Boolean = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostAdapter.PostViewHolder {
        return PostViewHolder(
            ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        Log.e("`TAG_postList`", "onBindViewHolder: "+post?.title )
        // val post = postList[position]
        holder.binding.apply {
            body.load("" + post!!.url)
            name.text = "(" + (position + 1).toString() + ") " + post!!.title


            val isFavorite: Boolean = favoriteDao!!.isFavorite(post.id)
            if (isFavorite) {
                ivFavorite.setImageResource(R.drawable.ic_favorite)
            } else {
                ivFavorite.setImageResource(R.drawable.ic_favorite_border)
            }
            ivFavorite.setOnClickListener { view ->
                val isCurrentlyFavorite: Boolean = favoriteDao!!.isFavorite(post.id)
                if (isCurrentlyFavorite) {
                    favoriteDao!!.deleteData(post.id)
                    ivFavorite.setImageResource(R.drawable.ic_favorite_border)
                } else {
                    var favouriteEntity = FavouriteEntity(
                        0, post.albumId, post.id, post.title, post.url, post.thumbnailUrl, ""
                    )
                    favoriteDao!!.insertFavorite(favouriteEntity)
                    ivFavorite.setImageResource(R.drawable.ic_favorite)
                }
            }
        }
    }

    override fun getItemCount(): Int = postList.size

    inner class PostViewHolder(val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root)

    fun setData(postDao: PostDao) {
        this.favoriteDao = postDao
        //this.postList = postList
        notifyDataSetChanged()
    }
}