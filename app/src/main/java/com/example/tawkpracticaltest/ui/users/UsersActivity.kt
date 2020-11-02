package com.example.tawkpracticaltest.ui.users

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.exam.tawk.ui.users.UsersAdapter
import com.example.tawkpracticaltest.R
import com.example.tawkpracticaltest.data.GithubService
import com.example.tawkpracticaltest.data.models.GithubUserProfile
import com.example.tawkpracticaltest.data.models.GithubUser
import com.example.tawkpracticaltest.ui.TawkUiState
import com.example.tawkpracticaltest.ui.TawkViewModel
import com.example.tawkpracticaltest.ui.TawkViewModelFactory
import com.facebook.shimmer.ShimmerFrameLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UsersActivity : AppCompatActivity() {

    private lateinit var viewModel: TawkViewModel

    private var shimmerLayout: ShimmerFrameLayout? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: UsersAdapter? = null
    private var search: EditText? = null
    private var searching = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this, TawkViewModelFactory()).get(TawkViewModel::class.java)
        adapter = UsersAdapter(this)

        initBindingResources()
        initViewModelObservers()

        viewModel.getUsers()
    }

    private fun initBindingResources() {
        shimmerLayout = findViewById(R.id.shimmerLayout)
        recyclerView = findViewById(R.id.recyclerView)
        search = findViewById(R.id.search)

        recyclerView?.adapter = adapter
        recyclerView?.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))
        recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // Checking scroll direction
                // 1 is down
                // -1 is up
                if (!recyclerView.canScrollVertically(1) && !searching) {
                    viewModel.getUsers(adapter?.items?.size?.toLong() ?: return)
                }
            }
        })

        search?.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            adapter?.items = emptyList()
            searching = hasFocus
            if (!hasFocus) {
                viewModel.getUsers()
            }
        }
        search?.addTextChangedListener {
            viewModel.getUsers(searchString = it.toString())
        }

        shimmerLayout?.startShimmer()
    }

    private fun initViewModelObservers() {
        viewModel.uiState.observe(this, { _uiState ->
            val uiState = _uiState ?: return@observe

            when (uiState) {
                TawkUiState.Loading -> TODO()

                is TawkUiState.UsersRetrieved -> updateUsers(uiState.users)
                is TawkUiState.SearchResults -> updateUsers(uiState.users)
                is TawkUiState.Error -> Log.d("TEST", uiState.message)
            }
        })
    }

    private fun updateUsers(users: List<GithubUser>) {
        if (!searching) {
            adapter?.items = adapter?.items?.plus(users)!!
        } else {
            adapter?.items = users
        }

        shimmerLayout?.stopShimmer()
        shimmerLayout?.visibility = View.GONE
    }

}