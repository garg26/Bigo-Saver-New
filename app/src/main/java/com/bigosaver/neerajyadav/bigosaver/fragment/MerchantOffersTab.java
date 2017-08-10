package com.bigosaver.neerajyadav.bigosaver.fragment;


import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bigosaver.neerajyadav.bigosaver.activity.EULA;
import com.bigosaver.neerajyadav.bigosaver.activity.SubscribeMembershipActivity;
import com.bigosaver.neerajyadav.bigosaver.model.RedeemResponse.RedeemResponse;
import com.bigosaver.neerajyadav.bigosaver.model.User.UpdateProfileResponse;
import com.bigosaver.neerajyadav.bigosaver.model.favourites.SetFavouritesResponse;
import com.bigosaver.neerajyadav.bigosaver.model.merchants.merchantdetail.MerchantDetailResponse;
import com.bigosaver.neerajyadav.bigosaver.model.merchants.merchantoffer.In_plan;
import com.bigosaver.neerajyadav.bigosaver.model.merchants.merchantoffer.MerchantOfferResponse;
import com.bigosaver.neerajyadav.bigosaver.model.merchants.merchantoffer.Transaction;
import com.bigosaver.neerajyadav.bigosaver.model.merchants.merchantoffer.TransactionMap;
import com.bigosaver.neerajyadav.bigosaver.model.offers.FilterOfferResponse;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.bigosavers.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.bigosaver.neerajyadav.bigosaver.model.AdditionalDetails;
import com.dpizarro.pinview.library.PinView;
import com.dpizarro.pinview.library.PinViewSettings;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import me.philio.pinentry.PinEntryView;
import simplifii.framework.activity.BaseActivity;
import simplifii.framework.asyncmanager.HttpParamObject;
import simplifii.framework.fragments.BaseFragment;
import simplifii.framework.utility.AppConstants;
import simplifii.framework.utility.Preferences;
import simplifii.framework.utility.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class MerchantOffersTab extends BaseFragment implements ViewPagerEx.OnPageChangeListener, BaseSliderView.OnSliderClickListener {
    private List<AdditionalDetails> membershipList = new ArrayList<>();
    private List<MerchantOfferResponse> offer = new ArrayList<>();
    private ListView lv_offer;
    private SliderLayout sliderOffres;
    private LinearLayout ll_sMembership, ll_gMembership, ll_pMembership, ll_dMembership,
            ll_default, ll_gold, ll_platinum, ll_signature, ll_offer;
    private CardView cardView;
    private LayoutInflater inflater;
    private com.getbase.floatingactionbutton.FloatingActionButton fabCall, fabShare, fabHeart, fabMenu, fabWebsite;
    private FilterOfferResponse offerResponse;
    private String merchantId = "";
    private String offerId = "";
    private HashMap<String, List<In_plan>> hashMapOffers;
    private HashMap<String, List<TransactionMap>> hashMapRedeemed;
    private String categoryImage;
    private HashMap<String, String> url_maps;
    private MerchantDetailResponse mdr;
    private String phone;
    private String url;
    private UpdateProfileResponse user;
    private Double latitiude, longitude;
    private boolean fav = false;
    private In_plan detail;
    private List<In_plan> defaultList = new ArrayList<>();
    private List<In_plan> goldList = new ArrayList<>();
    private List<In_plan> signatureList = new ArrayList<>();
    private List<In_plan> platinumList = new ArrayList<>();
    private String dialogTitle = "";


    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        if (fav)
            fabHeart.setIcon(R.drawable.isfav);
        this.fav = fav;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static MerchantOffersTab getInstance(String offerId, String category, double latitude, double longitude) {
        MerchantOffersTab merchantOffersTab = new MerchantOffersTab();
        merchantOffersTab.merchantId = offerId;
        merchantOffersTab.categoryImage = category;
        merchantOffersTab.latitiude = latitude;
        merchantOffersTab.longitude = longitude;
        return merchantOffersTab;
    }

    private void loadData() {
        merchantId = Preferences.getData(AppConstants.PREF_KEYS.MERCHANT, "");
        categoryImage = Preferences.getData(AppConstants.PREF_KEYS.CATEGORY_DATA, "");
    }

    public void setMdr(MerchantDetailResponse mdr) {
        this.mdr = mdr;
    }
    //8950392712 JD

    @Override
    public void initViews() {
        loadData();
        sliderOffres = (SliderLayout) findView(R.id.slider_offer);
        sliderOffres.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderOffres.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderOffres.setCustomAnimation(new DescriptionAnimation());

        fabCall = (com.getbase.floatingactionbutton.FloatingActionButton) findView(R.id.action_call);
//        fabShare = (com.getbase.floatingactionbutton.FloatingActionButton) findView(R.id.action_share);
        fabHeart = (com.getbase.floatingactionbutton.FloatingActionButton) findView(R.id.action_heart);
//        fabMenu = (com.getbase.floatingactionbutton.FloatingActionButton) findView(R.id.action_menu);
        fabWebsite = (com.getbase.floatingactionbutton.FloatingActionButton) findView(R.id.action_website);

        cardView = (CardView) findView(R.id.ly_root);
        hashMapOffers = new HashMap<>();
        hashMapRedeemed = new HashMap<>();
        getOfferData();

        inflater = LayoutInflater.from(getActivity());

        ll_default = (LinearLayout) findView(R.id.ll_trial);
        ll_gold = (LinearLayout) findView(R.id.ll_gold);
        ll_platinum = (LinearLayout) findView(R.id.ll_platinum);
        ll_signature = (LinearLayout) findView(R.id.ll_signature);
        ll_offer = (LinearLayout) findView(R.id.ll_offers);

        ll_dMembership = (LinearLayout) findView(R.id.ll_default_membrshp);
        ll_gMembership = (LinearLayout) findView(R.id.ll_gold_membrshp);
        ll_pMembership = (LinearLayout) findView(R.id.ll_platinum_membrshp);
        ll_sMembership = (LinearLayout) findView(R.id.ll_signature_membrshp);

        setOnClickListener(R.id.action_call, R.id.action_heart, R.id.action_website);

        askPermissions();
        getDetailData();
    }


    private void askPermissions() {
        new TedPermission(getActivity())
                .setPermissions(Manifest.permission.CALL_PHONE)
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {

                    }


                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {

                    }
                }).check();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.action_call:
                callMerchant(phone);
                break;
