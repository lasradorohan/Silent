package com.compultra.silent

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.compultra.silent.databinding.ActivityPermissionsBinding

class PermissionsActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityPermissionsBinding

    private lateinit var permissionsList: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityPermissionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        permissionsList = intent?.extras?.getStringArrayList(PERMISSIONS_EXTRA)?.toArray(arrayOf<String>()) ?: arrayOf()


        binding.buttonGrant.setOnClickListener {
            ActivityCompat.requestPermissions(this, this.permissionsList, 0)
        }
//
//        binding.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        refreshPendingPermissions()
    }

    override fun onResume() {
        super.onResume()
        refreshPendingPermissions()
    }

    fun refreshPendingPermissions() {
        val pendingPermissions = permissionsList.filterPendingPermissions(this)
        if (pendingPermissions.isEmpty()) finish()
        permissionsList = pendingPermissions
        binding.permissionsList.text = pendingPermissions.joinToString(separator = "\n")
    }

    companion object {
        const val PERMISSIONS_EXTRA = "permissions"

        @JvmStatic
        fun start(context: Context, permissions: ArrayList<String>) {
            val intent = Intent(context, PermissionsActivity::class.java)
            intent.putStringArrayListExtra(PERMISSIONS_EXTRA, ArrayList(permissions))
            context.startActivity(intent)
        }
    }
}

fun Array<String>.filterPendingPermissions(context: Context) = filter {
    ActivityCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED
}.toTypedArray()