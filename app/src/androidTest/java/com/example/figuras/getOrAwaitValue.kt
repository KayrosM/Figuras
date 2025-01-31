import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.test.platform.app.InstrumentationRegistry
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

fun <T> LiveData<T>.getOrAwaitValue(time: Long = 2, timeUnit: TimeUnit = TimeUnit.SECONDS): T {
    var data: T? = null
    val latch = CountDownLatch(1)

    val observer = object : Observer<T> {
        override fun onChanged(value: T) {
            data = value
            this@getOrAwaitValue.removeObserver(this)
            latch.countDown()
        }
    }

    // Asegurar que observeForever se ejecute en el hilo principal
    if (Looper.myLooper() == Looper.getMainLooper()) {
        this.observeForever(observer)
    } else {
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            this.observeForever(observer)
        }
    }

    try {
        if (!latch.await(time, timeUnit)) {
            throw TimeoutException("LiveData value was never set.")
        }
    } finally {
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            this.removeObserver(observer)
        }
    }

    return data ?: throw NullPointerException("LiveData returned null value")
}


