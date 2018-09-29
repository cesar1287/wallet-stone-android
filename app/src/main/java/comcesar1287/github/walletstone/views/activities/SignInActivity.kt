package comcesar1287.github.walletstone.views.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import comcesar1287.github.walletstone.R
import comcesar1287.github.walletstone.preferences.MainPreference
import comcesar1287.github.walletstone.views.MainApp
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        signUpButton.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }

        signInButton.setOnClickListener {
            val email = emailEdit.text.toString()
            val password = passwordEdit.text.toString()

            if (email.isBlank() || password.isBlank()) {
                Snackbar.make(signInButton, getString(R.string.error_all_fields_required), Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            MainApp.database?.userDao()?.getUser(email, password)?.let { _ ->
                MainPreference.setUserReference(this@SignInActivity, email)
                startActivity(Intent(this@SignInActivity, MainActivity::class.java))
                finish()
            } ?: run {
                Snackbar.make(signInButton, getString(R.string.error_email_and_or_password_wrong), Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}
