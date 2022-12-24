package com.example.habitualize.managers

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.OnHierarchyChangeListener
import android.widget.*
import androidx.annotation.NonNull
import com.example.habitualize.utils.SharePrefHelper
import com.example.habitualize.R
import com.google.android.gms.ads.*
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.MediaView
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.gms.tasks.OnSuccessListener


class AdsManager {
    private val TAG = AdsManager::class.java.name
    private var mInterstitialAd: InterstitialAd? = null
    var isEnabledNoAds = false
    var context: Context? = null
    var isRewardEarned = false
    var rewardedAd: RewardedAd? = null

    companion object {
        var manager: AdsManager? = null

        @JvmStatic
        fun getInstance(): AdsManager {
            if (manager == null) {
                manager = AdsManager()
            }
            return manager!!
        }

        fun isNetworkAvailable(context: Context?): Boolean {
            return try {
                if (context == null) return false
                val connectivityManager =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                if (connectivityManager != null) {
                    if (Build.VERSION.SDK_INT < 23) {
                        val ni = connectivityManager.activeNetworkInfo
                        if (ni != null) {
                            return ni.isConnected && (ni.type == ConnectivityManager.TYPE_WIFI || ni.type == ConnectivityManager.TYPE_MOBILE)
                        }
                    } else {
                        val n = connectivityManager.activeNetwork
                        if (n != null) {
                            val nc = connectivityManager.getNetworkCapabilities(n)
                            return nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(
                                NetworkCapabilities.TRANSPORT_WIFI
                            )
                        }
                    }
                }
                false
            } catch (e: Exception) {
                false
            }
        }
    }

    fun initializeManager(context: Context?) {
        this.context = context
        isEnabledNoAds = SharePrefHelper.readBoolean("is_ad_disable")
        if (isEnabledNoAds) {
            return
        }
        loadInterstitial()
    }

    fun setAdsEnabled(bool: Boolean) {
        isEnabledNoAds = bool
    }

    private fun loadInterstitial() {
        if (!isNetworkAvailable(context) || isEnabledNoAds) return
//        AnalyticsManager.getInstance().sendAnalytics("ad_loading", "ad_interstitial")
        val adRequest: AdRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            context!!,
            context!!.getString(R.string.admob_interstitial_CS_ad_unit),
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    // The mInterstitialAd reference will be null until
                    // an ad is loaded.
//                    AnalyticsManager.getInstance().sendAnalytics("ad_loaded", "ad_interstitial")
                    mInterstitialAd = interstitialAd
                    Log.i(TAG, "onAdLoaded")
                    if (mInterstitialAd == null) return
                    mInterstitialAd!!.setFullScreenContentCallback(object :
                        FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            // Called when fullscreen content is dismissed.
//                            AnalyticsManager.getInstance()
//                                .sendAnalytics("dismissed", "ad_interstitial")
                            Log.d("TAG", "The ad was dismissed.")
                            loadInterstitial()
                        }

                        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                            // Called when fullscreen content failed to show...
//                            AnalyticsManager.getInstance()
//                                .sendAnalytics("ad_failed_to_shown", "ad_interstitial")
                            Log.d("TAG", "The ad failed to show.")
                        }

                        override fun onAdShowedFullScreenContent() {
                            // Called when fullscreen content is shown.
                            // Make sure to set your reference to null so you don't
                            // show it a second time.
//                            AnalyticsManager.getInstance()
//                                .sendAnalytics("ad_shown_full_screen", "ad_interstitial")
                            mInterstitialAd = null
                            Log.d("TAG", "The ad was shown.")
                        }
                    })
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    // Handle the error
                    Log.i(TAG, loadAdError.getMessage())
