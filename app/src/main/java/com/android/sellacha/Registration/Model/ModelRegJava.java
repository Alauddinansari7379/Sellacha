package com.android.sellacha.Registration.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 public class ModelRegJava {

     @SerializedName("errors")
     @Expose
     private String errors;
     @SerializedName("domain")
     @Expose
     private String domain;
     @SerializedName("redirect")
     @Expose
     private Boolean redirect;
     @SerializedName("msg")
     @Expose
     private String msg;

     public String getErrors() {
         return errors;
     }

     public void setErrors(String errors) {
         this.errors = errors;
     }

     public String getDomain() {
         return domain;
     }

     public void setDomain(String domain) {
         this.domain = domain;
     }

     public Boolean getRedirect() {
         return redirect;
     }

     public void setRedirect(Boolean redirect) {
         this.redirect = redirect;
     }

     public String getMsg() {
         return msg;
     }

     public void setMsg(String msg) {
         this.msg = msg;
     }


     @SerializedName("success")
     @Expose
     private Boolean success;
     @SerializedName("data")
     @Expose
     private DataNew data;

     public Boolean getSuccess() {
         return success;
     }

     public void setSuccess(Boolean success) {
         this.success = success;
     }

     public DataNew getData() {
         return data;
     }

     public void setData(DataNew data) {
         this.data = data;
     }

}