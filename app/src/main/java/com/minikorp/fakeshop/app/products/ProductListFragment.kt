package com.minikorp.fakeshop.app.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.minikorp.fakeshop.R
import com.minikorp.fakeshop.shop.model.Product
import com.minikorp.fakeshop.util.viewModel
import kotlinx.android.synthetic.main.products_fragment.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import kotlin.properties.Delegates

class ProductListFragment : Fragment(), KodeinAware {

    override val kodein: Kodein
        get() = (requireActivity() as KodeinAware).kodein

    private val productAdapter = ProductAdapter()
    private val productListViewModel: ProductListViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.products_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        product_list.adapter = productAdapter
        product_list.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        productListViewModel.products.apply {
            val value = this.value
            if (value == null || value.isFailure) {
                productListViewModel.fetchProducts()
            }

            observe(this@ProductListFragment) {
                if (it.isSuccess) {
                    productAdapter.items = it.getOrThrow().products
                } else {
                    productAdapter.items = emptyList()
                }
            }
        }
    }

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.product_name)!!
        val price = view.findViewById<TextView>(R.id.product_price)!!
    }

    inner class ProductAdapter : RecyclerView.Adapter<ProductViewHolder>() {
        var items: List<Product> by Delegates.observable(emptyList()) { _, _, _ ->
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
            return ProductViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.products_item, parent, false)
            )
        }

        override fun getItemCount(): Int = items.size

        override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
            val product = items[position]
            holder.name.text = product.name
            holder.price.text = product.price.displayPrice
        }
    }
}