package com.example.tawkpracticaltest.ui.users

import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.exam.tawk.ui.users.UsersAdapter
import com.example.tawkpracticaltest.R
import com.example.tawkpracticaltest.data.models.GithubUser
import com.example.tawkpracticaltest.ui.TawkUiState
import com.example.tawkpracticaltest.ui.TawkViewModel
import com.example.tawkpracticaltest.ui.TawkViewModelFactory
import com.example.tawkpracticaltest.ui.profile.ProfileActivity
import com.example.tawkpracticaltest.util.ConnectivityChecker
import com.facebook.shimmer.ShimmerFrameLayout

class UsersActivity : AppCompatActivity() {

    private lateinit var viewModel: TawkViewModel

    private var shimmerLayout: ShimmerFrameLayout? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: UsersAdapter? = null
    private var search: EditText? = null
    private var searching = false
    private var loading = false

    private var isNetworkAvailable = true
    private var connectivityManager: ConnectivityManager? = null
    private var connectivityChecker: ConnectivityChecker? = null

    private var progressCircular: ProgressBar? = null

    private val pagination = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            // Checking scroll direction
            // 1 is down
            // -1 is up
            if (!recyclerView.canScrollVertically(1) && !searching && !loading) {
                viewModel.getUsers(adapter?.items?.size?.toLong() ?: return)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this, TawkViewModelFactory()).get(TawkViewModel::class.java)
        adapter = UsersAdapter(this)

        initBindingResources()
        initViewModelObservers()
        initializeConnectivityChecker()

        viewModel.getUsers()
    }

    override fun onResume() {
        super.onResume()
        connectivityChecker?.startMonitoringConnectivity()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ProfileActivity.REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                data?.getParcelableExtra<GithubUser>(ProfileActivity.EXTRA_USER)?.let {
                    adapter?.items = ArrayList()
                    shimmerLayout?.startShimmer()
                    viewModel.getUsers()
                }
            }
        }
    }

    override fun onPause() {
        recyclerView?.removeOnScrollListener(pagination)
       // _connectivityChecker?.stop
        super.onPause()
    }

    private fun initializeConnectivityChecker() {
        connectivityManager = ContextCompat.getSystemService(this, ConnectivityManager::class.java)
        if (connectivityManager != null) {
            connectivityChecker =
                ConnectivityChecker(connectivityManager!!)

            connectivityChecker?.apply {
                connectedStatus.observe(this@UsersActivity, { connected ->
                    Handler(mainLooper).postDelayed({
                        if(connected) {
                            handleNetworkConnected()
                            Toast.makeText(this@UsersActivity, "Internet Connection Active!", Toast.LENGTH_SHORT).show()
                        } else {
                            handleNetworkConnectivity()
                            Toast.makeText(this@UsersActivity, "No Internet Connection!", Toast.LENGTH_SHORT).show()
                        }
                    }, 1500)
                })
            }
        }
    }

    fun handleNetworkConnectivity()  {
        isNetworkAvailable = false
        //TODO
    }

    fun handleNetworkConnected() {
        isNetworkAvailable = true
    }

    private fun initBindingResources() {
        shimmerLayout = findViewById(R.id.shimmerLayout)
        recyclerView = findViewById(R.id.recyclerView)
        search = findViewById(R.id.search)
        progressCircular = findViewById(R.id.progress_circular)

        recyclerView?.adapter = adapter
        recyclerView?.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))


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
                TawkUiState.Loading -> {
                    progressCircular?.visibility = View.VISIBLE
                    loading = true
                }

                is TawkUiState.UsersRetrieved -> updateUsers(uiState.users)
                is TawkUiState.SearchResults -> updateUsers(uiState.users)
                is TawkUiState.Error -> Log.d("TEST", uiState.message)
            }
        })
    }

    private fun updateUsers(users: List<GithubUser>) {
        loading = false
        progressCircular?.visibility = View.GONE
        if (!searching) {
            adapter?.items = adapter?.items?.plus(users)!!
        } else {
            adapter?.items = users
        }

        shimmerLayout?.stopShimmer()
        shimmerLayout?.visibility = View.GONE

        recyclerView?.addOnScrollListener(pagination)
    }

}