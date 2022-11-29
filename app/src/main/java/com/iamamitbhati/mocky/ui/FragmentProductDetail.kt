package com.iamamitbhati.mocky.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.iamamitbhati.mocky.databinding.FragmentProductDetailBinding
import com.iamamitbhati.mocky.extension.getFavDrawable
import com.iamamitbhati.mocky.extension.setImage
import com.iamamitbhati.mocky.extension.updateTitle
import com.iamamitbhati.mocky.model.ProductDetail
import com.iamamitbhati.mocky.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FragmentProductDetail : DialogFragment() {
    private lateinit var binding: FragmentProductDetailBinding
    private val args by navArgs<FragmentProductDetailArgs>()
    private val detailViewModel: DetailViewModel by viewModels()
    private var id: Long? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.updateTitle("Detail")

        id = args.productId
        id?.let {
            detailViewModel.getProductDetail(it)
        }

        setupObserver()
    }

    private fun setupObserver() {
        detailViewModel.stateChange.observe(viewLifecycleOwner) {
            it?.let { product ->
                setValues(product)
            }
        }
    }

    private fun setValues(productDetail: ProductDetail) {
        with(binding) {
            name.text = productDetail.title
            image.setImage(root.context,productDetail.imageURL)
            ratingBar.rating = productDetail.ratingCount
            review.text = String.format("(%d)", productDetail.totalReviewCount)
            price.text= String.format("$ %.2f", productDetail.price)

            binding.favImage.setImageDrawable(
                productDetail.getFavDrawable(requireContext())
            )

            binding.favImage.setOnClickListener {
                productDetail.favorite= !productDetail.favorite
                detailViewModel.setUnFavorite(id, productDetail.favorite)

                binding.favImage.setImageDrawable(
                    productDetail.getFavDrawable(requireContext())
                )

            }
        }
    }
}