//                    AnalyticsManager.getInstance().sendAnalytics("ad_failed", "ad_interstitial")
                    mInterstitialAd = null
                }
            })
    }

    fun loadAdaptiveBanner(activity: Activity, frameLayout: FrameLayout) {
        try {
            if (!isNetworkAvailable(context) || isEnabledNoAds) return
//            AnalyticsManager.getInstance().sendAnalytics("showing", "ad_adaptive_banner")
            val adView = AdView(activity)
            adView.setAdUnitId(context!!.getString(R.string.admob_adaptive_banner_ad_unit))
            frameLayout.removeAllViews()
            frameLayout.addView(adView)

//        AdSize adSize = getAdSize(activity);
            adView.setAdSize(AdSize.MEDIUM_RECTANGLE)
            val adRequest: AdRequest = AdRequest.Builder().build()

            // Start loading the ad in the background.
            adView.loadAd(adRequest)
        } catch (e: Exception) {
            Log.d(TAG, "loadAdaptiveBanner: " + e.message)
        }
    }

    fun showInterstitial(activity: Activity) {
        if (!isNetworkAvailable(context) || isEnabledNoAds) return
        run {
//            AnalyticsManager.getInstance().sendAnalytics("showing", "ad_interstitial")
            val pd = ProgressDialog(activity)
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            pd.setTitle("Ad showing")
            pd.setMessage("Loading...")
            pd.isIndeterminate = true
            pd.setCancelable(false)
            pd.isIndeterminate = false

            // Finally, show the progress dialog
            pd.show()
            if (mInterstitialAd != null) {
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({
                    if (mInterstitialAd == null) {
                        try {
                            if (pd.isShowing) {
                                pd.dismiss()
                            }
                        } catch (e: Exception) {
                            Log.e(TAG, "run: " + e.message)
                        }
                    } else {
                        mInterstitialAd!!.show(activity)
                        try {
                            if (pd.isShowing) {
                                pd.dismiss()
                            }
                        } catch (e: Exception) {
                            Log.e(TAG, "run: " + e.message)
                        }
                    }
                }, 1000)
            } else {
                loadInterstitial()
                try {
                    if (pd.isShowing) {
                        pd.dismiss()
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "run: " + e.message)
                }
                Log.d("TAG", "The interstitial ad wasn't ready yet.")
            }
        }
    }



    private fun populateNativeAdView(nativeAd: NativeAd, adView: NativeAdView) {
        try {
            // Set the media view.
            adView.setMediaView(adView.findViewById(R.id.ad_media) as MediaView)
            (adView.findViewById(R.id.ad_media) as MediaView).setOnHierarchyChangeListener(
                object :
                    OnHierarchyChangeListener {
                    override fun onChildViewAdded(view: View, child: View) {
                        if (child is ImageView) {
                            val imageView = child
                            imageView.adjustViewBounds = true
                            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                        }
                    }

                    override fun onChildViewRemoved(view: View, view1: View) {}
                })

            // Set other ad assets.
            adView.setHeadlineView(adView.findViewById(R.id.ad_headline))
            adView.setBodyView(adView.findViewById(R.id.ad_body))
            adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action))
            adView.setIconView(adView.findViewById(R.id.ad_app_icon))

            /*adView.setPriceView(adView.findViewById(R.id.ad_price));
            adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
            adView.setStoreView(adView.findViewById(R.id.ad_store));
            adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));*/

            // The headline and mediaContent are guaranteed to be in every NativeAd.
            (adView.getHeadlineView() as TextView).setText(nativeAd.getHeadline())
            adView.getMediaView()?.setMediaContent(nativeAd.getMediaContent())

            // These assets aren't guaranteed to be in every NativeAd, so it's important to
            // check before trying to display them.
            if (nativeAd.body == null) {
                (adView.getBodyView() as TextView).setVisibility(View.INVISIBLE)
            } else {
                (adView.getBodyView() as TextView).setVisibility(View.VISIBLE)
                (adView.getBodyView() as TextView).text = "Ad - " + nativeAd.getBody()
            }
            if (nativeAd.getCallToAction() == null) {
                (adView.getCallToActionView() as TextView).setVisibility(View.INVISIBLE)
            } else {
                (adView.getCallToActionView() as TextView).setVisibility(View.VISIBLE)
                (adView.getCallToActionView() as Button).setText(nativeAd.getCallToAction())
            }
            if (nativeAd.getIcon() == null) {
                (adView.getIconView() as TextView).setVisibility(View.INVISIBLE)
            } else {
                (adView.getIconView() as ImageView).setImageDrawable(
                    nativeAd.getIcon()!!.getDrawable()
                )
                (adView.getIconView() as ImageView).setVisibility(View.VISIBLE)
            }

            /*if (nativeAd.getPrice() == null) {
                adView.getPriceView().setVisibility(View.INVISIBLE);
            } else {
                adView.getPriceView().setVisibility(View.VISIBLE);
                ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
            }*/

            /*if (nativeAd.getStore() == null) {
                adView.getStoreView().setVisibility(View.INVISIBLE);
            } else {
                adView.getStoreView().setVisibility(View.VISIBLE);
                ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
            }*/

            /*if (nativeAd.getStarRating() == null) {
                adView.getStarRatingView().setVisibility(View.INVISIBLE);
            } else {
                ((RatingBar) adView.getStarRatingView())
                        .setRating(nativeAd.getStarRating().floatValue());
                adView.getStarRatingView().setVisibility(View.VISIBLE);
            }*/

            /*if (nativeAd.getAdvertiser() == null) {
                adView.getAdvertiserView().setVisibility(View.INVISIBLE);
            } else {
                ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
                adView.getAdvertiserView().setVisibility(View.VISIBLE);
            }*/

            // This method tells the Google Mobile Ads SDK that you have finished populating your
            // native ad view with this native ad.
            adView.setNativeAd(nativeAd)

            // Get the video controller for the ad. One will always be provided, even if the ad doesn't
            // have a video asset.
            val vc: VideoController? = nativeAd.getMediaContent()?.getVideoController()

            // Updates the UI to say whether or not this ad has a video asset.
            if (vc?.hasVideoContent() == true) {

                // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
                // VideoController will call methods on this object when events occur in the video
                // lifecycle.
                vc?.setVideoLifecycleCallbacks(object :
                    VideoController.VideoLifecycleCallbacks() {
                    override fun onVideoEnd() {
                        // Publishers should allow native ads to complete video playback before
                        // refreshing or replacing them with another ad in the same UI location.

                        /*refresh.setEnabled(true);
                    videoStatus.setText("Video status: Video playback has ended.");*/
                        super.onVideoEnd()
                    }
                })
            } else {
                /* videoStatus.setText("Video status: Ad does not contain a video asset.");
            refresh.setEnabled(true);*/
            }
        } catch (e: Exception) {
            Log.e(TAG, "populateNativeAdView: " + e.message)
        }
    }

    fun loadNative(frameLayout: FrameLayout, layoutInflater: LayoutInflater, layout: Int) {
        if (!isNetworkAvailable(context) || isEnabledNoAds) return
//        AnalyticsManager.getInstance().sendAnalytics("ad_loading", "ad_native")
        val builder: AdLoader.Builder =
            AdLoader.Builder(context!!, context!!.getString(R.string.admob_native_ad_unit))
        builder.forNativeAd { nativeAd ->
            // OnLoadedListener implementation.
            //                    AnalyticsManager.getInstance().sendAnalytics("ad_loaded", "ad_native")
            Log.d("adFail", "onNativeAdLoaded: load")
            val adView: NativeAdView = layoutInflater.inflate(layout, null) as NativeAdView
            populateNativeAdView(nativeAd, adView)
            frameLayout.removeAllViews()
            frameLayout.addView(adView)
        }
        val videoOptions: VideoOptions = VideoOptions.Builder().setStartMuted(false).build()
        val adOptions: NativeAdOptions =
            NativeAdOptions.Builder().setVideoOptions(videoOptions).build()
        builder.withNativeAdOptions(adOptions)
        val adLoader: AdLoader = builder
            .withAdListener(
                object : AdListener() {
                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
//                                        refresh.setEnabled(true);
                        val error = String.format(
                            "domain: %s, code: %d, message: %s",
                            loadAdError.getDomain(),
                            loadAdError.getCode(),
                            loadAdError.getMessage()
                        )
                        Log.d("adFail", "onAdFailedToLoad: $error")
                    }
                })
            .build()
        adLoader.loadAd(AdRequest.Builder().build())
    }


    fun loadNative(
        frameLayout: FrameLayout,
        layoutInflater: LayoutInflater,
        layout: Int,
        tvLoading: TextView
    ) {
        if (!isNetworkAvailable(context) || isEnabledNoAds) return
        val builder: AdLoader.Builder =
            AdLoader.Builder(context!!, context!!.getString(R.string.admob_native_ad_unit))
        builder.forNativeAd { nativeAd ->

            // OnLoadedListener implementation.
            tvLoading.visibility = View.GONE
            frameLayout.visibility = View.VISIBLE
            val adView: NativeAdView = layoutInflater.inflate(layout, null) as NativeAdView
            populateNativeAdView(nativeAd, adView)
            frameLayout.removeAllViews()
            frameLayout.addView(adView)
        }
        val videoOptions: VideoOptions = VideoOptions.Builder().setStartMuted(false).build()
        val adOptions: NativeAdOptions =
            NativeAdOptions.Builder().setVideoOptions(videoOptions).build()
        builder.withNativeAdOptions(adOptions)
        val adLoader: AdLoader = builder
            .withAdListener(
                object : AdListener() {
                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    }
                })
            .build()
        adLoader.loadAd(AdRequest.Builder().build())
    }

    fun loadBanner(activity: Activity, frameLayout: FrameLayout?) {
//        AnalyticsManager.getInstance()?.sendAnalytics("showing", "ad_banner")
        if (!isNetworkAvailable(context) || isEnabledNoAds) return
        if (frameLayout == null) return
        val adView = AdView(activity)
        adView.setAdUnitId(context!!.getString(R.string.admob_banner_ad_unit))
        frameLayout.removeAllViews()
        frameLayout.addView(adView)
        val adSize: AdSize = getAdSize(activity)
        adView.setAdSize(adSize)
        val adRequest: AdRequest = AdRequest.Builder().build()

        // Start loading the ad in the background.
        adView.loadAd(adRequest)
    }

    private fun getAdSize(activity: Activity): AdSize {
        // Step 2 - Determine the screen width (less decorations) to use for the ad width.
        val display = activity.windowManager.defaultDisplay
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)
        val widthPixels = outMetrics.widthPixels.toFloat()
        val density = outMetrics.density
        val adWidth = (widthPixels / density).toInt()

        // Step 3 - Get adaptive ad size and return for setting on the ad view.
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth)
    }



    fun loadRewardedAd(activity: Activity, listener: OnSuccessListener<Boolean>) {
        if (!isNetworkAvailable(activity) || isEnabledNoAds) return

//        if (rewardedAd == null) {

        //  DialogsUtils.showLoader(activity, activity.getString(R.string.loading_reward_ad));
//            CustomLoader customLoader = new CustomLoader(activity, false);
//            customLoader.showIndicator();
        val adRequest = AdManagerAdRequest.Builder().build()
        RewardedAd.load(
            activity,
            activity.getString(R.string.admob_reward_ad_unit),  //                    AD_UNIT_ID,
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(@NonNull loadAdError: LoadAdError) {
//                    AnalyticsManager.getInstance()
//                        .sendAnalytics(AnalyticsManager.FAILED, "reward_ad")
                    //    DialogsUtils.dismissLoader();
//                            customLoader.hideIndicator();
                    // Handle the error.
                    Log.d(TAG, loadAdError.message)
                    rewardedAd = null
                    listener.onSuccess(false)
                    Log.e("waleedhassan", "----1-----------")
                    loadRewardedAd(activity, listener)
                    //   Toast.makeText(activity, "Ad Failed To Load", Toast.LENGTH_SHORT).show();
                }

                override fun onAdLoaded(@NonNull ad: RewardedAd) {
                    //    DialogsUtils.dismissLoader();
//                    AnalyticsManager.getInstance()
//                        .sendAnalytics(AnalyticsManager.LOADED, "reward_ad")

//                            customLoader.hideIndicator();
                    rewardedAd = ad
                    Log.d(TAG, "onAdLoaded")
                    //                            showRewardedVideo(activity, listener);
                    listener.onSuccess(true)
                    Log.e("waleedhassan", "----2-----------")
                }
            })
