package com.example.androidflow.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.androidflow.R
import com.example.androidflow.roomDB.dao.PostDao
import com.example.androidflow.roomDB.entity.FavouriteEntity


class FavouriteAdapter(
    private val context: Context,
    private var postList: ArrayList<FavouriteEntity>
) :
    RecyclerView.Adapter<FavouriteAdapter.PostViewHolder>() {
    private var favoriteDao: PostDao? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        )
    }
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = postList[position]
        holder.body.load("" + post.url)
        holder.name.text = "(" + (position + 1).toString() + ") " + post!!.title

        val isFavorite: Boolean = favoriteDao!!.isFavorite(post.id)
        if (isFavorite) {
            holder.iv_favorite.setImageResource(R.drawable.ic_favorite)
        } else {
            holder.iv_favorite.setImageResource(R.drawable.ic_favorite_border)
        }
        holder.iv_favorite.setOnClickListener { view ->
            val isCurrentlyFavorite: Boolean = favoriteDao!!.isFavorite(post.id)
            if (isCurrentlyFavorite) {
                favoriteDao!!.deleteData(post.id)
                holder.iv_favorite.setImageResource(R.drawable.ic_favorite_border)
            } else {
                var favouriteEntity = FavouriteEntity(
                    0,
                    post.albumId,
                    post.id,
                    post.title,
                    post.url,
                    post.thumbnailUrl,
                    ""
                )
                favoriteDao!!.insertFavorite(favouriteEntity)
                holder.iv_favorite.setImageResource(R.drawable.ic_favorite)
            }
        }

    }

    override fun getItemCount(): Int = postList.size
    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val body: ImageView = itemView.findViewById(R.id.body)
        val name: TextView = itemView.findViewById(R.id.name)
        val iv_favorite: ImageView = itemView.findViewById(R.id.iv_favorite)
    }

    fun setData(postList: ArrayList<FavouriteEntity>, postDao: PostDao) {
        this.favoriteDao = postDao
        this.postList = postList
        notifyDataSetChanged()
    }

}


