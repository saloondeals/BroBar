package com.brobar.anpp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.brobar.anpp.SignUpActivity.Companion.toast
import com.brobar.anpp.databinding.ActivitySignInBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.pixplicity.easyprefs.library.Prefs

class SignInActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignInBinding
    val database = Firebase.database
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val myRef = database.getReference(SignUpActivity.users)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.signIn.setOnClickListener {
            val query: Query = myRef.orderByChild("emailPassword")
                .equalTo(binding.email.text.toString() + ":" + binding.password.text.toString())
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Prefs.putBoolean(SignUpActivity.isLogin, true)
                        startActivity(Intent(this@SignInActivity, MainActivity::class.java))
                        finishAffinity()
                    } else {
                        toast("Account not found")
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
        }
        binding.signUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}