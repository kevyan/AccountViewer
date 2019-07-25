package com.example.mlappchallenge

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button

import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast
import kotlinx.android.synthetic.main.content_main.*
import android.content.SharedPreferences



private const val CUR_STATE = "mCurState"
private const val PREFS_NAME = "MyPrefsFile"

class MainActivity : AppCompatActivity() {


    companion object {
        enum class STATE {
            LNDG, ACCT, TXN, ERR
        }

        lateinit var curState : STATE

        private fun getState(i : Int?) : STATE {
            return when (i) {
                0 -> Companion.STATE.LNDG
                1 -> Companion.STATE.ACCT
                2 -> Companion.STATE.TXN
                else -> Companion.STATE.ERR
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val settings = getSharedPreferences(PREFS_NAME, 0)
        curState = getState(settings.getInt(CUR_STATE, 0))

        button_open.setOnClickListener {
            openAccountListView()
            Toast.makeText(this, "Account Page Coming Up", Toast.LENGTH_SHORT).show()
        }

        if (curState == Companion.STATE.ACCT) {
            openAccountListView()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        val icon = menu.findItem(R.id.action_quit)
        icon.isVisible = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_quit -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        curState = Companion.STATE.LNDG
    }

    override fun onStop() {
        super.onStop()
        val settings = getSharedPreferences(PREFS_NAME, 0)
        val editor = settings.edit()
        editor.putInt(CUR_STATE, curState.ordinal)
        editor.apply()
    }

    private fun openAccountListView() {
        val intent = Intent(this, AccountView::class.java)
        startActivity(intent)
    }
}
