package com.example.habitualize.billing;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.HashMap;
import java.util.Map;

public class MakePurchaseViewModel extends ViewModel {
    static final String TAG = MakePurchaseViewModel.class.getSimpleName();
    static final private Map<String, Integer> skuToResourceIdMap = new HashMap<>();

    final static public String ITEM_SKU_YEARLY = "item_1year";
    final static public  String ITEM_SKU_ONEMONTHS = "item_1m";
    final static public  String ITEM_SKU_WEEK = "item_1week";

    static {
        skuToResourceIdMap.put(ITEM_SKU_WEEK, 1);
        skuToResourceIdMap.put(ITEM_SKU_ONEMONTHS, 2);
        skuToResourceIdMap.put(ITEM_SKU_YEARLY, 3);
    }

    private final PurchaseRepository tdr;

    public MakePurchaseViewModel(@NonNull PurchaseRepository trivialDriveRepository) {
        super();
        tdr = trivialDriveRepository;
    }

    public SkuDetails getSkuDetails(String sku) {
        return new SkuDetails(sku, tdr);
    }

    public LiveData<Boolean> canBuySku(String sku) {
        return tdr.canPurchase(sku);
    }

    public LiveData<Boolean> isPurchased(String sku) {
        return tdr.isPurchased(sku);
    }

    public void checkSubscriptionStatus() {
        tdr.checkSubscriptionStatus();
    }
    /**
     * Starts a billing flow for purchasing gas.
     *
     * @param activity needed by Billing library to launch the purchase Activity
     */

    public void buySku(Activity activity, String sku) {
        tdr.buySku(activity, sku);
    }

    public LiveData<Boolean> getBillingFlowInProcess() {
        return tdr.getBillingFlowInProcess();
    }

    public void sendMessage(int message) {
        tdr.sendMessage(message);
    }

    static public class SkuDetails {
        final public String sku;
        final public LiveData<String> title;
        final public LiveData<String> description;
        final public LiveData<String> price;
        final public int iconDrawableId;

        SkuDetails(@NonNull String sku, PurchaseRepository tdr) {
            this.sku = sku;
            title = tdr.getSkuTitle(sku);
            description = tdr.getSkuDescription(sku);
            price = tdr.getSkuPrice(sku);
            iconDrawableId = skuToResourceIdMap.get(sku);
        }
    }

    public static class MakePurchaseViewModelFactory implements ViewModelProvider.Factory {
        private final PurchaseRepository trivialDriveRepository;

        public MakePurchaseViewModelFactory(PurchaseRepository tdr) {
            trivialDriveRepository = tdr;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            if (modelClass.isAssignableFrom(MakePurchaseViewModel.class)) {
                return (T) new MakePurchaseViewModel(trivialDriveRepository);
            }
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
