package com.example.mlappchallenge.ui.accountview

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mlappchallenge.*
import org.json.JSONArray
import java.io.IOException

class AccountViewFragment : Fragment() {

    companion object {
        fun newInstance() = AccountViewFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewFragment = inflater.inflate(R.layout.account_view_fragment, container, false)
        val recyclerView = viewFragment.findViewById<RecyclerView>(R.id.account_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = AccountViewAdapter()
        val json: String? = rawJSONToString(resources.openRawResource(R.raw.list_of_accounts))
        adapter.setAccounts(JSONArray(json))
        recyclerView.adapter = adapter

        recyclerView.addOnItemTouchListener(
            AccountViewItemClickListener(
                context!!,
                object : OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        val intent = Intent(activity, TransactionView::class.java)
                        intent.putExtra("ID", adapter.getID(position))
                        startActivity(intent)
                    }
                }))

        return viewFragment
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//    }

}
