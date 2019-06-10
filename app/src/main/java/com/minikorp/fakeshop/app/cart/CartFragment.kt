package com.minikorp.fakeshop.app.cart

import android.annotation.SuppressLint
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.minikorp.fakeshop.R
import com.minikorp.fakeshop.app.BaseFragment
import com.minikorp.fakeshop.shop.model.cart.CartProduct
import com.minikorp.fakeshop.util.injectableViewModel
import kotlinx.android.synthetic.main.cart_fragment.*

class CartFragment : BaseFragment() {

    private val adapter = CartAdapter()
    private val cartViewModel: CartViewModel by injectableViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.cart_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        cart_list.adapter = adapter
        cart_list.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        cartViewModel.cart
            .observe(this) {
                adapter.updateItems(it.discountedProducts)
                //Hardcoded string concat, out of scope for demo
                @SuppressLint("SetTextI18n")
                cart_total.text = "Total - ${it.totalPrice().displayPrice}"
            }
    }

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.cart_product_name)!!
        val originalPrice = view.findViewById<TextView>(R.id.cart_product_original_price)!!.apply {
            paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }
        val discountedPrice = view.findViewById<TextView>(R.id.cart_product_price)!!
        val deleteButton = view.findViewById<View>(R.id.cart_product_delete)!!
    }

    inner class CartAdapter : RecyclerView.Adapter<CartViewHolder>() {
        private var items: List<CartProduct> = emptyList()

        fun updateItems(new: List<CartProduct>) {
            //With some basic animations that look nice
            val old = items
            val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int = old.size
                override fun getNewListSize(): Int = new.size
                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return old[oldItemPosition].id == new[newItemPosition].id
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return old[oldItemPosition] == new[newItemPosition]
                }
            })
            items = new
            diff.dispatchUpdatesTo(this)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
            return CartViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
            )
        }

        override fun getItemCount(): Int = items.size

        override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
            val cartProduct = items[position]
            val product = cartProduct.product
            holder.name.text = product.name
            holder.deleteButton.setOnClickListener {
                cartViewModel.removeProduct(cartProduct)
            }
            holder.discountedPrice.text = cartProduct.discountedPrice.displayPrice
            if (cartProduct.discountedPrice != product.price) {
                holder.originalPrice.visibility = View.VISIBLE //A discount is applied
                holder.originalPrice.text = product.price.displayPrice
            } else {
                holder.originalPrice.visibility = View.GONE
            }
        }
    }
}