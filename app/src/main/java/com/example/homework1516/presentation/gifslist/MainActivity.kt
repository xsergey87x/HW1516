package com.example.homework1516.presentation.gifslist

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homework1516.R
import kotlinx.coroutines.*
import okhttp3.internal.notify
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by inject()
    private lateinit var adapter: GifAdapter
    var isLoading = false
    lateinit var gifList: RecyclerView
    lateinit var searchView: EditText
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchView = findViewById<EditText>(R.id.searchView)
        gifList = findViewById<RecyclerView>(R.id.gifList)
        progressBar = findViewById<ProgressBar>(R.id.progressBar)

        searchView.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch(searchView.text.toString())
            }
            true
        }
        viewModel.searchResult.observe(this) {
            if (!it.isNullOrEmpty()) {
                adapter.results = it
                adapter.notifyDataSetChanged()
            }
        }
        val layoutManager = LinearLayoutManager(this)
        gifList.layoutManager = layoutManager
        adapter = GifAdapter(this)
        gifList.adapter = adapter

        gifList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val k = layoutManager.findLastCompletelyVisibleItemPosition()

                if ((!isLoading) && (dy > 0)) {
                    if (k == (adapter.itemCount - 1)) {
                        isLoading = true
                        loadMore(k)
                    }
                }
            }
        })
        gifList.setRecyclerListener { holder ->
            val gifViewHolder: GifViewHolder = holder as GifViewHolder
            Glide.with(this@MainActivity).clear(gifViewHolder.gifView)
        }
    }

    private fun loadMore(k: Int) {
        progressBar.visibility = ProgressBar.VISIBLE
        val heightProgressBar = progressBar.height
        gifList.setPadding(0, 0, 0, heightProgressBar + 20)

        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.performSearchNext(searchView.text.toString(), (k + 1).toString())
            progressBar.visibility = ProgressBar.GONE
            gifList.setPadding(0, 0, 0, 0)
            adapter.notifyDataSetChanged()  // Работает и без этого но на эмуляторе с ним плавнее подгружает next part
            isLoading = false
        }, 2500)
    }

    private fun performSearch(searchText: String) {
        if (searchText.isBlank()) {
            Toast.makeText(this, "Input text must be not empty", Toast.LENGTH_SHORT).show()
            return
        }
        viewModel.performSearch(searchText, "0")
    }
}

/// https://api.giphy.com/v1/gifs/search?api_key=NRKtaEWTHEzvBGNfvXJfioQN6Zz1NKPm&q=cat&offset=5&limit=10
///   key for find cat offset - first value, limit - image item - 0..9 for search string !!!