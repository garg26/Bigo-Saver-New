package com.bigosaver.neerajyadav.bigosaver.model.membership;

import android.util.Log;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import simplifii.framework.asyncmanager.HttpParamObject;

/**
 * Created by nbansal2211 on 12/12/16.
 */

public class PayuParams implements Serializable {

    public static final String URL = "https://secure.payu.in/_payment";
    private String amount;
    private String txnId;
    private String key = "Dpdk1BmA";
    private String salt = "Io2mnMHJvG";
    private String email;
    private String firstname;
    //    https://www.bigosavers.com/homes/membership_payment_failure
//    https://www.bigosavers.com/homes/membership_payment_success
    private String surl = "https://www.bigosavers.com/homes/membership_payment_success";
    private String furl = "https://www.bigosavers.com/homes/membership_payment_failure";
    private String phone;
    private String service_provider = "payu_paisa";
    private String hash;
    private String productInfo = "Bigo Plan";

    public String getHash() {
        String checkSum3 = "Dpdk1BmA|%s|%s|%s|%s|%s|||||||||||Io2mnMHJvG";
        String hashString = String.format(checkSum3, txnId, amount, productInfo, firstname, email);
        String hash = calculateHash(hashString);
        return hash;
    }

    public HttpParamObject getHttpParam() {
        HttpParamObject obj = new HttpParamObject();
        obj.setUrl(URL);
        obj.setPostMethod();
        obj.addHeader("Authorization", "Dpdk1BmA");
        obj.addParameter("key", key);
        obj.addParameter("txnid", txnId);
        obj.addParameter("amount", amount);
        obj.addParameter("productinfo", productInfo);
        obj.addParameter("firstname", firstname);
        obj.addParameter("email", email);
        obj.addParameter("phone", phone);
        obj.addParameter("surl", surl);
        obj.addParameter("furl", furl);
        obj.addParameter("hash", getHash());
        obj.addParameter("service_provider", "payu_paisa");
        return obj;
    }

    public String getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo;
    }

    private String calculateHash(String hashString) {
        try {
            StringBuilder e = new StringBuilder();
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(hashString.getBytes());
            byte[] mdbytes = messageDigest.digest();
            byte[] var5 = mdbytes;
            int var6 = mdbytes.length;

            for (int var7 = 0; var7 < var6; ++var7) {
                byte hashByte = var5[var7];
                e.append(Integer.toString((hashByte & 255) + 256, 16).substring(1));
            }
            Log.d("Hash", e.toString());
            return e.toString();
        } catch (NoSuchAlgorithmException var9) {

        }

        return "";
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurl() {
        return surl;
    }

    public void setSurl(String surl) {
        this.surl = surl;
    }

    public String getFurl() {
        return furl;
    }

    public void setFurl(String furl) {
        this.furl = furl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getService_provider() {
        return service_provider;
    }

    public void setService_provider(String service_provider) {
        this.service_provider = service_provider;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