//            case R.id.action_share:
//                break;
            case R.id.action_heart:
                if (!isFav()) {
                    setFavourite(merchantId);
                } else {
                    removeFavourite(merchantId);
                }
                break;
            case R.id.action_website:
                if (!TextUtils.isEmpty(url)) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                }
                break;
        }
    }

    private void getDetailData() {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.MERCHANTDETAIL + merchantId + "/?longitude=" + longitude +
                "&latitude=" + latitiude);
        httpParamObject.setClassType(MerchantDetailResponse.class);
        executeTask(AppConstants.TASKCODES.MERCHANTDETAIL, httpParamObject);
    }

    private void removeFavourite(String merchantId) {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.REMOVEFAVOURITE);
        httpParamObject.addParameter("format", "json");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("merchant", merchantId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        httpParamObject.setJson(jsonObject.toString());
        httpParamObject.setPostMethod();
        httpParamObject.setJSONContentType();
        executeTask(AppConstants.TASKCODES.REMOVEFAVOURITE, httpParamObject);
    }

    private void setFavourite(String merchantId) {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.SETFAVOURITE);
        httpParamObject.addParameter("format", "json");
        httpParamObject.setClassType(SetFavouritesResponse.class);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("merchant", merchantId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        httpParamObject.setJson(jsonObject.toString());
        httpParamObject.setPostMethod();
        httpParamObject.setJSONContentType();
        executeTask(AppConstants.TASKCODES.SETFAVOURITE, httpParamObject);
    }

    private void callMerchant(String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        if (!TextUtils.isEmpty(phone)) {
            intent.setData(Uri.parse(phone));
            startActivity(intent);
        } else
            showToast("Phone number not available");

    }

    private void getOfferData() {
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.BASE_URL + "merchant/" + merchantId + "/offers/");
        httpParamObject.setClassType(MerchantOfferResponse.class);
        executeTask(AppConstants.TASKCODES.MERCHANTOFFER, httpParamObject);
    }

    @Override
    public void onPostExecute(Object response, int taskCode, Object... params) {
        super.onPostExecute(response, taskCode, params);
        if (null == response) {
            showToast("No response");
            return;
        }
        switch (taskCode) {
            case AppConstants.TASKCODES.MERCHANTOFFER:
                MerchantOfferResponse mor = (MerchantOfferResponse) response;
                if (mor != null) {
                    if (mor.getIn_plan() != null && mor.getIn_plan().size() > 0) {
                        setTransactionMap(mor.getIn_plan());
                    } else {
                        ll_offer.setVisibility(View.GONE);
                        cardView.setVisibility(View.GONE);
                    }
                } else {
                    cardView.setVisibility(View.GONE);
                    ll_offer.setVisibility(View.GONE);
                }
                break;
            case AppConstants.TASKCODES.SETFAVOURITE:
                SetFavouritesResponse fr = (SetFavouritesResponse) response;
                if (null != fr) {
                    setFav(true);
                    fabHeart.setIcon(R.drawable.isfav);
                    showToast(fr.getMerchant_name() + " has been added to your favourites.");
                }
                break;
            case AppConstants.TASKCODES.REMOVEFAVOURITE:
                setFav(false);
                fabHeart.setIcon(R.drawable.not_fav);
                showToast(mdr.getName() + " has been removed from your favourites.");
                break;
            case AppConstants.TASKCODES.REDEEMOFFER:
                RedeemResponse redeemResponse = (RedeemResponse) response;
                if (redeemResponse != null) {
                    showConfirmationDialog(R.layout.dialog_bigo_offer, redeemResponse, detail);
                }
                break;
            case AppConstants.TASKCODES.MERCHANTDETAIL:
                MerchantDetailResponse mdr = (MerchantDetailResponse) response;
                if (mdr != null) {
                    if (!TextUtils.isEmpty(mdr.getName()))
                        initToolBar(mdr.getName());
                    setImage(mdr);
                    setPhone("tel:" + mdr.getPhone_outlet());
                    setUrl(mdr.getWebsite());
                    setFav(mdr.getIs_favorite());
                    setMdr(mdr);
                    setData(mdr);
                }
                break;
        }
    }

    private void setTransactionMap(List<In_plan> in_plan) {
        List<In_plan> inPlanList = new ArrayList<>();
        for (int i = 0; i < in_plan.size(); i++) {
            List<Transaction> transactionList = in_plan.get(i).getTransactions();
            List<Transaction> goldTransactionsList = new ArrayList<>();
            List<Transaction> platinumTransactionsList = new ArrayList<>();
            List<Transaction> signatureTransactionsList = new ArrayList<>();
            List<Transaction> defaultTransactionsList = new ArrayList<>();

            if (transactionList != null && transactionList.size() > 0) {
                for (Transaction transaction : transactionList) {
                    if (transaction.getType().toLowerCase().contains("gold")) {
                        goldTransactionsList.add(transaction);
                    } else if (transaction.getType().toLowerCase().contains("platinum")) {
                        platinumTransactionsList.add(transaction);
                    } else if (transaction.getType().toLowerCase().contains("signature")) {
                        signatureTransactionsList.add(transaction);
                    } else if (transaction.getType().toLowerCase().contains("default")) {
                        defaultTransactionsList.add(transaction);
                    }
                }
            }
            Integer totalGoldOffers = in_plan.get(i).getGold_number_offer();
            if (totalGoldOffers != null && totalGoldOffers > 0) {
                for (int goIndex = 0; goIndex < totalGoldOffers; goIndex++) {
                    In_plan inPlanObject = in_plan.get(i).clone();
                    inPlanObject.setType(AppConstants.HASHKEY.GOLD);
                    if (goldTransactionsList.size() > 0) {
                        inPlanObject.setRedeemed(true);
                        inPlanObject.setRedeemptionDate(Util.convertDateFormat(goldTransactionsList.get(0).getDate(),
                                Util.DISCVER_SERVER_DATE_PATTERN, "dd-MMM-yyyy").replace("-", " "));
                        goldTransactionsList.remove(0);
                    } else {
                        inPlanObject.setRedeemed(false);
                        inPlanObject.setRedeemptionDate(null);
                    }
                    inPlanList.add(inPlanObject);
                }
            }
            Integer totalPlatinumOffers = in_plan.get(i).getPlatinum_number_offer();
            if (totalPlatinumOffers != null && totalPlatinumOffers > 0) {
                for (int goIndex = 0; goIndex < totalPlatinumOffers; goIndex++) {
                    In_plan inPlanObject = in_plan.get(i).clone();
                    inPlanObject.setType(AppConstants.HASHKEY.PLATINUM);
                    if (platinumTransactionsList.size() > 0) {
                        inPlanObject.setRedeemed(true);
                        inPlanObject.setRedeemptionDate(Util.convertDateFormat(platinumTransactionsList.get(0).getDate(),
                                Util.DISCVER_SERVER_DATE_PATTERN, "dd-MMM-yyyy").replace("-", " "));
                        platinumTransactionsList.remove(0);
                    } else {
                        inPlanObject.setRedeemed(false);
                        inPlanObject.setRedeemptionDate(null);
                    }
                    inPlanList.add(inPlanObject);
                }
            }
            Integer totalSignatureOffers = in_plan.get(i).getSignature_number_offer();
            if (totalSignatureOffers != null && totalSignatureOffers > 0) {
                for (int goIndex = 0; goIndex < totalSignatureOffers; goIndex++) {
                    In_plan inPlanObject = in_plan.get(i).clone();
                    inPlanObject.setType(AppConstants.HASHKEY.SIGNATURE);
                    if (signatureTransactionsList.size() > 0) {
                        inPlanObject.setRedeemed(true);
                        inPlanObject.setRedeemptionDate(Util.convertDateFormat(signatureTransactionsList.get(0).getDate(),
                                Util.DISCVER_SERVER_DATE_PATTERN, "dd-MMM-yyyy").replace("-", " "));
                        signatureTransactionsList.remove(0);
                    } else {
                        inPlanObject.setRedeemed(false);
                        inPlanObject.setRedeemptionDate(null);
                    }
                    inPlanList.add(inPlanObject);
                }
            }
            Integer totalDefaultOffers = in_plan.get(i).getDefault_number_offer();
            if (totalDefaultOffers != null && totalDefaultOffers > 0) {
                for (int goIndex = 0; goIndex < totalDefaultOffers; goIndex++) {
                    In_plan inPlanObject = in_plan.get(i).clone();
                    inPlanObject.setType(AppConstants.HASHKEY.DEFAULT);
                    if (defaultTransactionsList.size() > 0) {
                        inPlanObject.setRedeemed(true);
                        inPlanObject.setRedeemptionDate(Util.convertDateFormat(defaultTransactionsList.get(0).getDate(),
                                Util.DISCVER_SERVER_DATE_PATTERN, "dd-MMM-yyyy").replace("-", " "));
                        defaultTransactionsList.remove(0);
                    } else {
                        inPlanObject.setRedeemed(false);
                        inPlanObject.setRedeemptionDate(null);
                    }
                    inPlanList.add(inPlanObject);
                }
            }
        }


        for (int i = 0; i < inPlanList.size(); i++) {
            if (inPlanList.get(i).getType().contains(AppConstants.HASHKEY.DEFAULT)) {
                defaultList.add(inPlanList.get(i));
            } else if (inPlanList.get(i).getType().contains(AppConstants.HASHKEY.GOLD)) {
                goldList.add(inPlanList.get(i));
            } else if (inPlanList.get(i).getType().contains(AppConstants.HASHKEY.PLATINUM)) {
                platinumList.add(inPlanList.get(i));
            } else if (inPlanList.get(i).getType().contains(AppConstants.HASHKEY.SIGNATURE)) {
                signatureList.add(inPlanList.get(i));
            }
        }

        setMembershipTypeData(defaultList, goldList, signatureList, platinumList);

//        for (int i = 0; i < in_plan.size(); i++) {
//            List<TransactionMap> transactionDefault = new ArrayList<>();
//            List<TransactionMap> transactionGold = new ArrayList<>();
//            List<TransactionMap> transactionPlatinum = new ArrayList<>();
//            List<TransactionMap> transactionSignature = new ArrayList<>();
//            if (hashMapRedeemed.get(AppConstants.HASHKEY.DEFAULTTRANSACTION) != null)
//                transactionDefault.addAll(hashMapRedeemed.get(AppConstants.HASHKEY.DEFAULTTRANSACTION));
//            if (hashMapRedeemed.get(AppConstants.HASHKEY.GOLDTRANSACTION) != null)
//                transactionGold.addAll(hashMapRedeemed.get(AppConstants.HASHKEY.GOLDTRANSACTION));
//            if (hashMapRedeemed.get(AppConstants.HASHKEY.PLATINUMTRANSACTION) != null)
//                transactionPlatinum.addAll(hashMapRedeemed.get(AppConstants.HASHKEY.PLATINUMTRANSACTION));
//            if (hashMapRedeemed.get(AppConstants.HASHKEY.SIGNATURETRANSACTION) != null)
//                transactionSignature.addAll(hashMapRedeemed.get(AppConstants.HASHKEY.SIGNATURETRANSACTION));
//
//            In_plan merchantResponse = in_plan.get(i);
//            TransactionMap transactionMap = new TransactionMap();
//            for (int j = 0; j < merchantResponse.getTransactions().size(); j++) {
//                if (merchantResponse.getTransactions().get(j).getType().toLowerCase().contains("default")) {
//                    transactionMap.setTransaction(merchantResponse.getTransactions().get(j));
//                    transactionMap.setOfferId(merchantResponse.getId());
//                    transactionDefault.add(transactionMap);
//                } else if (merchantResponse.getTransactions().get(j).getType().toLowerCase().contains("gold")) {
//                    transactionMap.setTransaction(merchantResponse.getTransactions().get(j));
//                    transactionMap.setOfferId(merchantResponse.getId());
//                    transactionGold.add(transactionMap);
//                } else if (merchantResponse.getTransactions().get(j).getType().toLowerCase().contains("platinum")) {
//                    transactionMap.setTransaction(merchantResponse.getTransactions().get(j));
//                    transactionMap.setOfferId(merchantResponse.getId());
//                    transactionPlatinum.add(transactionMap);
//                } else if (merchantResponse.getTransactions().get(j).getType().toLowerCase().contains("signature")) {
//                    transactionMap.setTransaction(merchantResponse.getTransactions().get(j));
//                    transactionMap.setOfferId(merchantResponse.getId());
//                    transactionSignature.add(transactionMap);
//                }
//            }
//            hashMapRedeemed.put(AppConstants.HASHKEY.DEFAULTTRANSACTION, transactionDefault);
//            hashMapRedeemed.put(AppConstants.HASHKEY.GOLDTRANSACTION, transactionGold);
//            hashMapRedeemed.put(AppConstants.HASHKEY.PLATINUMTRANSACTION, transactionPlatinum);
//            hashMapRedeemed.put(AppConstants.HASHKEY.SIGNATURETRANSACTION, transactionSignature);
//        }

//        setMembershipData(in_plan);

    }


    @Override
    public void initToolBar(String title) {
        ((BaseActivity) getActivity()).initToolBar(title);
    }

    public void setImage(MerchantDetailResponse mdr) {
        url_maps = new HashMap<String, String>();
        if (mdr.getImages().size() > 0)
            for (int i = 0; i < mdr.getImages().size(); i++) {
                String url = AppConstants.PAGE_URL.PHOTO_URL + mdr.getImages().get(i).getImage();
                url_maps.put("a" + i, url);
            }
        setImageData(url_maps, mdr);
    }

    private void setImageData(HashMap<String, String> map, MerchantDetailResponse mdr) {
        if (mdr.getImages().size() > 0)
            for (String name : map.keySet()) {
                TextSliderView textSliderView = new TextSliderView(getActivity());
                textSliderView
                        .image(map.get(name))
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(this);

                textSliderView.bundle(new Bundle());
                textSliderView.getBundle()
                        .putString("extra", name);
                sliderOffres.addSlider(textSliderView);
            }
        else {
            for (String name : map.keySet()) {
                int id = getResources().getIdentifier("name1", name, "com.bigosaver.my.bigosaver");
                Drawable drawable = getResources().getDrawable(id);
                TextSliderView textSliderView = new TextSliderView(getActivity());
                textSliderView
                        .image(String.valueOf(drawable))
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(this);

                textSliderView.bundle(new Bundle());
                textSliderView.getBundle()
                        .putString("extra", name);
                sliderOffres.addSlider(textSliderView);
            }
        }
    }

