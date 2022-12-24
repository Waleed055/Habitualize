package com.example.habitualize.billing;

import static com.example.habitualize.billing.MakePurchaseViewModel.ITEM_SKU_ONEMONTHS;
import static com.example.habitualize.billing.MakePurchaseViewModel.ITEM_SKU_WEEK;
import static com.example.habitualize.billing.MakePurchaseViewModel.ITEM_SKU_YEARLY;

import android.app.Activity;
import android.util.Log;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.habitualize.R;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PurchaseRepository {
    // Source for all constants
    final static public int GAS_TANK_MIN = 0;
    final static public int GAS_TANK_MAX = 4;
    final static public int GAS_TANK_INFINITE = 5;
    static final String TAG = PurchaseRepository.class.getSimpleName();

    public static final String[] SUBSCRIPTION_SKUS = new String[]{ITEM_SKU_WEEK, ITEM_SKU_ONEMONTHS, ITEM_SKU_YEARLY};

    final BillingDataSource billingDataSource;
    final SingleMediatorLiveEvent<Integer> gameMessages;
    final SingleMediatorLiveEvent<Integer> allMessages = new SingleMediatorLiveEvent<>();
    final ExecutorService driveExecutor = Executors.newSingleThreadExecutor();

    public PurchaseRepository(BillingDataSource billingDataSource) {
        this.billingDataSource = billingDataSource;
        gameMessages = new SingleMediatorLiveEvent<>();
        setupMessagesSingleMediatorLiveEvent();
        // Since both are tied to application lifecycle
        billingDataSource.observeConsumedPurchases().observeForever(skuList -> {
            for (String sku : skuList) {
                /*
                 * TODO:: here we make action against inapp purchased e.g is_no_ads_enabled = true
                 * */
                //Toast.makeText(BaseApplication.getContext(), "observeConsumedPurchases()", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Sets up the event that we can use to send messages up to the UI to be used in Snackbars. This
     * SingleMediatorLiveEvent observes changes in SingleLiveEvents coming from the rest of the game
     * and combines them into a single source with new purchase events from the BillingDataSource.
     * Since the billing data source doesn't know about our SKUs, it also transforms the known SKU
     * strings into useful String messages.
     */

    void setupMessagesSingleMediatorLiveEvent() {
        final LiveData<List<String>> billingMessages = billingDataSource.observeNewPurchases();
        allMessages.addSource(gameMessages, allMessages::setValue);
        allMessages.addSource(billingMessages,
                stringList -> {
                    for (String s : stringList) {
                        switch (s) {
                            case ITEM_SKU_WEEK:
                            case ITEM_SKU_ONEMONTHS:
                            case ITEM_SKU_YEARLY:
                                // TODO: Handle multi-line purchases better
                                // this makes sure that upgraded and downgraded subscriptions are
                                // reflected correctly in the app UI
                                billingDataSource.refreshPurchasesAsync();
                                allMessages.setValue(R.string.message_subscribed);
                                //allMessages.setValue(ActivityCompat.getString(R.string.subscribed_successfully));
                                //Toast.makeText(BaseApplication.getContext(), R.string.message_subscribed, Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
    }

    /**
     * Automatic support for upgrading/downgrading subscription.
     *
     * @param activity Needed by billing library to start the Google Play billing activity
     * @param sku      the product ID to purchase
     */
    public void buySku(Activity activity, String sku) {
        Log.d(TAG, "Launch Billing flow for SKU: " + sku);
        billingDataSource.launchBillingFlow(activity, sku);

        /*String oldSku = null;
        switch (sku) {
            case SKU_ITEM_ONE_MONTH:
                oldSku = SKU_ITEM_SIX_MONTH;
                break;
            case SKU_ITEM_SIX_MONTH:
                oldSku = SKU_ITEM_ONE_MONTH;
                break;
        }
        if (null != oldSku) {
            billingDataSource.launchBillingFlow(activity, sku, oldSku);
        } else {
            billingDataSource.launchBillingFlow(activity, sku);
        }*/
    }

    public LiveData<Boolean> isPurchased(String sku) {
        return billingDataSource.isPurchased(sku);
    }

    public LiveData<Boolean> canPurchase(String sku) {
        switch (sku) {
            default:
                return billingDataSource.canPurchase(sku);
        }
    }

    public void checkSubscriptionStatus() {
        billingDataSource.checkSubscriptionStatus();
    }

    private void combineGasAndSubscriptionData(MediatorLiveData<Integer> result, LiveData<Integer> gasTankLevel, LiveData<Boolean> monthlySubscription, LiveData<Boolean> sixMonthSubPurchased,
                                               LiveData<Boolean> yearlySubPurchased) {
        Boolean isMonthlySubscription = monthlySubscription.getValue();
        Boolean isSixMonthSubscription = sixMonthSubPurchased.getValue();
        Boolean isYearlySubscription = yearlySubPurchased.getValue();

        if (null == isMonthlySubscription || null == isSixMonthSubscription || null == isYearlySubscription)
            return; // do not emit

        if (isMonthlySubscription || isSixMonthSubscription || isYearlySubscription) {
            result.setValue(GAS_TANK_INFINITE);
        }
    }

    public final void refreshPurchases() {
        billingDataSource.refreshPurchasesAsync();
    }

    public final LifecycleObserver getBillingLifecycleObserver() {
        return billingDataSource;
    }

    // There's lots of information in SkuDetails, but our app only needs a few things, since our
    // goods never go on sale, have introductory pricing, etc.
    public final LiveData<String> getSkuTitle(String sku) {
        return billingDataSource.getSkuTitle(sku);
    }

    public final LiveData<String> getSkuPrice(String sku) {
        return billingDataSource.getSkuPrice(sku);
    }

    public final LiveData<String> getSkuDescription(String sku) {
        return billingDataSource.getSkuDescription(sku);
    }

    public final LiveData<Integer> getMessages() {
        return allMessages;
    }

    public final void sendMessage(int resId) {
        gameMessages.postValue(resId);
    }

    public final LiveData<Boolean> getBillingFlowInProcess() {
        return billingDataSource.getBillingFlowInProcess();
    }
}
