package com.android.sellacha.Home.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelOrdeCountJava {
    public class Data {

        @SerializedName("total_orders")
        @Expose
        private String totalOrders;
        @SerializedName("total_pending")
        @Expose
        private String totalPending;
        @SerializedName("total_completed")
        @Expose
        private String totalCompleted;
        @SerializedName("total_processing")
        @Expose
        private String totalProcessing;

        public String getTotalOrders() {
            return totalOrders;
        }

        public void setTotalOrders(String totalOrders) {
            this.totalOrders = totalOrders;
        }

        public String getTotalPending() {
            return totalPending;
        }

        public void setTotalPending(String totalPending) {
            this.totalPending = totalPending;
        }

        public String getTotalCompleted() {
            return totalCompleted;
        }

        public void setTotalCompleted(String totalCompleted) {
            this.totalCompleted = totalCompleted;
        }

        public String getTotalProcessing() {
            return totalProcessing;
        }

        public void setTotalProcessing(String totalProcessing) {
            this.totalProcessing = totalProcessing;
        }

    }

    public class Example {

        @SerializedName("success")
        @Expose
        private Boolean success;
        @SerializedName("data")
        @Expose
        private Data data;
        @SerializedName("message")
        @Expose
        private String message;

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }
}
