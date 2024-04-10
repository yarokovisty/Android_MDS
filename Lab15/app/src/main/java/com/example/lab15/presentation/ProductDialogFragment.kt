package com.example.lab15.presentation

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.lab15.R
import com.example.lab15.databinding.DialogFragmentProductBinding
import com.example.lab15.domain.entity.ProductItem

class ProductDialogFragment : DialogFragment() {
    private var _binding: DialogFragmentProductBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel by lazy {
        ViewModelProvider(this)[ProductItemViewModel::class.java]
    }
    private var screenMode: String = MODE_UNKNOWN
    private var productItemId: Int = ProductItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = LayoutInflater.from(requireContext())
        val dialogView = inflater.inflate(R.layout.dialog_fragment_product, null)
        _binding = DialogFragmentProductBinding.bind(dialogView)
        val builder = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setPositiveButton(getString(R.string.ok)) { _, _ -> saveProduct() }
            .setNegativeButton(getString(R.string.cancel)) { _, _ -> }
        observerViewModel()
        launchRightMode()

        return builder.create()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseParams() {
        val args = requireArguments()

        if (!args.containsKey(SCREEN_MODE)) throw RuntimeException("Param screen mode is absent")

        val mode = args.getString(SCREEN_MODE)

        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode $mode")
        }

        screenMode = mode

        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(PRODUCT_ITEM_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            productItemId = args.getInt(PRODUCT_ITEM_ID, ProductItem.UNDEFINED_ID)
        }


    }

    private fun observerViewModel() {
        viewModel.productItem.observe(this@ProductDialogFragment) { product ->
            with(binding) {
                etThing.setText(product.name)
                etNumber.setText(product.number)
            }
        }
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_ADD -> {}
            MODE_EDIT -> viewModel.getProductItem(productItemId)
        }
    }


    private fun saveProduct() {
        val inputName = binding.etThing.text.toString()
        val inputNumber = binding.etNumber.text.toString()

        when (screenMode) {
            MODE_ADD -> viewModel.addProductItem(inputName, inputNumber)
            MODE_EDIT -> viewModel.editProductItem(inputName, inputNumber)
        }
    }


    companion object {
        const val TAG = "ProductDialogFragment"
        private const val MODE_UNKNOWN = ""
        private const val SCREEN_MODE = "screen_mode"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        private const val PRODUCT_ITEM_ID = "product_shop_item_id"


        fun newInstanceAddItem(): ProductDialogFragment {
            return ProductDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem(productItemId: Int): ProductDialogFragment {
            return ProductDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(PRODUCT_ITEM_ID, productItemId)
                }
            }
        }
    }
}