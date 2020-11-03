package com.example.tawkpracticaltest.ui.profile

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.tawkpracticaltest.R
import com.example.tawkpracticaltest.data.models.GithubUser
import com.example.tawkpracticaltest.data.models.GithubUserProfile
import com.example.tawkpracticaltest.ui.TawkUiState
import com.example.tawkpracticaltest.ui.TawkViewModel
import com.example.tawkpracticaltest.ui.TawkViewModelFactory
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.android.synthetic.main.activity_profile.*

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
    private var user: GithubUser? = null
    private var notes: TextView? = null



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
            user = intent.getParcelableExtra(EXTRA_USER)

            viewModel.getUserProfile(user?.login ?: return)
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
        notes = findViewById(R.id.notes)
        notesEditText = findViewById(R.id.notesEditText)
        save = findViewById(R.id.save)
        profileShimmerLayout = findViewById(R.id.profileShimmerLayout)
        profileLayout = findViewById(R.id.profile)

        profileLayout?.visibility = View.GONE
        profileShimmerLayout?.startShimmer()
    }


    private fun initBindingEvents() {
        notesEditText?.addTextChangedListener {
            save?.isEnabled = true
            user?.notes = notesEditText!!.text.toString()
        }
        save?.setOnClickListener {
            user?.let {
                viewModel.updateUser(it)
            }
        }
    }

    private fun initViewModelObservers() {
        viewModel.uiState.observe(this, { _uiState ->
            val uiState = _uiState ?: return@observe

            when (uiState) {
                is TawkUiState.UserProfileReceived -> updateUserProfile(uiState.profile)
                is TawkUiState.UserRetrieved -> {
                    user = uiState.user
                    notes?.text = user?.notes
                }

                is TawkUiState.UserUpdated -> {
                    val resultIntent = Intent()
                    resultIntent.putExtra(EXTRA_USER, user)

                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                }

                else -> {}
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

    @SuppressLint("SetTextI18n")
    private fun updateUserProfile(profile: GithubUserProfile) {

        avatar?.load(profile.avatarUrl)
        title?.text = profile.name
        followers?.text = "Followers: " + profile.followers.toString()
        following?.text = "Following: " + profile.following.toString()
        name?.text = "Name: " + profile.name
        company?.text = "Company: " + profile.company
        blog?.text = "Blog: " + profile.blog
        notesEditText?.setText(user?.notes)

        profileLayout?.visibility = View.VISIBLE
        profileShimmerLayout?.stopShimmer()
        profileShimmerLayout?.visibility = View.GONE
    }

    companion object {
        const val EXTRA_USER = "EXTRA_USER"
        const val REQUEST_CODE = 1000
    }


}