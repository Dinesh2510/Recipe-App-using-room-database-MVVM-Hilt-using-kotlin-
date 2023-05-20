package com.example.androidflow.pagining

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.androidflow.roomDB.dao.PostDao
import com.example.androidflow.roomDB.entity.PostEntity
import kotlinx.coroutines.delay

class MainPagingSource(
    private val dao: PostDao
) : PagingSource<Int, PostEntity>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostEntity> {
        val page = params.key ?: 0

        return try {
            val entities = dao.getPagedList(params.loadSize, page * params.loadSize)
            Log.e("TAG_page", "load: "+page )
            // simulate page loading
            if (page != 0) delay(5000)

            LoadResult.Page(
                data = entities,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (entities.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PostEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}