package com.example.simplenewsapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.simplenewsapp.R
import com.example.simplenewsapp.ui.NewsActivity
import com.example.simplenewsapp.viewmodels.NewsViewModel
import kotlinx.android.synthetic.main.fragment_options.*

/**
 * Fragment used to select options such as news source
 */
class OptionsFragment : Fragment(R.layout.fragment_options) {

    lateinit var viewModel: NewsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).newsViewModel

        var countryCode = ""

        //Used to change news source country
        options_jp_icon.setOnClickListener {
            countryCode = "jp"
            save_options_fab.isEnabled = true
        }

        options_no_icon.setOnClickListener {
            countryCode = "no"
            save_options_fab.isEnabled = true
        }

        options_pl_icon.setOnClickListener {
            countryCode = "pl"
            save_options_fab.isEnabled = true
        }

        options_us_icon.setOnClickListener {
            countryCode = "us"
            save_options_fab.isEnabled = true
        }

        save_options_fab.setOnClickListener {
            viewModel.getNews(countryCode, 1)
        }
    }
}