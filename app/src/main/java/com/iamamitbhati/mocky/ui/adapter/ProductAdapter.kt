package com.iamamitbhati.mocky.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iamamitbhati.mocky.R
import com.iamamitbhati.mocky.databinding.ItemListBinding
import com.iamamitbhati.mocky.extension.getFavDrawable
import com.iamamitbhati.mocky.extension.setImage
import com.iamamitbhati.mocky.model.ProductDetail


class ProductAdapter(
    private val productList: ArrayList<ProductDetail>,
    var onFavClick: ((ProductDetail, Boolean, Int) -> Unit)? = null,
    var onItemClick: ((ProductDetail) -> Unit)? = null
) : RecyclerView.Adapter<ProductAdapter.AdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListBinding.inflate(inflater, parent, false)
        return AdapterViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product, onFavClick, onItemClick)
    }

    class AdapterViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            product: ProductDetail,
            onFavClick: ((ProductDetail, Boolean, Int) -> Unit)?,
            onItemClick: ((ProductDetail) -> Unit)?
        ) {
            with(binding) {
                name.text = product.title
                image.setImage(root.context, product.imageURL)
                root.setOnClickListener {
                    onItemClick?.invoke(product)
                }

                favImage.setOnClickListener {
                    onFavClick?.invoke(product, !product.favorite, adapterPosition)
                    product.favorite = !product.favorite
                }

                price.text = String.format("$ %.2f", product.price)
                val isFavDrawable = product.getFavDrawable(root.context)
                favImage.setImageDrawable(isFavDrawable)
            }

        }
    }
}