package com.example.myapplication

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.Settings
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            btnOpenSettings.setOnClickListener {
                newIntentSettings()
                screenTry.isVisible = false
                screenContacts.isVisible = true
            }
            btnDeny.setOnClickListener {
                finish()
            }
            btnGetContacts.setOnClickListener {
                getContacts()
            }

        }

    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_ACCESS_READ_CONTACTS) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getContacts()
            } else {
                with(binding) {
                    screenContacts.isVisible = false
                    screenTry.isVisible = true
                }
            }
        }
    }

    private fun getContacts() {
        if (checkPermission()) {
            val result = mutableListOf<String>()
            // Запрос списка контактов
            val cur = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null
            )
            if (cur != null) {
                // Определение номера столбца, содержащего имя контакта
                val colName = cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                while (cur.moveToNext()) {
                    val name = cur.getString(colName)
                    result.add(name)
                }
                cur.close()
            }

            binding.tvContacts.text = result.joinToString("\n")

        } else {
            requestPermission()
        }
    }

    private fun checkPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_CONTACTS),
            PERMISSION_REQUEST_ACCESS_READ_CONTACTS
        )
    }

    private fun newIntentSettings() {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:$packageName")
        )
        startActivity(intent)
    }

    companion object {
        private const val PERMISSION_REQUEST_ACCESS_READ_CONTACTS = 69
    }
}