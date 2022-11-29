package com.iamamitbhati.mocky.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.iamamitbhati.mocky.R
import com.iamamitbhati.mocky.databinding.FragmentProductListingBinding
import com.iamamitbhati.mocky.extension.setVisibility
import com.iamamitbhati.mocky.extension.updateTitle
import com.iamamitbhati.mocky.model.ProductDetail
import com.iamamitbhati.mocky.ui.adapter.ProductAdapter
import com.iamamitbhati.mocky.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FragmentFavorite : Fragment() {
    private lateinit var binding: FragmentProductListingBinding
    private lateinit var productAdapter: ProductAdapter
    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private var favoriteList = ArrayList<ProductDetail>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductListingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.updateTitle("Favorite")
        requireActivity().actionBar
        setupView()
        setupObserver()
    }

    private fun setupView() {
        with(binding) {
            productAdapter = ProductAdapter(favoriteList, { product, isFav, position ->
                favoriteViewModel.setFavoriteUnFavorite(product.id, isFav)
                favoriteList.removeAt(position)
                notifyAdapter()
                favoriteList.takeIf { it.isEmpty() }?.let {
                    emptyText.setVisibility(visible = true)
                }
            }) { product ->
                val directions = FragmentProductListingDirections.navigateToProductDetail(
                    product.id
                )
                findNavController().navigate(directions)
            }
            recyclerView.adapter = productAdapter

        }
    }

    private fun setupObserver() {
        favoriteViewModel.getFavProducts()
        favoriteViewModel.stateChange.observe(viewLifecycleOwner) { productList ->
            with(binding) {
                progressbar.setVisibility(false)
                productList?.let { list ->
                    list.takeIf { it.isNotEmpty() }?.let {
                        emptyText.setVisibility(false)
                        favoriteList.clear()
                        favoriteList.addAll(it)
                    } ?: kotlin.run {
                        favoriteList.clear()
                        emptyText.setVisibility(true)
                        emptyText.setText(R.string.fav_empty_list)
                    }
                }
            }
            notifyAdapter()
        }
    }


    /**
     * In order to update list
     */
    @SuppressLint("NotifyDataSetChanged")
    private fun notifyAdapter() {
        productAdapter.notifyDataSetChanged()
    }
}