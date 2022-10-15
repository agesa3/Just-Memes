package com.agesadev.funnymemes.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.agesadev.funnymemes.R
import com.agesadev.funnymemes.databinding.FragmentMemesBinding
import com.agesadev.funnymemes.domain.model.MemeDomain
import com.agesadev.funnymemes.presentation.model.Mapper
import com.agesadev.funnymemes.util.ItemClick
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import omari.hamza.storyview.StoryView
import omari.hamza.storyview.callback.StoryClickListeners
import omari.hamza.storyview.model.MyStory
import java.util.ArrayList


@AndroidEntryPoint
class MemesFragment : Fragment() {


    private var _binding: FragmentMemesBinding? = null
    private val binding get() = _binding
    private val memeList = arrayListOf<MyStory>()
    private val memeViewModel: MemesViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMemesBinding.inflate(inflater, container, false)
        binding?.button?.setOnClickListener {
            showStories()
        }
        binding?.apply {
            button.setOnClickListener {
                showStories()
            }
            asListButton.setOnClickListener {
                findNavController().navigate(R.id.listViewMemesFragment)
            }
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAndObserveMemes()
    }

    private fun getAndObserveMemes() {
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                memeViewModel.meme.collect { state ->
                    when {
                        state.data.isNotEmpty() -> {
                            val meme = Mapper.getMemeUrls(state.data)
                            memeList.addAll(meme.shuffled())
                            binding?.memeProgressBar?.visibility = View.GONE
                        }
                        state.error.isNotEmpty() -> {
                            binding?.memeProgressBar?.visibility = View.VISIBLE
                        }
                        state.isLoading -> {
                            binding?.memeProgressBar?.visibility = View.VISIBLE
                        }
                    }

                }
            }
        }
    }

    private fun showStories() {
        StoryView.Builder(requireActivity().supportFragmentManager)
            .setStoriesList(memeList as ArrayList<MyStory>?)
            .setStoryDuration(5000)
            .setStoryClickListeners(object : StoryClickListeners {
                override fun onDescriptionClickListener(position: Int) {
                }

                override fun onTitleIconClickListener(position: Int) {}
            })
            .setOnStoryChangedCallback {}
            .setStartingIndex(0).build().show()
    }

    private fun retry() {
        getAndObserveMemes()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}