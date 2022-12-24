package com.example.habitualize.ui.premium

import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.habitualize.MyApplication
import com.example.habitualize.R
import com.example.habitualize.billing.MakePurchaseViewModel
import com.example.habitualize.billing.MakePurchaseViewModel.*
import com.example.habitualize.databinding.ActivityPremiumBinding
import com.example.habitualize.ui.privacypolicy.PrivacyPolicyActivity
import com.example.habitualize.utils.SharePrefHelper
import com.example.habitualize.utils.changeStatusBarColor
import com.example.habitualize.utils.selectedColorIndex


class PremiumActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPremiumBinding
    private var makePurchaseViewModel: MakePurchaseViewModel? = null
    var subscriptionSKUs: ArrayList<String>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPremiumBinding.inflate(layoutInflater)
        updateTheme(SharePrefHelper.readInteger(selectedColorIndex))
        setContentView(binding.root)
        initListeners()
        setData()
        init()
    }

    private fun init() {

        val makePurchaseViewModelFactory =
            MakePurchaseViewModelFactory((this.application as MyApplication).appContainer.purchaseRepository)

        makePurchaseViewModel = ViewModelProvider(this, makePurchaseViewModelFactory).get(
            MakePurchaseViewModel::class.java
        )
        subscriptionSKUs = ArrayList()
        subscriptionSKUs!!.add(ITEM_SKU_WEEK)
        subscriptionSKUs!!.add(ITEM_SKU_ONEMONTHS)
        subscriptionSKUs!!.add(ITEM_SKU_YEARLY)

       setPrice()
    }

    private fun setData(){
        binding.includedLayout.tvTitle.text = resources.getString(R.string.premium)
    }

    private fun initListeners(){

        binding.includedLayout.backButton.setOnClickListener {
            finish()
        }

        binding.termsAndConditions.setOnClickListener {
//            startActivity(Intent(this, PrivacyPolicyActivity::class.java))
            privacyPolicyEvent()
        }

        binding.tvMoreBtn.setOnClickListener {
            if(binding.cvYearlyCard.visibility == View.VISIBLE){
                binding.cvYearlyCard.visibility = View.GONE
                binding.tvMoreBtn.text = resources.getString(R.string.more)
            }else{
                binding.cvYearlyCard.visibility = View.VISIBLE
                binding.tvMoreBtn.text = resources.getString(R.string.hide)
            }
        }

        binding.cvFreeTrialCard.setOnClickListener {
            //bp.subscribe(SubscriptionActivity.this, Constants.SKU_ITEM_WEEKLY);
            makePurchaseViewModel?.buySku(this, ITEM_SKU_WEEK)
        }

        binding.tvFreeTry.setOnClickListener {
            //bp.subscribe(SubscriptionActivity.this, Constants.SKU_ITEM_WEEKLY);
            makePurchaseViewModel?.buySku(this, ITEM_SKU_WEEK)
        }

        binding.cvMonthlyCard.setOnClickListener {
            makePurchaseViewModel?.buySku(this, ITEM_SKU_ONEMONTHS)
        }

        binding.cvYearlyCard.setOnClickListener {
            makePurchaseViewModel?.buySku(this, ITEM_SKU_YEARLY)
        }

    }

    private fun setPrice() {
        makePurchaseViewModel!!.getSkuDetails(ITEM_SKU_WEEK).price.observe(
            this
        ) { s ->
//            binding.tvSubscribeOneMonth.text = "1 Months Package\n$s"
            println("checking the price of sku : $s")
            binding.tvFreeTry.text = "${resources.getString(R.string.enjoy_a_3_day_free_trial_then_week)} $s ${resources.getString(R.string.week)}."
        }

        makePurchaseViewModel!!.getSkuDetails(ITEM_SKU_ONEMONTHS).price.observe(
            this
        ) { s ->
            binding.tvMonthlyPrice.text = "${resources.getString(R.string.monthly_package)}\n$s"
        }

        makePurchaseViewModel!!.getSkuDetails(ITEM_SKU_YEARLY).price.observe(
            this
        ) { s ->
            binding.tvYearlyPrice.text = "${resources.getString(R.string.yearly_package)}\n$s"
        }
    }

    private fun updateTheme(position: Int){
        when(position){
            0->{ setThemeColor(R.color.theme) }
            1->{ setThemeColor(R.color.theme_2) }
            2->{ setThemeColor(R.color.theme_3) }
            3->{ setThemeColor(R.color.theme_4) }
            4->{ setThemeColor(R.color.theme_5) }
            5->{ setThemeColor(R.color.theme_6) }
            6->{ setThemeColor(R.color.theme_7) }
            7->{ setThemeColor(R.color.theme_8) }
        }
    }

    private fun setThemeColor(color: Int){
        changeStatusBarColor(this, color)
        binding.cvMonthlyCard.setCardBackgroundColor(ContextCompat.getColor(this, color))
        binding.cvYearlyCard.setCardBackgroundColor(ContextCompat.getColor(this, color))
        binding.tvFreeTrial.setTextColor(ContextCompat.getColor(this, color))
        binding.root.setBackgroundColor(ContextCompat.getColor(this, color))
    }

    private fun privacyPolicyEvent(){
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://habitualizerapp.com/privacy-policy/")
        )
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }else{
            Toast.makeText(this, "App is not Installed to open this link!", Toast.LENGTH_SHORT).show()
        }
    }

}