//        }
    }

    fun showRewardedVideo(activity: Activity, listener: OnSuccessListener<Boolean>) {
        if (rewardedAd == null) {
            Log.d(TAG, "The rewarded ad wasn't ready yet.")
            return
        }
        rewardedAd!!.setFullScreenContentCallback(
            object : FullScreenContentCallback() {
                override fun onAdShowedFullScreenContent() {
//                    AnalyticsManager.getInstance()
//                        .sendAnalytics(AnalyticsManager.SHOWED, "reward_ad")

                    // Called when ad is shown.
                    Log.d(TAG, "onAdShowedFullScreenContent")
                    isRewardEarned = false
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
//                    AnalyticsManager.getInstance()
//                        .sendAnalytics(AnalyticsManager.FAILED, "reward_ad")

                    // Called when ad fails to show.
                    Log.d(TAG, "onAdFailedToShowFullScreenContent")
                    // Don't forget to set the ad reference to null so you
                    // don't show the ad a second time.
                    rewardedAd = null
                    Toast.makeText(
                        activity, "Failed to show fullscreen content.", Toast.LENGTH_SHORT
                    )
                        .show()
                    listener.onSuccess(false)
                }

                override fun onAdDismissedFullScreenContent() {
//                    AnalyticsManager.getInstance()
//                        .sendAnalytics(AnalyticsManager.DISMISSED, "reward_ad")

                    // Called when ad is dismissed.
                    Log.d(TAG, "onAdDismissedFullScreenContent")
                    // Don't forget to set the ad reference to null so you
                    // don't show the ad a second time.
                    rewardedAd = null
                    listener.onSuccess(isRewardEarned)
                }
            })
        rewardedAd!!.show(
            activity
        ) { rewardItem ->
            // Handle the reward.
            Log.d(TAG, "The user earned the reward.")
            val rewardAmount: Int = rewardItem.getAmount()
            val rewardType: String = rewardItem.getType()
            isRewardEarned = true
        }
    }

}