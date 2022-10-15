package com.agesadev.funnymemes.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.agesadev.funnymemes.databinding.FragmentListViewMemesBinding
import com.agesadev.funnymemes.presentation.model.Mapper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import omari.hamza.storyview.model.MyStory

@AndroidEntryPoint
class ListViewMemesFragment : Fragment() {


    private var _listBinding: FragmentListViewMemesBinding? = null
    private val listBinding get() = _listBinding

    private val memeList = arrayListOf<MyStory>()
    private val memesListAdapter: MemesListAdapter = MemesListAdapter()
    private val memeViewModel: MemesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _listBinding = FragmentListViewMemesBinding.inflate(layoutInflater, container, false)
        setUpRecycler()
        return listBinding?.root
    }

    private fun setUpRecycler() {
        listBinding?.memesRecyclerView?.apply {
            adapter = memesListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
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
                            memesListAdapter.submitList(state.data.shuffled())
                        }
                        state.error.isNotEmpty() -> {

                        }
                        state.isLoading -> {

                        }
                    }

                }
            }
        }
    }


}