//    private void setMembershipData(List<In_plan> morInPlan) {
//        for (int i = 0; i < morInPlan.size(); i++) {
//            List<In_plan> offerDefault = new ArrayList<>();
//            List<In_plan> offerGold = new ArrayList<>();
//            List<In_plan> offerPlatinum = new ArrayList<>();
//            List<In_plan> offerSignature = new ArrayList<>();
//            List<TransactionMap> transactionDefault = new ArrayList<>();
//            List<TransactionMap> transactionGold = new ArrayList<>();
//            List<TransactionMap> transactionPlatinum = new ArrayList<>();
//            List<TransactionMap> transactionSignature = new ArrayList<>();
//
//            In_plan merchantOfferResponse = morInPlan.get(i);
//
//            if (hashMapRedeemed.get(AppConstants.HASHKEY.DEFAULTTRANSACTION) != null)
//                transactionDefault.addAll(hashMapRedeemed.get(AppConstants.HASHKEY.DEFAULTTRANSACTION));
//            if (hashMapOffers.get(AppConstants.HASHKEY.DEFAULT) != null)
//                offerDefault.addAll(hashMapOffers.get(AppConstants.HASHKEY.DEFAULT));
//            for (int j = 0; j < merchantOfferResponse.getDefault_number_offer(); j++) {
//                In_plan merchant = merchantOfferResponse.clone();
////                offerDefault.add(merchant);
////                int position = offerDefault.size();
//                int listSize = transactionDefault.size();
//                for (int k = 0; k < listSize; k++) {
//                    TransactionMap tMap = transactionDefault.get(k);
//                    if (merchant.getId().compareTo(tMap.getOfferId()) == 0) {
//                        merchant.setRedeemed(true);
//                        merchant.setRedeemptionDate(Util.convertDateFormat(transactionDefault.get(0).getTransaction().getDate(),
//                                Util.DISCVER_SERVER_DATE_PATTERN, "dd-MMM-yyyy").replace("-", " "));
//                        merchant.setType(AppConstants.HASHKEY.DEFAULT);
//                        offerDefault.add(merchant);
//                        transactionDefault.remove(tMap);
//                        listSize = transactionDefault.size();
//                    } else {
//                        offerSignature.add(merchant);
//                    }
//                }
//
////                if (listSize > 0) {
////                    offerDefault.get(position - 1).setRedeemed(true);
////                    offerDefault.get(position - 1).setRedeemptionDate(Util.convertDateFormat(transactionDefault.get(0).getTransaction().getDate(),
////                            Util.DISCVER_SERVER_DATE_PATTERN, "dd-MMM-yyyy").replace("-", " "));
////                    transactionDefault.remove(0);
////                    hashMapRedeemed.put(AppConstants.HASHKEY.DEFAULTTRANSACTION, transactionDefault);
////                }
//            }
//            hashMapOffers.put(AppConstants.HASHKEY.DEFAULT, offerDefault);
//
//            if (hashMapRedeemed.get(AppConstants.HASHKEY.GOLDTRANSACTION) != null)
//                transactionGold.addAll(hashMapRedeemed.get(AppConstants.HASHKEY.GOLDTRANSACTION));
//            if (hashMapOffers.get(AppConstants.HASHKEY.GOLD) != null)
//                offerGold.addAll(hashMapOffers.get(AppConstants.HASHKEY.GOLD));
//            for (int j = 0; j < merchantOfferResponse.getGold_number_offer(); j++) {
//                In_plan merchant = merchantOfferResponse.clone();
////                offerGold.add(merchant);
////                int position = offerGold.size();
////                int listSize = transactionGold.size();
////                if (listSize > 0) {
////                    offerGold.get(position - 1).setRedeemed(true);
////                    offerGold.get(position - 1).setRedeemptionDate(Util.convertDateFormat(transactionGold.get(0).getTransaction().getDate(),
////                            Util.DISCVER_SERVER_DATE_PATTERN, "dd-MMM-yyyy").replace("-", " "));
////                    transactionGold.remove(0);
////                    hashMapRedeemed.put(AppConstants.HASHKEY.GOLDTRANSACTION, transactionGold);
////                }
//                int listSize = transactionGold.size();
//                for (int k = 0; k < listSize; k++) {
//                    TransactionMap tMap = transactionGold.get(k);
//                    if (merchant.getId().compareTo(tMap.getOfferId()) == 0) {
//                        merchant.setRedeemed(true);
//                        merchant.setRedeemptionDate(Util.convertDateFormat(transactionGold.get(0).getTransaction().getDate(),
//                                Util.DISCVER_SERVER_DATE_PATTERN, "dd-MMM-yyyy").replace("-", " "));
//                        merchant.setType(AppConstants.HASHKEY.GOLD);
//                        offerGold.add(merchant);
//                        transactionGold.remove(tMap);
//                        listSize = transactionGold.size();
//                    } else {
//                        offerGold.add(merchant);
//                    }
//                }
//
//            }
//            hashMapOffers.put(AppConstants.HASHKEY.GOLD, offerGold);
//
//            if (hashMapRedeemed.get(AppConstants.HASHKEY.PLATINUMTRANSACTION) != null)
//                transactionPlatinum.addAll(hashMapRedeemed.get(AppConstants.HASHKEY.PLATINUMTRANSACTION));
//            if (hashMapOffers.get(AppConstants.HASHKEY.PLATINUM) != null)
//                offerPlatinum.addAll(hashMapOffers.get(AppConstants.HASHKEY.PLATINUM));
//            for (int j = 0; j < merchantOfferResponse.getPlatinum_number_offer(); j++) {
//                In_plan merchant = merchantOfferResponse.clone();
//                offerPlatinum.add(merchant);
////                int position = offerPlatinum.size();
////                int listSize = transactionPlatinum.size();
////                if (listSize > 0) {
////                    offerPlatinum.get(position - 1).setRedeemed(true);
////                    offerPlatinum.get(position - 1).setRedeemptionDate(Util.convertDateFormat(transactionPlatinum.get(0).getTransaction().getDate(),
////                            Util.DISCVER_SERVER_DATE_PATTERN, "dd-MMM-yyyy").replace("-", " "));
////                    transactionPlatinum.remove(0);
////                    hashMapRedeemed.put(AppConstants.HASHKEY.PLATINUMTRANSACTION, transactionPlatinum);
////                }
//                int listSize = transactionPlatinum.size();
//                for (int k = 0; k < listSize; k++) {
//                    TransactionMap tMap = transactionPlatinum.get(k);
//                    if (merchant.getId().compareTo(tMap.getOfferId()) == 0) {
//                        merchant.setRedeemed(true);
//                        merchant.setRedeemptionDate(Util.convertDateFormat(transactionPlatinum.get(0).getTransaction().getDate(),
//                                Util.DISCVER_SERVER_DATE_PATTERN, "dd-MMM-yyyy").replace("-", " "));
//                        merchant.setType(AppConstants.HASHKEY.PLATINUM);
//                        offerPlatinum.add(merchant);
//                        transactionPlatinum.remove(tMap);
//                        listSize = transactionPlatinum.size();
//                    } else {
//                        offerPlatinum.add(merchant);
//                    }
//                }
//            }
//            hashMapOffers.put(AppConstants.HASHKEY.PLATINUM, offerPlatinum);
//
//            if (hashMapRedeemed.get(AppConstants.HASHKEY.SIGNATURETRANSACTION) != null)
//                transactionSignature.addAll(hashMapRedeemed.get(AppConstants.HASHKEY.SIGNATURETRANSACTION));
//            if (hashMapOffers.get(AppConstants.HASHKEY.SIGNATURE) != null)
//                offerSignature.addAll(hashMapOffers.get(AppConstants.HASHKEY.SIGNATURE));
//            for (int j = 0; j < merchantOfferResponse.getSignature_number_offer(); j++) {
//                In_plan merchant = merchantOfferResponse.clone();
////                offerSignature.add(merchant);
////                int position = offerSignature.size();
////                int listSize = transactionSignature.size();
////                if (listSize > 0) {
////                    offerSignature.get(position - 1).setRedeemed(true);
////                    offerSignature.get(position - 1).setRedeemptionDate(Util.convertDateFormat(transactionSignature.get(0).getTransaction().getDate(),
////                            Util.DISCVER_SERVER_DATE_PATTERN, "dd-MMM-yyyy").replace("-", " "));
////                    transactionSignature.remove(0);
////                    hashMapRedeemed.put(AppConstants.HASHKEY.SIGNATURETRANSACTION, transactionSignature);
////                }
//                for (int k = 0; k < transactionSignature.size(); k++) {
//                    TransactionMap tMap = transactionSignature.get(k);
//                    if (merchant.getId().compareTo(tMap.getOfferId()) == 0) {
//                        merchant.setRedeemed(true);
//                        merchant.setRedeemptionDate(Util.convertDateFormat(transactionSignature.get(0).getTransaction().getDate(),
//                                Util.DISCVER_SERVER_DATE_PATTERN, "dd-MMM-yyyy").replace("-", " "));
//                        merchant.setType(AppConstants.HASHKEY.SIGNATURE);
//                        offerSignature.add(merchant);
//                        transactionSignature.remove(tMap);
//                    } else {
//                        offerSignature.add(merchant);
//                    }
//                }
//            }
//            hashMapOffers.put(AppConstants.HASHKEY.SIGNATURE, offerSignature);
//        }
//        setMembershipTypeData();
//    }

    private void setMembershipTypeData(List<In_plan> defaultList, List<In_plan> goldList, List<In_plan> signatureList, List<In_plan> platinumList) {
        user = UpdateProfileResponse.getRunningInstance();
        if (user.getMembership_type().toLowerCase().contains("signature")) {
            setSignatureData(signatureList, 0);
        } else if (user.getMembership_type().toLowerCase().contains("platinum")) {
            setSignatureData(signatureList, 1);
            setDataPltnum(platinumList, 0);
        } else if (user.getMembership_type().toLowerCase().contains("gold")) {
            setSignatureData(signatureList, 1);
            setDataPltnum(platinumList, 1);
            setDataGold(goldList, 0);
        } else {
            setDefaultData(defaultList, 0);
            setSignatureData(signatureList, 1);
            setDataPltnum(platinumList, 1);
            setDataGold(goldList, 1);
        }
    }

    private void setData(MerchantDetailResponse mdr) {
        StringBuilder stringBuilder = new StringBuilder();
        if (!TextUtils.isEmpty(mdr.getPlace_name()))
            stringBuilder.append(mdr.getPlace_name() + ", ");
        if (!TextUtils.isEmpty(mdr.getArea_name()))
            stringBuilder.append(mdr.getArea_name());
        if (!TextUtils.isEmpty(stringBuilder.toString()))
            setText(R.id.tv_username, stringBuilder.toString());

        if (null != mdr.getDistance())
            setText(R.id.tv_place, getDistance(mdr.getDistance()));

    }

    private String getDistance(Double distance) {
        if (distance < 1) {
            distance = distance * 1000;
            return distance.intValue() + " Meters";
        } else {
            return distance.intValue() + " Km";
        }
    }

    private void enableOfferOptions(In_plan details, View view) {
        if (details.getOffer_option().contains("NEW_OFFER"))
            view.findViewById(R.id.iv_new).setVisibility(View.VISIBLE);
        if (details.getOffer_option().contains("MONTHLY_OFFER"))
            view.findViewById(R.id.iv_monthly).setVisibility(View.VISIBLE);
        if (details.getOffer_option().contains("BIGO_DRINK"))
            view.findViewById(R.id.iv_drink).setVisibility(View.VISIBLE);
    }

    private void loadCatImage(String categoryAPI, View view) {
        ImageView ivCat = (ImageView) view.findViewById(R.id.iv_offer);
        if (!TextUtils.isEmpty(categoryAPI))
            Picasso.with(getActivity()).load(AppConstants.PAGE_URL.PHOTO_URL + categoryAPI)
                    .placeholder(R.color.white).into(ivCat);

    }

    private String findDialogTitle(In_plan details) {
        final StringBuilder stringBuilder = new StringBuilder();
        if (details.getOffer_type().compareTo("B2G2") == 0) {
            stringBuilder.append("Buy 2 Get 2");
        } else if (details.getOffer_type().compareTo("B1G1") == 0) {
            stringBuilder.append("Buy 1 Get 1");
        } else if (details.getOffer_type().contains("OFF")) {
            stringBuilder.append(details.getSavings()).append(" OFF");
        }
        if (stringBuilder != null) {
            return stringBuilder.toString();
        }
        return null;
    }

    private void setDefaultData(List<In_plan> defaultMor, final int action) {
        if (defaultMor == null || defaultMor.size() == 0) {
            ll_default.setVisibility(View.GONE);
            return;
        }
        cardView.setVisibility(View.VISIBLE);
        ll_default.setVisibility(View.VISIBLE);
        for (int y = 0; y < defaultMor.size(); y++) {
            final In_plan details = defaultMor.get(y);
            if (details != null) {
                View view = inflater.inflate(R.layout.row_offers, null);
                LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.ll_card_envelope);
                if (!TextUtils.isEmpty(details.getTitle()))
                    setText(R.id.tv_offer_name, details.getTitle(), view);
                if (user.getMembership_type().toLowerCase().contains(AppConstants.HASHKEY.DEFAULT)) {
                    ImageView iv = (ImageView) findView(R.id.iv_default);
                    iv.setImageResource(R.drawable.appicon);
                    if (!details.getRedeemed() || details.getOffer_option().toLowerCase().contains(getString(R.string.monthly))) {
                        if (details.getIs_blackout_day()) {
                            setText(R.id.tv_desc, getString(R.string.not_avaliable_today), view);
                        } else if (!TextUtils.isEmpty(details.getEnd_date()))
                            setText(R.id.tv_desc, getString(R.string.valid_till) + Util.convertDateFormat(details.getEnd_date(), getString(R.string.server_date_format),
                                    getString(R.string.required_date_format)).replace("-", " "), view);
                    } else {
                        view.setEnabled(false);
                        linearLayout.setBackgroundColor(getResourceColor(R.color.off_white));
                        setText(R.id.tv_desc, getString(R.string.redeemed_on) + details.getRedeemptionDate(), view);
                    }
                } else
                    setText(R.id.tv_desc, getString(R.string.subscribe_button_text), view);

                if (details.getOffer_type().compareTo(getString(R.string.b2g2)) == 0) {
                    view.findViewById(R.id.iv_medal).setBackgroundResource(R.drawable.b2g2default);
                } else if (details.getOffer_type().compareTo(getString(R.string.b1g1)) == 0) {
                    view.findViewById(R.id.iv_medal).setBackgroundResource(R.drawable.bigodefault);
                } else if (details.getOffer_type().contains(getString(R.string.off))) {
                    view.findViewById(R.id.iv_medal).setBackgroundResource(R.drawable.offdefault);
                }

                String type = findDialogTitle(details);
                if (!TextUtils.isEmpty(type)) {
                    setText(R.id.tv_medal_text, type, view);
                }

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (details.getIs_blackout_day())
                            showToast(getString(R.string.not_avaliable_today));
                        else if (details.getOffer_option().toLowerCase().contains(getString(R.string.drink))) {
                            if (details.getNeed_activate_bigo())
                                showToast(getString(R.string.activate_bigo_drinks));
                            else {
                                dialogTitle = findDialogTitle(details);
                                showDialog(R.layout.redeem_pizza_offer, details, action, dialogTitle);
                            }
                        } else {
                            dialogTitle = findDialogTitle(details);
                            showDialog(R.layout.redeem_pizza_offer, details, action, dialogTitle);
                        }
                    }
                });
                enableOfferOptions(details, view);
                loadCatImage(categoryImage, view);
                ll_dMembership.addView(view);
            }
        }
    }

    private void setDataGold(List<In_plan> goldMor, final int action) {
        if (goldMor == null || goldMor.size() == 0) {
            ll_gold.setVisibility(View.GONE);
            return;
        }
        cardView.setVisibility(View.VISIBLE);
        ll_gold.setVisibility(View.VISIBLE);
        for (int y = 0; y < goldMor.size(); y++) {
            final In_plan details = goldMor.get(y);
            if (details != null) {
                View view = inflater.inflate(R.layout.row_offers, null);
                LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.ll_card_envelope);
                if (!TextUtils.isEmpty(details.getTitle()))
                    setText(R.id.tv_offer_name, details.getTitle(), view);
                if (user.getMembership_type().toLowerCase().contains("gold")) {
                    ImageView iv = (ImageView) findView(R.id.iv_gold);
                    iv.setImageResource(R.drawable.appicon);
                    if (!details.getRedeemed() || details.getOffer_option().toLowerCase().contains(getString(R.string.monthly))) {
                        if (details.getIs_blackout_day()) {
                            setText(R.id.tv_desc, getString(R.string.not_avaliable_today), view);
                        } else if (!TextUtils.isEmpty(details.getEnd_date()))
                            setText(R.id.tv_desc, getString(R.string.valid_till) + Util.convertDateFormat(details.getEnd_date(), getString(R.string.server_date_format),
                                    getString(R.string.required_date_format)).replace("-", " "), view);
                    } else {
                        view.setEnabled(false);
                        linearLayout.setBackgroundColor(getResourceColor(R.color.off_white));
                        setText(R.id.tv_desc, getString(R.string.redeemed_on) + details.getRedeemptionDate(), view);
                    }
                } else
                    setText(R.id.tv_desc, getString(R.string.subscribe_button_text), view);

                if (details.getOffer_type().compareTo(getString(R.string.b2g2)) == 0) {
                    view.findViewById(R.id.iv_medal).setBackgroundResource(R.drawable.b2g2gold);
                } else if (details.getOffer_type().compareTo(getString(R.string.b1g1)) == 0) {
                    view.findViewById(R.id.iv_medal).setBackgroundResource(R.drawable.bigogold);
                } else if (details.getOffer_type().contains(getString(R.string.off))) {
                    view.findViewById(R.id.iv_medal).setBackgroundResource(R.drawable.offgold);
                }

                String type = findDialogTitle(details);
                if (!TextUtils.isEmpty(type)) {
                    setText(R.id.tv_medal_text, type, view);
                }

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (details.getIs_blackout_day())
                            showToast(getString(R.string.not_avaliable_today));
                        else if (details.getOffer_option().toLowerCase().contains("drink")) {
                            if (details.getNeed_activate_bigo())
                                showToast(getString(R.string.activate_bigo_drinks));
                            else {
                                dialogTitle = findDialogTitle(details);
                                showDialog(R.layout.redeem_pizza_offer, details, action, dialogTitle);
                            }
                        } else {
                            dialogTitle = findDialogTitle(details);
                            showDialog(R.layout.redeem_pizza_offer, details, action, dialogTitle);
                        }
                    }
                });
                enableOfferOptions(details, view);
                loadCatImage(categoryImage, view);
                ll_gMembership.addView(view);
            }
        }
    }

    private void setDataPltnum(List<In_plan> platMor, final int action) {
        if (platMor == null || platMor.size() == 0) {
            ll_platinum.setVisibility(View.GONE);
            return;
        }
        cardView.setVisibility(View.VISIBLE);
        ll_platinum.setVisibility(View.VISIBLE);
        for (int y = 0; y < platMor.size(); y++) {
            final In_plan details = platMor.get(y);
            if (details != null) {
                View view = inflater.inflate(R.layout.row_offers, null);
                LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.ll_card_envelope);
                if (!TextUtils.isEmpty(details.getTitle()))
                    setText(R.id.tv_offer_name, details.getTitle(), view);

                if (user.getMembership_type().toLowerCase().contains("platinum")) {
                    ImageView iv = (ImageView) findView(R.id.iv_platinum);
                    iv.setImageResource(R.drawable.appicon);
                    if (!details.getRedeemed() || details.getOffer_option().toLowerCase().contains(getString(R.string.monthly))) {
                        if (details.getIs_blackout_day()) {
                            setText(R.id.tv_desc, getString(R.string.not_avaliable_today), view);
                        } else if (!TextUtils.isEmpty(details.getEnd_date()))
                            setText(R.id.tv_desc, getString(R.string.valid_till) + Util.convertDateFormat(details.getEnd_date(), getString(R.string.server_date_format),
                                    getString(R.string.required_date_format)).replace("-", " "), view);
                    } else {
                        view.setEnabled(false);
                        linearLayout.setBackgroundColor(getResourceColor(R.color.off_white));
                        setText(R.id.tv_desc, getString(R.string.redeemed_on) + details.getRedeemptionDate(), view);
                    }
                } else
                    setText(R.id.tv_desc, getString(R.string.subscribe_button_text), view);

                if (details.getOffer_type().compareTo(getString(R.string.b2g2)) == 0) {
                    view.findViewById(R.id.iv_medal).setBackgroundResource(R.drawable.b2g2platinum);
                } else if (details.getOffer_type().compareTo(getString(R.string.b1g1)) == 0) {
                    view.findViewById(R.id.iv_medal).setBackgroundResource(R.drawable.bigoplatinum);
                } else if (details.getOffer_type().contains(getString(R.string.off))) {
                    view.findViewById(R.id.iv_medal).setBackgroundResource(R.drawable.offplatinum);
                }

                String type = findDialogTitle(details);
                if (!TextUtils.isEmpty(type)) {
                    setText(R.id.tv_medal_text, type, view);
                }

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (details.getIs_blackout_day())
                            showToast(getString(R.string.not_avaliable_today));
                        else if (details.getOffer_option().toLowerCase().contains("drink")) {
                            if (details.getNeed_activate_bigo())
                                showToast(getString(R.string.activate_bigo_drinks));
                            else {
                                dialogTitle = findDialogTitle(details);
                                showDialog(R.layout.redeem_pizza_offer, details, action, dialogTitle);
                            }
                        } else {
                            dialogTitle = findDialogTitle(details);
                            showDialog(R.layout.redeem_pizza_offer, details, action, dialogTitle);
                        }
                    }
                });
                enableOfferOptions(details, view);
                loadCatImage(categoryImage, view);
                ll_pMembership.addView(view);
            }
        }
    }

    private void setSignatureData(List<In_plan> signatureMor, final int action) {
        if (signatureMor == null || signatureMor.size() == 0) {
            ll_signature.setVisibility(View.GONE);
            return;
        }
        cardView.setVisibility(View.VISIBLE);
        ll_signature.setVisibility(View.VISIBLE);
        for (int y = 0; y < signatureMor.size(); y++) {
            final In_plan details = signatureMor.get(y);
            if (details != null) {
                View view = inflater.inflate(R.layout.row_offers, null);
                LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.ll_card_envelope);
                if (!TextUtils.isEmpty(details.getTitle()))
                    setText(R.id.tv_offer_name, details.getTitle(), view);
                if (user.getMembership_type().toLowerCase().contains("signature")) {
                    ImageView iv = (ImageView) findView(R.id.iv_signature);
                    iv.setImageResource(R.drawable.appicon);
                    if (!details.getRedeemed() || details.getOffer_option().toLowerCase().contains(getString(R.string.monthly))) {
                        if (details.getIs_blackout_day()) {
                            setText(R.id.tv_desc, getString(R.string.not_avaliable_today), view);
                        } else if (!TextUtils.isEmpty(details.getEnd_date()))
                            setText(R.id.tv_desc, getString(R.string.valid_till) + Util.convertDateFormat(details.getEnd_date(), getString(R.string.server_date_format),
                                    getString(R.string.required_date_format)).replace("-", " "), view);
                    } else {
                        view.setEnabled(false);
                        linearLayout.setBackgroundColor(getResourceColor(R.color.off_white));
                        setText(R.id.tv_desc, getString(R.string.redeemed_on) + details.getRedeemptionDate(), view);
                    }
                } else
                    setText(R.id.tv_desc, getString(R.string.subscribe_button_text), view);

                if (details.getOffer_type().compareTo(getString(R.string.b2g2)) == 0) {
                    view.findViewById(R.id.iv_medal).setBackgroundResource(R.drawable.b2g2signature);
                } else if (details.getOffer_type().compareTo(getString(R.string.b1g1)) == 0) {
                    view.findViewById(R.id.iv_medal).setBackgroundResource(R.drawable.bigosignature);
                } else if (details.getOffer_type().contains(getString(R.string.off))) {
                    view.findViewById(R.id.iv_medal).setBackgroundResource(R.drawable.offsignature);
                }

                String type = findDialogTitle(details);
                if (!TextUtils.isEmpty(type)) {
                    setText(R.id.tv_medal_text, type, view);
                }

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (details.getIs_blackout_day())
                            showToast(getString(R.string.not_avaliable_today));
                        else if (details.getOffer_option().toLowerCase().contains("drink")) {
                            if (details.getNeed_activate_bigo())
                                showToast(getString(R.string.activate_bigo_drinks));
                            else {
                                dialogTitle = findDialogTitle(details);
                                showDialog(R.layout.redeem_pizza_offer, details, action, dialogTitle);
                            }
                        } else {
                            dialogTitle = findDialogTitle(details);
                            showDialog(R.layout.redeem_pizza_offer, details, action, dialogTitle);
                        }
                    }
                });
                enableOfferOptions(details, view);
                loadCatImage(categoryImage, view);
                ll_sMembership.addView(view);
            }
        }
    }

    private void showDialog(int redeem_offer, final In_plan details, final int action, final String dialogTitle) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(redeem_offer);

        TextView tvDialogTitle = (TextView) dialog.findViewById(R.id.font_tv_title);
        TextView tv_tnc = (TextView) dialog.findViewById(R.id.font_tv_footer);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_title);
        TextView tvDesc = (TextView) dialog.findViewById(R.id.tv_offer_desc);
        TextView tvSaving = (TextView) dialog.findViewById(R.id.tv_saving);
        TextView tvExpiration = (TextView) dialog.findViewById(R.id.tv_expiration_date);
        TextView tvCustomTerm = (TextView) dialog.findViewById(R.id.tv_custom_term);
        ImageView closeButton = (ImageView) dialog.findViewById(R.id.iv_cancel_redeem);
        ImageView ivMerchant = (ImageView) dialog.findViewById(R.id.iv_merchant);
        Button btnRedeem = (Button) dialog.findViewById(R.id.btn_redeem);
        Button btnSubscribe = (Button) dialog.findViewById(R.id.btn_subscribe);
        SpannableString spannableString = new SpannableString(getString(R.string.offers_are_sub));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Bundle bundle = new Bundle();
                bundle.putBoolean(AppConstants.BUNDLE_KEYS.RULES, true);
                startNextActivity(bundle, EULA.class);
            }

        };
        spannableString.setSpan(new UnderlineSpan(), 24, 42, 0);
        spannableString.setSpan(clickableSpan, 24, 42, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)), 24, 42, 0);
        tv_tnc.setMovementMethod(LinkMovementMethod.getInstance());
        tv_tnc.setText(spannableString);

        if (!TextUtils.isEmpty(dialogTitle))
            tvDialogTitle.setText(dialogTitle);
        else tvDialogTitle.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(details.getTitle()))
            tvTitle.setText(details.getTitle());
        else tvTitle.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(details.getMerchant_logo()))
            Picasso.with(getActivity()).load(AppConstants.PAGE_URL.PHOTO_URL + details.getMerchant_logo())
                    .placeholder(R.drawable.appicon)
                    .into(ivMerchant);

        if (!TextUtils.isEmpty(details.getSavings()))
            tvSaving.setText(details.getSavings());
        else tvSaving.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(details.getEnd_date()))
            tvExpiration.setText(Util.convertDateFormat(details.getEnd_date(), "yyyy-MM-dd", "dd-MMM-yyyy").replace("-", " "));
        else tvExpiration.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(details.getDescription()))
            tvDesc.setText(details.getDescription());
        else tvDesc.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(details.getCustom_terms()))
            tvCustomTerm.setText(details.getCustom_terms());
        else
            tvCustomTerm.setVisibility(View.GONE);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        if (action == 0) {
            if (details.getIs_default_limit_exceeded()) {
                btnRedeem.setVisibility(View.GONE);
                btnSubscribe.setVisibility(View.VISIBLE);
            } else {
                btnSubscribe.setVisibility(View.GONE);
                btnRedeem.setVisibility(View.VISIBLE);
            }
        } else {
            btnRedeem.setVisibility(View.GONE);
            btnSubscribe.setVisibility(View.VISIBLE);
        }

        btnRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogOffer(R.layout.redeem_offer, details, dialogTitle);
                dialog.dismiss();
            }
        });

        btnSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNextActivity(SubscribeMembershipActivity.class);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showDialogOffer(int redeem_offer, final In_plan details, final String dialogTitle) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(redeem_offer);

        TextView tvDialogTitle = (TextView) dialog.findViewById(R.id.font_tv_title);
        TextView tv_tnc = (TextView) dialog.findViewById(R.id.font_tv_footer);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_title);
        TextView tvDesc = (TextView) dialog.findViewById(R.id.tv_ask_merchant);
        TextView tvSaving = (TextView) dialog.findViewById(R.id.tv_saving);
        final PinEntryView pinView = (PinEntryView) dialog.findViewById(R.id.pinView_redeem_otp);
