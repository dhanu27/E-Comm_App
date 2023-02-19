package com.myyour.e_comm_app.ui

import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.widget.AppCompatEditText
import com.myyour.e_comm_app.viewmodel.ProductViewModel

class ProductTextWatcher(private var editText: AppCompatEditText,
                         private val productViewModel: ProductViewModel
) : TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        TODO("Not yet implemented")
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        TODO("Not yet implemented")
    }

    override fun afterTextChanged(editable: Editable?) {
        val editedString:String = editable.toString()
        if (editedString.isEmpty()) {
            return
        }
        // remove textwatcher
        editText.removeTextChangedListener(this)
    }
}