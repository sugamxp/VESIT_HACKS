package com.example.sumeetharyani.asset_manager.GS;

public class Asset {
    private String assetName;
    private int assetQuantity;
    private int price;
    private String userId;
    public Asset() {
    }

    public Asset(String userId, String assetName, int assetQuantity, int price) {
        this.userId = userId;
        this.assetName = assetName;
        this.assetQuantity = assetQuantity;
        this.price = price;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public int getAssetQuantity() {
        return assetQuantity;
    }

    public void setAssetQuantity(int assetQuantity) {
        this.assetQuantity = assetQuantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
