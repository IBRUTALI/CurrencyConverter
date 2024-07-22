package com.ighorosipov.currencyconverter.features.converter.presentation.screens.select_currency.adapter

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.ighorosipov.currencyconverter.features.converter.domain.model.Currency


class CurrencyAdapter(
    private val context: Context,
    textViewResourceId: Int,
    values: MutableList<Currency>
) : ArrayAdapter<Currency>(context, textViewResourceId, values) {

    private val values: List<Currency>

    init {
        this.values = values
    }

    override fun getCount(): Int {
        return values.size
    }

    override fun getItem(position: Int): Currency {
        return values[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val label = super.getView(position, convertView, parent) as TextView
        label.setTextColor(Color.BLACK)
        label.text = values[position].code

        return label
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val label = super.getDropDownView(position, convertView, parent) as TextView
        label.setTextColor(Color.BLACK)
        label.text = values[position].code
        return label
    }
}