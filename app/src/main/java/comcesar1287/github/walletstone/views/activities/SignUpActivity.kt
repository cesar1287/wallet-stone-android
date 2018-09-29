package comcesar1287.github.walletstone.views.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.widget.Toast
import comcesar1287.github.walletstone.R
import comcesar1287.github.walletstone.database.models.User
import comcesar1287.github.walletstone.preferences.MainPreference
import comcesar1287.github.walletstone.views.MainApp
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signInButton.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }

        signUpButton.setOnClickListener {
            val name = nameEdit.text.toString()
            val email = emailEdit.text.toString()
            val password = passwordEdit.text.toString()
            if (name.isBlank() || email.isBlank() || password.isBlank()) {
                Snackbar.make(signUpButton, getString(R.string.error_all_fields_required), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val userDao = MainApp.database?.userDao()

            userDao?.getUserByEmail(email)?.let { _ ->
                Snackbar.make(signUpButton, getString(R.string.error_user_already_registered), Snackbar.LENGTH_SHORT).show()
            } ?: run {
                val user = User(name = name, email = email, password = password)
                userDao?.insertUsers(user)
                MainPreference.setUserReference(this@SignUpActivity, email)
                startActivity(Intent(this@SignUpActivity, MainActivity::class.java))
                finish()
            }
        }
    }
}