//        PinViewSettings pinViewSettings = new PinViewSettings.Builder()
//                .withMaskPassword(true)
//                .withKeyboardMandatory(true)
//                .withSplit(null)
//                .withNumberPinBoxes(4)
//                .withNativePinBox(false)
//                .build();
//        pinView.setSettings(pinViewSettings);
        ImageView closeButton = (ImageView) dialog.findViewById(R.id.iv_cancel_redeem);
        ImageView ivMerchant = (ImageView) dialog.findViewById(R.id.iv_merchant);
        Button btnSubmit = (Button) dialog.findViewById(R.id.btn_submit_pin);
        final String[] pin = new String[1];
        SpannableString spannableString = new SpannableString(getString(R.string.offers_are_sub));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Bundle bundle = new Bundle();
                bundle.putBoolean(AppConstants.BUNDLE_KEYS.RULES, true);
                startNextActivity(bundle, EULA.class);
            }

        };
        spannableString.setSpan(new UnderlineSpan(), 24, 42, 0);
        spannableString.setSpan(clickableSpan, 24, 42, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)), 24, 42, 0);
        tv_tnc.setMovementMethod(LinkMovementMethod.getInstance());
        tv_tnc.setText(spannableString);

        if (!TextUtils.isEmpty(dialogTitle))
            tvDialogTitle.setText(dialogTitle);
        else tvDialogTitle.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(details.getTitle())) {
            tvTitle.setText(details.getTitle());
        } else tvTitle.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(details.getMerchant_name()))
            tvDesc.setText("Please ask " + details.getMerchant_name() + " to enter their code");
        else tvDesc.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(details.getMerchant_logo()))
            Picasso.with(getActivity()).load(AppConstants.PAGE_URL.PHOTO_URL + details.getMerchant_logo())
                    .placeholder(R.drawable.appicon)
                    .into(ivMerchant);

        if (!TextUtils.isEmpty(details.getSavings()))
            tvSaving.setText(details.getSavings());
        else tvSaving.setVisibility(View.GONE);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                hideKeyboard();
            }
        });

        pinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                pin[0] = pinView.getText().toString();
