package com.android.sellacha.api.model;

public class transactionsDM {

    Double Amount;
    String Payment;
    String Method;
    String Orederno;
    String TransactionId;

    public transactionsDM(Double amount, String payment, String method, String orederno, String transactionId) {
        Amount = amount;
        Payment = payment;
        Method = method;
        Orederno = orederno;
        TransactionId = transactionId;
    }


    public Double getAmount() {
        return Amount;
    }

    public void setAmount(Double amount) {
        Amount = amount;
    }

    public String getPayment() {
        return Payment;
    }

    public void setPayment(String payment) {
        Payment = payment;
    }

    public String getMethod() {
        return Method;
    }

    public void setMethod(String method) {
        Method = method;
    }

    public String getOrederno() {
        return Orederno;
    }

    public void setOrederno(String orederno) {
        Orederno = orederno;
    }

    public String getTransactionId() {
        return TransactionId;
    }

    public void setTransactionId(String transactionId) {
        TransactionId = transactionId;
    }

    public transactionsDM() {
    }
}
