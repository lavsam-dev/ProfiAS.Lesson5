package lavsam.gb.profiaslesson5.historyscreen.presentation.view.activity

import android.os.Bundle
import lavsam.gb.profiaslesson5.core.presentation.view.activity.base.BaseActivity
import lavsam.gb.profiaslesson5.databinding.ActivityHistoryBinding
import lavsam.gb.profiaslesson5.historyscreen.domain.interactor.HistoryInteractor
import lavsam.gb.profiaslesson5.historyscreen.presentation.adapter.HistoryActivityAdapter
import lavsam.gb.profiaslesson5.historyscreen.presentation.viewModel.HistoryActivityViewModel
import lavsam.gb.profiaslesson5.model.AppState
import lavsam.gb.profiaslesson5.model.VocabularyDataModel
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val VIEWMODEL_INITIAL_FIRST = "The ViewModel should be initialised first"

class HistoryActivity : BaseActivity<AppState, HistoryInteractor>() {

    private lateinit var binding: ActivityHistoryBinding

    override lateinit var viewModel: HistoryActivityViewModel

    private val adapter: HistoryActivityAdapter by lazy { HistoryActivityAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        iniViewModel()
        initViews()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getData("", false)
    }

    override fun setDataToAdapter(data: List<VocabularyDataModel>) {
        adapter.setData(data)
    }

    private fun iniViewModel() {
        if (binding.historyActivityRecyclerview.adapter != null) {
            throw IllegalStateException(VIEWMODEL_INITIAL_FIRST)
        }
        val historyViewModel: HistoryActivityViewModel by viewModel()
        viewModel = historyViewModel
        viewModel.subscribe().observe(this@HistoryActivity, { renderData(it) })
    }

    private fun initViews() {
        binding.historyActivityRecyclerview.adapter = adapter
    }
}