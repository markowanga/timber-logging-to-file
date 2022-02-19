package pl.theliver.timberloggingtofileapp

import androidx.multidex.MultiDexApplication
import pl.theliver.timberloggingtofile.LogManager
import pl.theliver.timberloggingtofile.LogToFileTimberTree
import timber.log.Timber


class TimberLoggingToFileApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        initLogger()
    }

    private fun initLogger() {
        Timber.plant(Timber.DebugTree())
        Timber.plant(
            LogToFileTimberTree(LogManager.getExternalLogsDirectory(this))
        )
        Timber.i("Hello log ;) !!!")
    }

}
