package com.minikorp.fakeshop.app.products

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.minikorp.fakeshop.R
import com.minikorp.fakeshop.app.BaseFragment
import com.minikorp.fakeshop.app.cart.CartViewModel
import com.minikorp.fakeshop.shop.model.Product
import com.minikorp.fakeshop.shop.model.Resource
import com.minikorp.fakeshop.util.injectableViewModel
import com.minikorp.fakeshop.util.toggleViewsVisibility
import kotlinx.android.synthetic.main.products_fragment.*

class ProductListFragment : BaseFragment() {

    private val adapter = ProductAdapter()
    private val productListViewModel: ProductListViewModel by injectableViewModel()
    private val cartViewModel: CartViewModel by injectableViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.products_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        product_list.adapter = adapter
        product_list.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        if (productListViewModel.products.value == null) {
            //Fetch first time, won't have effect if already loading
            productListViewModel.fetchProducts()
        }

        product_error_button.setOnClickListener {
            productListViewModel.fetchProducts()
        }

        productListViewModel.products.observe(this) {
            toggleViewsVisibility(it, product_list, product_progress_bar, product_error_button)
            when (it) {
                is Resource.Success -> adapter.updateItems(it.data!!.products)
                is Resource.Error -> product_error_button.text = it.message ?: "Unknown Error"
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_go_cart) {
            findNavController().navigate(R.id.action_productListFragment_to_cartFragment)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.product_name)!!
        val price = view.findViewById<TextView>(R.id.product_original_price)!!
        val buyButton = view.findViewById<View>(R.id.product_buy_button)!!
    }

    inner class ProductAdapter : RecyclerView.Adapter<ProductViewHolder>() {
        private var items: List<Product> = emptyList()

        fun updateItems(new: List<Product>) {
            items = new
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