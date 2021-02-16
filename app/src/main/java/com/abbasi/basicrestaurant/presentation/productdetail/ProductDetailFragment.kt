package com.abbasi.basicrestaurant.presentation.productdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.abbasi.basicrestaurant.R
import com.abbasi.basicrestaurant.databinding.FragmentProductDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    private val viewModel: ProductDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentProductDetailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_product_detail, container, false
        )
        setupUI(binding)
        return binding.root
    }

    private fun setupUI(binding: FragmentProductDetailBinding) {

        val args: ProductDetailFragmentArgs by navArgs()
        viewModel.initialize(args.productId, args.categoryId)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
    }
}