package net.rmitsolutions.eskool.sample

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import io.reactivex.disposables.CompositeDisposable
import net.rmitsolutions.eskool.ESkoolApplication
import net.rmitsolutions.eskool.R
import net.rmitsolutions.eskool.dependency.components.DaggerBaseActivityComponent
import net.rmitsolutions.eskool.helpers.*
import net.rmitsolutions.eskool.network.IStudent
import net.rmitsolutions.eskool.profile.ProfileRecyclerViewAdapter
import net.rmitsolutions.eskool.viewmodels.ProfileViewModel
import javax.inject.Inject

class SampleActivity : AppCompatActivity() {



    lateinit var recyclerView: RecyclerView
    val adapter: ProfileRecyclerViewAdapter by lazy { ProfileRecyclerViewAdapter() }
    lateinit var modelList: MutableList<ProfileViewModel>
    @Inject
    lateinit var studentService: IStudent
    lateinit internal var compositeDisposable: CompositeDisposable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        val depComponent = DaggerBaseActivityComponent.builder()
                .applicationComponent(ESkoolApplication.applicationComponent)
                .build()

        depComponent.injectSampleActivity(this)

//        val toolbar = find<Toolbar>(R.id.toolbar_actionbar)
//        setSupportActionBar(toolbar)
//        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white)
//        toolbar.setNavigationOnClickListener {
//            finishNoAnim()
//        }
//        window.reenterTransition = null

        compositeDisposable = CompositeDisposable()

        modelList = mutableListOf<ProfileViewModel>()


        recyclerView = findViewById<RecyclerView>(R.id.sample_activity_recycler_view)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.adapter = adapter

        Log.d("API Access Token", apiAccessToken)

        //getStudentDetails()

//        setPersonalData()

    }

    private fun setRecyclerViewAdapter(list: List<ProfileViewModel>) {
        adapter.items = list
        adapter.notifyDataSetChanged()
    }

    val context: Context? = null

    private fun getStudentDetails() {
        Log.d("Sample Activity", "Getting Student Details")
        if (!isNetConnected()) return
        showProgress()

        compositeDisposable.add(studentService.getAll()
                .processRequest(
                        { student ->
                            //Constants.studentModel = student
                            //context?.putPref(SharedPrefKeys.STUDENT_KEY, JsonHelper.KtToJson(student))student
                            Log.d("Getting Student", student.question)
                            Log.d("Getting Student", student.a)
                            Log.d("Getting Student", student.b)
                            Log.d("Getting Student", student.answer)
                            hideProgress()
                            setPersonalData()
                        },
                        { errMsg ->
                            hideProgress(true, errMsg)
                            logE(errMsg)
                        }
                ))
    }

    private fun setPersonalData() {
        modelList.clear()
        val student : Question? = null
        modelList.add(ProfileViewModel("Question", student?.question))
        modelList.add(ProfileViewModel("a", student?.a))
        modelList.add(ProfileViewModel("b", student?.b))
        modelList.add(ProfileViewModel("Mobile Number", student?.c))
        modelList.add(ProfileViewModel("Email", student?.answer))
//        modelList.add(ProfileViewModel("Name", "Mayank"))
//        modelList.add(ProfileViewModel("Gender", "Boy"))
//        modelList.add(ProfileViewModel("Born on", "20/03/1990"))
//        modelList.add(ProfileViewModel("Mobile Number", "9993657966"))
//        modelList.add(ProfileViewModel("Email", "mayank84735@gmail.com"))
        setRecyclerViewAdapter(modelList)
    }

//    fun addData(view: View){
//        addBulkPrefs { editor ->
//            addToPrefEditor(editor, "fcm_token_key", "Meri Token")
//        }
//    }
//
    fun getData(view : View){
        val token : String = getPref(SharedPrefKeys.FCM_TOKEN_KEY,"Default")
        Log.d("MyToken", token)
    }
}