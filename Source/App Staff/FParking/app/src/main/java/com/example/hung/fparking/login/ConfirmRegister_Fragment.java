package com.example.hung.fparking.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hung.fparking.R;
import com.example.hung.fparking.config.Constants;

public class ConfirmRegister_Fragment extends Fragment implements OnClickListener {
    private static View view;
    private static TextView phoneNumer;
    private static EditText otp;
    private static Button summitRegister;
    private static TextView reSentOtp;
    private static TextView changePhoneNumber;
    private static ImageView back;
    public ConfirmRegister_Fragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_cf_regis, container,
                false);
        initViews();
        setListeners();
        return view;
    }
    // Initialize the views
    private void initViews() {
      phoneNumer = (TextView) view.findViewById(R.id.phoneNumber);
      otp = (EditText)view.findViewById(R.id.otp);
      summitRegister = (Button) view.findViewById(R.id.summitBtn);
      reSentOtp = (TextView) view.findViewById(R.id.reSentOtp);
      changePhoneNumber = (TextView) view.findViewById(R.id.changePhone);
      back = (ImageView) view.findViewById(R.id.backToLoginBtn);

    }
    // Set Listeners over buttons
    private void setListeners() {
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // Replace SignUp Fragment on Back Presses
            case R.id.backToLoginBtn:
                new MainActivity().replaceSignUpFragment();

                break;

            case R.id.nextBtn:

                // Call Submit button task
                break;

        }

    }
}

