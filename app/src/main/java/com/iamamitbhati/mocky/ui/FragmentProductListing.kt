package com.iamamitbhati.mocky.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.iamamitbhati.mocky.MainActivity
import com.iamamitbhati.mocky.R
import com.iamamitbhati.mocky.databinding.FragmentProductListingBinding
import com.iamamitbhati.mocky.extension.updateTitle
import com.iamamitbhati.mocky.model.ProductDetail
import com.iamamitbhati.mocky.repository.Resource
import com.iamamitbhati.mocky.ui.adapter.ProductAdapter
import com.iamamitbhati.mocky.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FragmentProductListing : Fragment() {
    private lateinit var binding: FragmentProductListingBinding
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter
    private var productList = ArrayList<ProductDetail>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductListingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.updateTitle(getString(R.string.app_name))

        getProducts()
        setupView()
        setupObserver()
    }

    private fun setupView() {
        with(binding) {
            productAdapter = ProductAdapter(productList, { product, isFav, position ->
                mainViewModel.setFavoriteUnFavorite(product.id, isFav)
                productAdapter.notifyItemChanged(position)
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
        mainViewModel.stateChange.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.progressbar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressbar.visibility = View.GONE
                    it.data?.let { list ->
                        productList.clear()
                        productList.addAll(list)
                    }
                    notifyAdapter()
                }
                is Resource.Failed -> {
                    onRetrieveListError(it.errorMessage)
                }
            }
        }
    }

    private fun getProducts() {
        mainViewModel.getProducts()
    }

    /**
     * In order to update complete list
     */
    @SuppressLint("NotifyDataSetChanged")
    private fun notifyAdapter() {
        productAdapter.notifyDataSetChanged()
    }


    private fun onRetrieveListError(errorMessage: String?) {
        val showMessage = errorMessage ?: getString(R.string.post_error)
        val errorSnackbar =
            Snackbar.make(binding.recyclerView, showMessage, Snackbar.LENGTH_LONG)
        errorSnackbar.setAction(R.string.retry, errorClickListener)
        errorSnackbar.show()
    }

    private val errorClickListener = View.OnClickListener { getProducts() }

}