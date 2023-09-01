package com.odukabdulbasit.asynctask

import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.odukabdulbasit.asynctask.databinding.ActivityMainBinding
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Button click event to start the AsyncTask
        binding.startTaskButton.setOnClickListener {
            it.isEnabled = false
            binding.progressBar.visibility = View.VISIBLE
            val task = MyAsyncTask(this)
            task.execute()
        }
    }

    // Define your AsyncTask class
    private class MyAsyncTask(context: MainActivity) : AsyncTask<Void, Void, String>() {

        private val activityReference: WeakReference<MainActivity> = WeakReference(context)

        override fun doInBackground(vararg params: Void?): String {
            // Simulate a background task, e.g., fetching data from a server
            Thread.sleep(5000) // Simulate a delay of 5 seconds
            return "Task completed"
        }

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
            val activity = activityReference.get()
            activity?.updateUI(result)
        }
    }

    // Update UI after AsyncTask completion
    fun updateUI(result: String) {
        binding.startTaskButton.isEnabled = true
        binding.progressBar.visibility = View.GONE
        binding.resultTextView.text = result
    }
}
