package lavsam.gb.profiaslesson5.presentation.view.activity

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnticipateInterpolator
import androidx.annotation.RequiresApi
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import lavsam.gb.profiaslesson5.R
import lavsam.gb.profiaslesson5.core.presentation.view.activity.base.BaseActivity
import lavsam.gb.profiaslesson5.databinding.ActivityMainBinding
import lavsam.gb.profiaslesson5.domain.interactor.MainInteractor
import lavsam.gb.profiaslesson5.historyscreen.presentation.view.activity.HistoryActivity
import lavsam.gb.profiaslesson5.model.AppState
import lavsam.gb.profiaslesson5.model.VocabularyDataModel
import lavsam.gb.profiaslesson5.presentation.adapter.MainActivityAdapter
import lavsam.gb.profiaslesson5.presentation.view.fragment.SearchDialogFragment
import lavsam.gb.profiaslesson5.presentation.viewModel.MainActivityViewModel
import lavsam.gb.profiaslesson5.utils.convertMeaningsToString
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.random.Random

class MainActivity : BaseActivity<AppState, MainInteractor>() {

    private lateinit var binding: ActivityMainBinding

    override lateinit var viewModel: MainActivityViewModel

    private val adapter: MainActivityAdapter by lazy {
        MainActivityAdapter(onListItemClickListener)
    }

    private val fabClickListener: View.OnClickListener =
        View.OnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(onSearchClickListener)
            searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
        }

    private val onListItemClickListener: MainActivityAdapter.OnListItemClickListener =
        object : MainActivityAdapter.OnListItemClickListener {
            override fun onItemClick(data: VocabularyDataModel) {
                startActivity(
                    DescriptionActivity.getIntent(
                        this@MainActivity,
                        data.text!!,
                        convertMeaningsToString(data.meanings!!),
                        data.meanings!![0].imageUrl
                    )
                )
            }
        }

    private val onSearchClickListener: SearchDialogFragment.OnSearchClickListener =
        object : SearchDialogFragment.OnSearchClickListener {
            override fun onClick(searchWord: String) {
                if (isNetworkAvailable) {
                    viewModel.getData(searchWord, isNetworkAvailable)
                } else {
                    showNoInternetConnectionDialog()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDefaultSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        initViews()
    }

    override fun setDataToAdapter(data: List<VocabularyDataModel>) {
        adapter.setData(data)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.history_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                startActivity(Intent(this, HistoryActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initViewModel() {
        if (binding.mainActivityRecyclerview.adapter != null) {
            throw IllegalStateException(VIEWMODEL_INIT_FIRST)
        }
        val mainActivityViewModel: MainActivityViewModel by viewModel()
        viewModel = mainActivityViewModel
        viewModel.subscribe().observe(this@MainActivity, { renderData(it) })
    }

    private fun initViews() {
        with(binding) {
            searchFab.setOnClickListener(fabClickListener)
            mainActivityRecyclerview.layoutManager = LinearLayoutManager(applicationContext)
            mainActivityRecyclerview.adapter = adapter
        }
    }

    private fun setDefaultSplashScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (Random.nextBoolean()) {
                setSplashScreenAnimation()
            } else {
                setSplashScreenHideAnimation()
            }
        } else {
            setSplashScreenDuration()
        }
    }

    @RequiresApi(31)
    private fun setSplashScreenAnimation() {
        val splashScreen = installSplashScreen()
        splashScreen.setOnExitAnimationListener { splashScreenProvider ->
            ObjectAnimator.ofFloat(
                splashScreenProvider.view,
                View.TRANSLATION_Y,
                START_ANIMATION,
                -splashScreenProvider.view.height.toFloat()
            ).apply {
                interpolator = AnticipateInterpolator()
                duration = SLIDE_UP_DURATION
                doOnEnd { splashScreenProvider.remove() }
            }.start()
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun setSplashScreenHideAnimation() {
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            val slideLeft = ObjectAnimator.ofFloat(
                splashScreenView,
                View.TRANSLATION_X,
                0f,
                -splashScreenView.height.toFloat()
            )
            slideLeft.interpolator = AnticipateInterpolator()
            slideLeft.duration = SLIDE_LEFT_DURATION

            slideLeft.doOnEnd { splashScreenView.remove() }
            slideLeft.start()
        }
    }

    private fun setSplashScreenDuration() {
        var isHideSplashScreen = false

        object : CountDownTimer(COUNTDOWN_DURATION, COUNTDOWN_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                isHideSplashScreen = true
            }
        }.start()

        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if (isHideSplashScreen) {
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        false
                    }
                }
            }
        )
    }

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG = "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"
        private const val VIEWMODEL_INIT_FIRST = "The ViewModel should be initialised first"
        private const val SLIDE_UP_DURATION = 1500L
        private const val START_ANIMATION = 0f
        private const val SLIDE_LEFT_DURATION = 1000L
        private const val COUNTDOWN_DURATION = 2000L
        private const val COUNTDOWN_INTERVAL = 1000L
    }
}

