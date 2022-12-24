package com.example.habitualize

import android.app.Application
import com.example.habitualize.billing.BillingDataSource
import com.example.habitualize.billing.PurchaseRepository
import com.example.habitualize.managers.AdsManager
import com.example.habitualize.managers.AdsManager.Companion.getInstance
import com.example.habitualize.utils.SharePrefHelper
import com.example.habitualize.utils.openAppCounter
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {

    lateinit var appContainer: AppContainer
    companion object{
        var mInstance: MyApplication? = null
        fun getInstance(): MyApplication{
            if(mInstance == null){
                mInstance = MyApplication()
            }
            return mInstance!!
        }
    }
    override fun onCreate() {
        super.onCreate()
        mInstance = this
        AdsManager.getInstance().initializeManager(this)
        appContainer = AppContainer()
        //open app counter
        var counter  = SharePrefHelper.readInteger(openAppCounter) + 1
        SharePrefHelper.writeInteger(openAppCounter, counter)
    }

    class AppContainer {
        val billingDataSource: BillingDataSource =
            BillingDataSource.getInstance(getInstance(), PurchaseRepository.SUBSCRIPTION_SKUS)
        val purchaseRepository: PurchaseRepository = PurchaseRepository(billingDataSource)
    }
}