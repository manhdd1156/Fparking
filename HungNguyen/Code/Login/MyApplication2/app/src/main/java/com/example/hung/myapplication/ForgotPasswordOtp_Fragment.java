package com.example.hung.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class ForgotPasswordOtp_Fragment extends Fragment implements OnClickListener{
    private static View view;
    private static EditText otp;
    private static EditText newPassword;
    private static EditText reNewPassword;
    private static Button confirm;
    private static ImageView back;



    public ForgotPasswordOtp_Fragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.forgotpasswordotp_layout, container,
                false);
        initViews();
        setListeners();
        return view;
    }
    // Initialize the views
    private void initViews() {

        otp = (EditText)view.findViewById(R.id.otp);
        confirm = (Button) view.findViewById(R.id.confirmBtn);
        newPassword = (EditText) view.findViewById(R.id.password);
        reNewPassword = (EditText) view.findViewById(R.id.confirmPassword);
        back = (ImageView) view.findViewById(R.id.back);

    }
    // Set Listeners over buttons
    private void setListeners() {
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // Replace SignUp Fragment on Back Presses
            case R.id.back:
                new MainActivity().replaceForgotPasswordFragment();
                break;

            case R.id.confirmBtn:

                // Call Submit button task
                break;

        }
    }
}
