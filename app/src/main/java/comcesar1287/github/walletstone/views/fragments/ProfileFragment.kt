package comcesar1287.github.walletstone.views.fragments

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.view.*

import comcesar1287.github.walletstone.R
import comcesar1287.github.walletstone.database.dao.UserDao
import comcesar1287.github.walletstone.database.models.User
import comcesar1287.github.walletstone.preferences.MainPreference
import comcesar1287.github.walletstone.views.MainApp
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    private var user: User? = null
    private var userDao: UserDao? = null

    init {
        userDao = MainApp.database?.userDao()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).title = ""

        setHasOptionsMenu(true)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let { activity ->
            val email = MainPreference.getUserReference(activity)

            user = userDao?.getUserByEmail(email)

            user?.let { userNonNull ->
                nameEdit.text = Editable.Factory.getInstance().newEditable(userNonNull.name)
                emailEdit.text = Editable.Factory.getInstance().newEditable(userNonNull.email)
                passwordEdit.text = Editable.Factory.getInstance().newEditable(userNonNull.password)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.profile, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            R.id.menu_update -> {
                user?.name = nameEdit.text.toString()
                user?.password = passwordEdit.text.toString()

                user?.let { userNonNull ->
                    userDao?.insertUsers(userNonNull)
                    Snackbar.make(logout, getString(R.string.data_successfully_saved), Snackbar.LENGTH_SHORT).show()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
