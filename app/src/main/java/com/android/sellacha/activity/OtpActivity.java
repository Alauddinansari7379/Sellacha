package com.android.sellacha.activity;
//
//import android.os.Bundle;
//import android.os.CountDownTimer;
//import android.util.Log;
//import android.view.View;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.databinding.DataBindingUtil;
//
//import com.android.sellacha.R;
//import com.android.sellacha.databinding.ActivityOtpBinding;
//import com.android.sellacha.utils.StatusBarUtils;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.FirebaseException;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.PhoneAuthCredential;
//import com.google.firebase.auth.PhoneAuthProvider;
//
//import java.util.Objects;
//import java.util.concurrent.TimeUnit;
//
//public class OtpActivity extends BaseActivity {
//    ActivityOtpBinding binding;
//    String phone;
//    private FirebaseAuth mAuth;
//    String otp;
//    private String verificationId;
//    String phoneNumber;
//
//    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
//    private String verificationCode;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_otp);
//        StatusBarUtils.statusBarColor(this, R.color.primary_bg);
//        mAuth = FirebaseAuth.getInstance();
//        StartFirebaseLogin();
//        phone = (getIntent().getStringExtra("phone"));
//        phoneNumber = "+91" + phone;
//        setCounter();
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                phoneNumber,                     // Phone number to verify
//                60,                           // Timeout duration
//                TimeUnit.SECONDS,                // Unit of timeout
//                OtpActivity.this,        // Activity (for callback binding)
//                mCallback);
//        binding.resendOtp.setOnClickListener(v -> {
//            PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                    phoneNumber,                     // Phone number to verify
//                    60,                           // Timeout duration
//                    TimeUnit.SECONDS,                // Unit of timeout
//                    OtpActivity.this,        // Activity (for callback binding)
//                    mCallback);
//            setCounter();
//
//        });
//
//        binding.backBtn.setOnClickListener(v -> {
//            onBackPressed();
//        });
//        binding.submit.setOnClickListener(v -> {
//            if (Objects.requireNonNull(binding.pinView.getText()).length() != 6) {
//                Toast.makeText(this, "Please Enter OTP", Toast.LENGTH_SHORT).show();
//            } else {
//                otp = binding.pinView.getText().toString();
//
//                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, otp);
//
//              //  SigninWithPhone(credential);
//            }
//        });
//        //   sendVerificationCode(phone);
//    }
//
//
//    @Override
//    public void onBackPressed() {
//
//
//    }
//
//    public void setCounter() {
//        new CountDownTimer(60000, 1000) {
//
//            public void onTick(long millisUntilFinished) {
//                int seconds = (int) (millisUntilFinished / 1000);
//                int minutes = seconds / 60;
//                seconds = seconds % 60;
//                binding.otpCounter.setText("Resend Otp : " + String.format("%02d", minutes)
//                        + ":" + String.format("%02d", seconds));
//                binding.resendOtp.setVisibility(View.GONE);
//                binding.otpCounter.setVisibility(View.VISIBLE);
//                // logic to set the EditText could go here
//            }
//
//            public void onFinish() {
//                cancel();
//                binding.resendOtp.setVisibility(View.VISIBLE);
//                binding.otpCounter.setVisibility(View.GONE);
//                //    binding.otpCounter.setText("OTP send");
//            }
//
//        }.start();
//    }
//
//    private void SigninWithPhone(PhoneAuthCredential credential,String email,String password) {
//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Log.d("TAG", "createUserWithEmail:onComplete:" + task.isSuccessful());
//                        if (!task.isSuccessful()) {
//
//                        }
//                        else
//                        {
//                            errorSnackBar(binding.getRoot(),"Incorrect Otp");
//                        }
//
//                    }
//                });
//    }
//
//
//    private void StartFirebaseLogin() {
//
//        mAuth = FirebaseAuth.getInstance();
//        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//            @Override
//            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
////                Toast.makeText(OtpScreen.this, "verification completed", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onVerificationFailed(FirebaseException e) {
////                Toast.makeText(OtpScreen.this, "verification fialed", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                super.onCodeSent(s, forceResendingToken);
//                verificationCode = s;
////                Toast.makeText(OtpScreen.this, "Code sent", Toast.LENGTH_SHORT).show();
//            }
//        };
//    }
//
//
//}