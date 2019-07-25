package com.example.mlappchallenge.ui.transactionview

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mlappchallenge.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

private const val ID = "ID"

class TransactionViewFragment : Fragment() {

    companion object {
        fun newInstance(id : Int) = TransactionViewFragment().apply {
            arguments = Bundle().apply {
                putInt(ID, id)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var id = -1
        if (arguments != null) {
            id = arguments!!.getInt(ID)
        }
        val viewFragment = inflater.inflate(R.layout.transaction_view_fragment, container, false)
        val recyclerView = viewFragment.findViewById<RecyclerView>(R.id.transaction_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = TransactionViewAdapter()
        val json: String? = rawJSONToString(resources.openRawResource(R.raw.account_transactions))
        var jsonArr = JSONArray(json)
        for (i in 0 until jsonArr.length()) {
            val obj = jsonArr[i] as JSONObject
            if (obj.has(id.toString())) {
                jsonArr = obj.getJSONArray(id.toString())
                break
            }
        }
        adapter.setTransactions(jsonArr)
        recyclerView.adapter = adapter
        return viewFragment
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//    }

}
