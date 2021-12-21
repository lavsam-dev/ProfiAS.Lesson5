package lavsam.gb.profiaslesson5.di

import androidx.room.Room
import lavsam.gb.profiaslesson5.data.dataSource.local.VocabularyDataSourceLocalImpl
import lavsam.gb.profiaslesson5.data.dataSource.remote.VocabularyDataSourceRemoteImpl
import lavsam.gb.profiaslesson5.data.repository.local.RepositoryLocal
import lavsam.gb.profiaslesson5.data.repository.local.RepositoryLocalImpl
import lavsam.gb.profiaslesson5.data.repository.remote.RepositoryRemote
import lavsam.gb.profiaslesson5.data.repository.remote.RepositoryRemoteImpl
import lavsam.gb.profiaslesson5.data.room.HistoryDataBase
import lavsam.gb.profiaslesson5.domain.interactor.MainInteractor
import lavsam.gb.profiaslesson5.historyscreen.domain.interactor.HistoryInteractor
import lavsam.gb.profiaslesson5.historyscreen.presentation.viewModel.HistoryActivityViewModel
import lavsam.gb.profiaslesson5.model.VocabularyDataModel
import lavsam.gb.profiaslesson5.presentation.viewModel.MainActivityViewModel
import org.koin.dsl.module

private const val NAME_LOCAL_DB = "HistoryDB"

val application = module {

    single {
        Room.databaseBuilder(
            get(), HistoryDataBase::class.java, NAME_LOCAL_DB
        ).build()
    }

    single { get<HistoryDataBase>().historyDao() }

    single<RepositoryRemote<List<VocabularyDataModel>>> {
        RepositoryRemoteImpl(
            VocabularyDataSourceRemoteImpl()
        )
    }

    single<RepositoryLocal<List<VocabularyDataModel>>> {
        RepositoryLocalImpl(
            VocabularyDataSourceLocalImpl(historyDao = get())
        )
    }
}

val mainScreen = module {
    factory { MainActivityViewModel(mainInteractor = get()) }
    factory { MainInteractor(repositoryRemote = get(), repositoryLocal = get()) }
}

val historyScreen = module {
    factory { HistoryActivityViewModel(historyInteractor = get()) }
    factory { HistoryInteractor(repositoryRemote = get(), repositoryLocal = get()) }
}