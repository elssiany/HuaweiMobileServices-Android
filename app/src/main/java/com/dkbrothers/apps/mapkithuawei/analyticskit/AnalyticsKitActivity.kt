package com.dkbrothers.apps.mapkithuawei.analyticskit

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dkbrothers.apps.mapkithuawei.R
import com.huawei.hms.analytics.HiAnalytics
import com.huawei.hms.analytics.HiAnalyticsInstance
import com.huawei.hms.analytics.HiAnalyticsTools
import com.huawei.hms.analytics.type.HAEventType.SUBMITSCORE
import com.huawei.hms.analytics.type.HAParamType.SCORE
import java.text.SimpleDateFormat
import java.util.*


/*
 * todo Analytics Kit Docs
  *https://developer.huawei.com/consumer/en/doc/development/HMS-Guides/3021001
  * Codelab:
  * https://developer.huawei.com/consumer/en/codelab/HMSAnalyticsKit/index.html#0
 * */
class AnalyticsKitActivity : AppCompatActivity() {



    //UI
    private var inputAnswer:EditText? = null

    private val correctAnswer = "huawei device"

    // TODO: Analytics Instance
    var hiAnalyticsInstance: HiAnalyticsInstance? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analytics_kit)
        initSettings()
        initViews()
    }


    private fun initViews(){
        inputAnswer = findViewById(R.id.input_answer)
    }


    private fun initSettings(){
        // TODO: Initiate Analytics Kit
        // Enable Analytics Kit Log
        HiAnalyticsTools.enableLog()
        // Generate the Analytics Instance
        hiAnalyticsInstance = HiAnalytics.getInstance(this)

        // Enable collection capability
        hiAnalyticsInstance?.setAnalyticsEnabled(true)
    }

    fun onAnalyticsTest(view: View) {
        val answer = inputAnswer?.text.toString().trim()
        if (answer.isNotEmpty()) {
            reportAnswerEvt(answer)
            analyzeAnswer(answer)
            inputAnswer?.setText("")
        }else{
            Toast.makeText(applicationContext,getString(R.string.enter_your_answer),
                Toast.LENGTH_SHORT).show()
        }
    }


    fun onGoDocumentation(view: View) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(getString(R.string.analytics_kit_documentation_link)))
        )
    }


    private fun reportAnswerEvt(answer: String) {
        // TODO: Report a customized Event
        // Event Name: Answer
        // Event Parameters:
        //  -- question: String
        //  -- answer:String
        //  -- answerTime: String

        // Initialize parameters.
        val bundle = Bundle()
        bundle.putString("question", getString(R.string.china_s_largest_and_most_famous_mobile_phone_sales_company))
        bundle.putString("answer", answer)
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        bundle.putString("answerTime", sdf.format(Date()))

        // Report a predefined event.
        hiAnalyticsInstance?.onEvent("Answer", bundle)
    }

    private fun analyzeAnswer(answer: String){
        val normalizedAnswer = answer.toUpperCase(Locale.getDefault())
        val score:Int
        score = if(normalizedAnswer==correctAnswer){
            100
        }else if(correctAnswer.contains(normalizedAnswer)){
            50
        }else if(normalizedAnswer.contains("huawei")
            || normalizedAnswer.contains("device")){
            10
        }else{
            5
        }
        postScore(score)
        Toast.makeText(applicationContext,getString(R.string.you_earned_d_points,score),
            Toast.LENGTH_SHORT).show()
    }

    private fun postScore(score:Int) {
        // TODO: Report score by using SUBMITSCORE Event
        // Initiate Parameters
        val bundle = Bundle()
        bundle.putLong(SCORE, score.toLong())
        // Report a predefined Event
        hiAnalyticsInstance?.onEvent(SUBMITSCORE, bundle)
    }

}
