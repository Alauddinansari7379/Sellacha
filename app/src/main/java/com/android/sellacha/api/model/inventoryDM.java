package com.android.sellacha.api.model;

public class inventoryDM {
    public inventoryDM(int productImage, String productName, String sku, String stockManage, String status) {
        this.productImage = productImage;
        this.productName = productName;
        this.sku = sku;
        this.stockManage = stockManage;
        this.status = status;
    }

    int productImage;
    String productName;
    String sku;
    String stockManage;
    String status;

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getStockManage() {
        return stockManage;
    }

    public void setStockManage(String stockManage) {
        this.stockManage = stockManage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public inventoryDM() {
    }
}