//                if (pin.length() == 4) {
//                    if (isUser) {
//                        if (userPin.equals(pin)) {
//                            dialog.dismiss();
//                            showPinDialog(offerData, userPin, false);
//                        } else {
//                            showToast(getString(R.string.wrong_pin));
//                        }
//                    } else {
//                        dialog.dismiss();
//                        sendOfferPin(offerData, userPin, pin);
//                    }
//                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(pin[0]) && pin[0].length() == 4) {
                    redeemOffer(pin[0], details);
                    hideKeyboard();
                    dialog.dismiss();
                } else
                    showToast(getString(R.string.pin_empty_message));
            }
        });
        dialog.show();

        hideKeyboard();

        btnSubmit.setFocusable(true);
    }

    private void showConfirmationDialog(int redeem_offer, RedeemResponse redeemResponse, In_plan details) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(redeem_offer);

        ImageView closeButton = (ImageView) dialog.findViewById(R.id.iv_cancel_redeem);
        TextView tvReferenceNo = (TextView) dialog.findViewById(R.id.tv_reference_no);
        TextView tvSaved = (TextView) dialog.findViewById(R.id.tv_saved_upto);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_title);
        ImageView ivMerchant = (ImageView) dialog.findViewById(R.id.iv_merchant);
        TextView tvDialogTitle = (TextView) dialog.findViewById(R.id.font_tv_title);

