package com.example.tawkpracticaltest.ui.profile

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.tawkpracticaltest.R
import com.example.tawkpracticaltest.data.models.GithubUserProfile
import com.example.tawkpracticaltest.ui.TawkUiState
import com.example.tawkpracticaltest.ui.TawkViewModel
import com.example.tawkpracticaltest.ui.TawkViewModelFactory
import com.facebook.shimmer.ShimmerFrameLayout

class ProfileActivity : AppCompatActivity() {

    private lateinit var viewModel: TawkViewModel

    private var avatar: ImageView? = null
    private var toolbar: Toolbar? = null
    private var title: TextView? = null
    private var following: TextView? = null
    private var followers: TextView? = null
    private var name: TextView? = null
    private var company: TextView? = null
    private var blog: TextView? = null
    private var notesEditText: EditText? = null
    private var save: Button? = null
    private var profileShimmerLayout: ShimmerFrameLayout? = null
    private var profileLayout: LinearLayout? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        viewModel = ViewModelProvider(this, TawkViewModelFactory()).get(TawkViewModel::class.java)

        initViewModelObservers()
        initParcels()
        initBindingResources()
        initBindingEvents()

        setupToolbar()
    }

    private fun initParcels() {
        if (intent.hasExtra(EXTRA_USER)) {
            viewModel.getUserProfile(intent.getStringExtra(EXTRA_USER) ?: return)
        }
    }

    private fun initBindingResources() {
        avatar = findViewById(R.id.avatar)
        toolbar = findViewById(R.id.toolbar)
        title = findViewById(R.id.title)
        followers = findViewById(R.id.followers)
        following = findViewById(R.id.following)
        name = findViewById(R.id.name)
        company = findViewById(R.id.company)
        blog = findViewById(R.id.blog)
        notesEditText = findViewById(R.id.notes)
        save = findViewById(R.id.save)
        profileShimmerLayout = findViewById(R.id.profileShimmerLayout)
        profileLayout = findViewById(R.id.profile)

        profileLayout?.visibility = View.GONE
        profileShimmerLayout?.startShimmer()
    }


    private fun initBindingEvents() {
        notesEditText?.addTextChangedListener {
            save?.isEnabled = true
        }

        save?.setOnClickListener {
        }
    }

    private fun initViewModelObservers() {
        viewModel.uiState.observe(this, { _uiState ->
            val uiState = _uiState ?: return@observe

            if (uiState is TawkUiState.UserProfileReceived) {
                updateUserProfile(uiState.profile)
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onNavigateUp(): Boolean {
        onBackPressed()
        return super.onNavigateUp()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
    }

    private fun updateUserProfile(profile: GithubUserProfile) {

        avatar?.load(profile.avatarUrl)
        title?.text = profile.name
        followers?.text = "Followers: " + profile.followers.toString()
        following?.text = "Following: " + profile.following.toString()
        name?.text = "Name: " + profile.name
        company?.text = "Company: " + profile.company
        blog?.text = "Blog: " + profile.blog

        profileLayout?.visibility = View.VISIBLE
        profileShimmerLayout?.stopShimmer()
        profileShimmerLayout?.visibility = View.GONE
    }

    companion object {
        const val EXTRA_USER = "EXTRA_USER"
    }


}