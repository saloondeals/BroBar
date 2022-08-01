package com.brobar.anpp

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.brobar.anpp.databinding.ActivitySignUpBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding
    val database = Firebase.database
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val myRef = database.getReference(users)
        binding.signUp.setOnClickListener {
            val query: Query = myRef.orderByChild("email").equalTo(binding.email.text.toString())
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        toast("Email already exist")
                    } else {
                        val user = User(
                            binding.name.text.toString(),
                            binding.email.text.toString(),
                            binding.password.text.toString(),
                            binding.email.text.toString() + ":" + binding.password.text.toString(),
                            0
                        )
                        myRef.push().setValue(user).addOnCompleteListener {
                            if (it.isSuccessful) {
                                toast("Account created Successfully")
                                finish()
                            }
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
        }
        binding.signIn.setOnClickListener {
            finish()
        }
    }

    companion object {
        private const val TAG = "SignUpActivity"
        const val users = "users"
        const val isLogin = "isLogin"
        fun Context.toast(msg: String) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }
}