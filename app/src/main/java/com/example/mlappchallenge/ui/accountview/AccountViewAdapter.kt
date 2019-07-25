package com.example.mlappchallenge.ui.accountview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mlappchallenge.R
import org.json.JSONArray
import org.json.JSONObject

class AccountViewAdapter :  RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var accounts : MutableList<Account> = mutableListOf()

    inner class AccountViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var accountId = itemView.findViewById<TextView>(R.id.acct_id)
        private var accountName = itemView.findViewById<TextView>(R.id.acct_name)
        private var accountNumber = itemView.findViewById<TextView>(R.id.acct_number)
        private var accountBalance = itemView.findViewById<TextView>(R.id.acct_balance)

        fun bind(account : Account) = with(itemView) {
            accountId.text = String.format(resources.getString(R.string.acct_id), account.id)
            accountName.text = String.format(resources.getString(R.string.acct_name), account.name)
            accountNumber.text = String.format(resources.getString(R.string.acct_number), account.number)
            accountBalance.text = String.format(resources.getString(R.string.acct_balance), account.balance)
        }
    }

    private fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View =
        LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return AccountViewHolder(p0.inflate(R.layout.account_view_holder))
    }

    override fun getItemCount(): Int {
        return accounts.size
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        (p0 as AccountViewHolder).bind(accounts[p1])
    }

    fun setAccounts(accountsJSON : JSONArray) {
        for (i in 0 until accountsJSON.length()) {
            val obj : JSONObject = accountsJSON[i] as JSONObject
            accounts.add(Account(obj.get("id") as Int, obj.get("display_name") as String, obj.get("account_number") as String,
                obj.get("balance") as Double
            ))
        }
    }

    fun getID(int: Int) : Int {
        return accounts[int].id
    }
}