package com.example.mlappchallenge.ui.transactionview

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mlappchallenge.R
import org.json.JSONArray
import org.json.JSONObject

class TransactionViewAdapter :  RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var transactions : MutableList<Transaction> = mutableListOf()

    inner class AccountViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var txnId = itemView.findViewById<TextView>(R.id.txn_id)
        private var txnDescription = itemView.findViewById<TextView>(R.id.txn_description)
        private var txnDate = itemView.findViewById<TextView>(R.id.txn_date)
        private var txnDepositAmount = itemView.findViewById<TextView>(R.id.txn_deposit_amount)
        private var txnBalance = itemView.findViewById<TextView>(R.id.txn_balance)

        fun bind(txn : Transaction) = with(itemView) {
            txnId.text = String.format(resources.getString(R.string.txn_id), txn.id)
            txnDescription.text = String.format(resources.getString(R.string.txn_description), txn.description)
            txnDate.text = String.format(resources.getString(R.string.txn_date), txn.date)
            when (txn.amount >= 0) {
                true -> {
                    txnDepositAmount.text = String.format(resources.getString(R.string.txn_deposit_amount), txn.amount)
                    itemView.setBackgroundColor(Color.parseColor("#a4c639"))
                }
                false -> {
                    txnDepositAmount.text = String.format(resources.getString(R.string.txn_withdraw_amount), txn.amount * -1.0)
                    itemView.setBackgroundColor(Color.parseColor("#ffcccb"))
                }
            }
            txnBalance.text = String.format(resources.getString(R.string.txn_balance), txn.balance)
        }
    }

    private fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View =
        LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return AccountViewHolder(p0.inflate(R.layout.transaction_view_holder))
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        (p0 as AccountViewHolder).bind(transactions[p1])
    }

    fun setTransactions(accountsJSON : JSONArray) {
        for (i in 0 until accountsJSON.length()) {
            val activities : JSONArray = (accountsJSON[i] as JSONObject).getJSONArray("activity")
            for (j in 0 until activities.length()) {
                val obj : JSONObject = activities[j] as JSONObject
                if (obj.has("deposit_amount")) {
                    transactions.add(
                        Transaction(
                            obj.get("id") as Int, obj.get("date") as String, obj.get("description") as String,
                            obj.get("deposit_amount") as Double, obj.get("balance") as Double
                        )
                    )
                } else {
                    transactions.add(
                        Transaction(
                            obj.get("id") as Int, obj.get("date") as String, obj.get("description") as String,
                            (obj.getString("withdrawal_amount").toDouble()) * -1.0, obj.get("balance") as Double
                        )
                    )
                }
            }
            transactions.sortBy { it.date }
        }
    }

}