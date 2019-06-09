package com.minikorp.fakeshop.app.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.minikorp.fakeshop.R
import com.minikorp.fakeshop.app.BaseFragment
import com.minikorp.fakeshop.app.cart.CartViewModel
import com.minikorp.fakeshop.shop.model.Product
import com.minikorp.fakeshop.util.injectableViewModel
import kotlinx.android.synthetic.main.products_fragment.*
import kotlin.properties.Delegates

class ProductListFragment : BaseFragment() {

    private val adapter = ProductAdapter()
    private val productListViewModel: ProductListViewModel by injectableViewModel()
    private val cartViewModel: CartViewModel by injectableViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.products_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbar.menu.add("Cart")
            .apply {
                setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
                setIcon(R.drawable.ic_shopping_cart)
                setOnMenuItemClickListener {
                    findNavController().navigate(R.id.action_productListFragment_to_cartFragment)
                    true
                }
            }

        product_list.adapter = adapter
        product_list.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        productListViewModel.products.apply {
            val value = this.value
            if (value == null || value.isFailure) {
                productListViewModel.fetchProducts()
            }

            observe(this@ProductListFragment) {
                it.onSuccess { products ->
                    adapter.items = products.products
                }
                it.onFailure {
                    adapter.items = emptyList()
                }
            }
        }
    }

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.product_name)!!
        val price = view.findViewById<TextView>(R.id.product_original_price)!!
        val buyButton = view.findViewById<View>(R.id.product_buy_button)!!
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
            holder.buyButton.setOnClickListener {
                cartViewModel.addProduct(product)
            }
        }
    }
}