//        String type = findDialogTitle(details);

        if (!TextUtils.isEmpty(dialogTitle))
            tvDialogTitle.setText(dialogTitle);
        else tvDialogTitle.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(details.getTitle())) {
            tvTitle.setText(details.getTitle());
        } else tvTitle.setVisibility(View.GONE);


        if (!TextUtils.isEmpty(details.getMerchant_logo()))
            Picasso.with(getActivity()).load(AppConstants.PAGE_URL.PHOTO_URL + details.getMerchant_logo())
                    .placeholder(R.drawable.appicon)
                    .into(ivMerchant);

        if (!TextUtils.isEmpty(redeemResponse.getReference_number()))
            tvReferenceNo.setText(redeemResponse.getReference_number());
        else tvReferenceNo.setVisibility(View.GONE);

        if (redeemResponse.getSavings() != null)
            tvSaved.setText(parseInt(redeemResponse.getSavings()));
        else tvSaved.setVisibility(View.GONE);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                defaultList.clear();
                goldList.clear();
                platinumList.clear();
                signatureList.clear();
                ll_sMembership.removeAllViews();
                ll_gMembership.removeAllViews();
                ll_pMembership.removeAllViews();
                ll_dMembership.removeAllViews();
                getOfferData();
            }
        });
        dialog.show();
    }

    private String parseInt(Integer savings) {
        if (savings != null)
            return "INR " + savings.toString();
        return "0";
    }

    private void redeemOffer(String s, In_plan details) {
        detail = details.clone();
        HttpParamObject httpParamObject = new HttpParamObject();
        httpParamObject.setUrl(AppConstants.PAGE_URL.REDEEMOFFER + details.getId() + "/redeem/");
        httpParamObject.setPostMethod();
        httpParamObject.setJSONContentType();
        httpParamObject.setClassType(RedeemResponse.class);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pin", s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        httpParamObject.setJson(jsonObject.toString());
        executeTask(AppConstants.TASKCODES.REDEEMOFFER, httpParamObject);
    }


    @Override
    public int getViewID() {
        return R.layout.fragment_offers;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d("slider", "page changed" + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
}
