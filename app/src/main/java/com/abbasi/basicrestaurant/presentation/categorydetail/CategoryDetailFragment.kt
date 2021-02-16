package com.abbasi.basicrestaurant.presentation.categorydetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.abbasi.basicrestaurant.R
import com.abbasi.basicrestaurant.databinding.FragmentCategoryDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryDetailFragment : Fragment() {

    private val viewModel: CategoryDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentCategoryDetailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_category_detail, container, false
        )
        setupUI(binding)
        return binding.root
    }

    private fun setupUI(binding: FragmentCategoryDetailBinding) {

        val args: CategoryDetailFragmentArgs by navArgs()
        viewModel.initialize(args.categoryId, args.categoryName)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
    }

}
