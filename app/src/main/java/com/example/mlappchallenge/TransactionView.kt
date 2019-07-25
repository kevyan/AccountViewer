package com.example.mlappchallenge

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.mlappchallenge.ui.accountview.AccountViewFragment
import com.example.mlappchallenge.ui.transactionview.TransactionViewFragment

class TransactionView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.account_view_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, TransactionViewFragment.newInstance(intent.getIntExtra("ID", -1)))
                .commitNow()
        }
        MainActivity.curState = MainActivity.Companion.STATE.ACCT
    }
}
