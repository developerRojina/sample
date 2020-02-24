package com.demo.sample

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private lateinit var ratesAdapter: RatesAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val viewModel: RatesViewModel = ViewModelProviders.of(this).get(RatesViewModel::class.java)
        observeViewModel(viewModel)
    }


    private fun observeViewModel(viewModel: RatesViewModel) { // Update the list when the data changes
        viewModel.getRatesObservable().observe(
            this,
            Observer { rates ->
                if (rates.isNotEmpty()) {
                    val rateListRv=findViewById<RecyclerView>(R.id.rates_list_rv)

                    if (!::ratesAdapter.isInitialized) {
                        ratesAdapter=RatesAdapter(rates)
                        rateListRv.adapter = ratesAdapter
                    }else{
                        ratesAdapter.updateAdapter(rates)
                        ratesAdapter.notifyDataSetChanged()
                    }


                }
            })

        viewModel.getLoading().observe(
            this,
            Observer { shouldLoad->
                val progressBar=findViewById<ProgressBar>(R.id.load_rate_pb);

                if (shouldLoad)
                    progressBar.visibility=View.VISIBLE;
                else
                    progressBar.visibility=View.GONE;

            }
        )
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
