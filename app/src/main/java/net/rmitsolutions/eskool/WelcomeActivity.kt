package net.rmitsolutions.eskool

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.google.firebase.analytics.FirebaseAnalytics
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_welcome.*
import kotlinx.android.synthetic.main.include_login.*
import net.rmitsolutions.eskool.dashboard.DashboardActivity
import net.rmitsolutions.eskool.databinding.ActivityWelcomeBinding
import net.rmitsolutions.eskool.fcm.php.ApiService
import net.rmitsolutions.eskool.fcm.php.User
import net.rmitsolutions.eskool.helpers.*
import net.rmitsolutions.eskool.models.AccessToken
import net.rmitsolutions.eskool.network.IUser
import net.rmitsolutions.eskool.network.TokenService
import net.rmitsolutions.eskool.viewmodels.UserViewModel
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper


class WelcomeActivity : AppCompatActivity() {
    lateinit private var fireBaseAnalytics: FirebaseAnalytics
    private var compositeDisposable: CompositeDisposable? = null
    lateinit var userViewModel: UserViewModel
    lateinit var dataBinding: ActivityWelcomeBinding
    private val tokenService: TokenService by lazy { TokenService() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fireBaseAnalytics = FirebaseAnalytics.getInstance(this)
        val userAccessToken = getPref(SharedPrefKeys.TOKEN_OBJECT_KEY, "")
        if (!userAccessToken.isEmpty()) {
            Constants.accessToken = JsonHelper.jsonToKt<AccessToken>(userAccessToken)
            startActivity<DashboardActivity>()
            finishNoAnim()
            return
        }

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_welcome)
        Glide.with(this).load(R.drawable.reqelford).into(welcomeLogo)

        userViewModel = UserViewModel("", "")
        dataBinding.user = userViewModel
        compositeDisposable = CompositeDisposable()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.dispose()
    }

    override fun attachBaseContext(newBase: Context) = super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))

    fun onLoginClick(view: View) {
        login()
    }

    fun onForgotPasswordClick(view: View) {
        alert("Contact the school admin.") {
            okButton { }
        }.show()
    }

    private fun login() {
        if (!validate()) return

        setProgress(true)
        compositeDisposable?.add(tokenService.getService<IUser>().validateUser(
                Constants.GRANT_TYPE, Constants.CLIENT_ID, Constants.CLIENT_SECRET, userViewModel.userName, userViewModel.password)
                .processRequest(
                        { token ->
                            logFireBase(userViewModel.userName)
                            getFCMToken(userViewModel.userName)
                            Constants.accessToken = token
                            addBulkPrefs { editor ->
                                addToPrefEditor(editor, SharedPrefKeys.TOKEN_OBJECT_KEY, JsonHelper.KtToJson(token))
                                addToPrefEditor(editor, SharedPrefKeys.TOKEN_KEY, token.accessToken)
                            }
                            setProgress(false)
                            startActivity<DashboardActivity>()
                            finishNoAnim()
                        },
                        { errMsg ->
                            setProgress(false)
                            snackBar(buttonLogin, errMsg, Snackbar.LENGTH_INDEFINITE)
                            logE(errMsg)
                        }
                ))
    }

    private fun validate(): Boolean {
        if (userViewModel.userName.isBlank()) {
            lblUserName.error = "User name is required."
            return false
        } else {
            lblUserName.isErrorEnabled = false
        }

        if (userViewModel.password.isBlank()) {
            lblPassword.error = "Password is required."
            return false
        } else {
            lblPassword.isErrorEnabled = false
        }

        if (!isNetConnected(false)) {
            snackBar(buttonLogin, getString(R.string.you_are_offline))
            return false
        }

        return true
    }


    private fun setProgress(show: Boolean) {
        if (show) {
            buttonLogin.visibility = View.INVISIBLE
            showProgress()
        } else {
            buttonLogin.visibility = View.VISIBLE
            hideProgress()
        }
    }

    private fun logFireBase(user: String) {
        fireBaseAnalytics.setUserId(user)
        val bundle = Bundle()
        bundle.putString("UserName", user);
        fireBaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle)
    }

    private val DEFAULT = "DEFAULT"

    private fun getFCMToken(userName: String) {
        val token : String = getPref(SharedPrefKeys.FCM_TOKEN_KEY,DEFAULT)
        Log.d("GetToken", token)
        val user = User()
        if (token!= DEFAULT){
            val apiService : ApiService = tokenService.getService()
            val call = apiService.insertToken(user.userName!!, user.fcmTokenId!!)

            call.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    Log.d("OnResponse","response" + response)

                }
                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.e("OnFailure", "" + t)
                    Log.d("Message", t.message)

                }
            })
        }else{
            Log.d("Fcm Token", "Default")
        }

    }
}
