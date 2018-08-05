package com.example.hung.fparking.login;

import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hung.fparking.R;
import com.example.hung.fparking.config.Constants;

public class SignUp_Fragment extends Fragment implements OnClickListener {
    private static View view;
    private static EditText phone, password, confirmPassword;
    private static Button signUpButton;
    private static TextView login;
    private static ImageView back;
    private static FragmentManager fragmentManager;
    private static Animation shakeAnimation;
    public SignUp_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.layout_signup, container, false);
        initViews();
        setListeners();
        return view;
    }
    // Initialize all views
    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();
        phone = (EditText) view.findViewById(R.id.phone);
        password = (EditText) view.findViewById(R.id.password);
        confirmPassword = (EditText) view.findViewById(R.id.confirmPassword);
        signUpButton = (Button) view.findViewById(R.id.signUpBtn);
        login = (TextView) view.findViewById(R.id.already_user);
        back = (ImageView) view.findViewById(R.id.back);
        // Load ShakeAnimation
        shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.shake);

        // Setting text selector over textviews
        XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            login.setTextColor(csl);

        } catch (Exception e) {
        }
    }
    // Set Listeners
    private void setListeners() {
        signUpButton.setOnClickListener(this);
        login.setOnClickListener(this);
        back.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUpBtn:

                // Call checkValidation method
//                checkValidation();
                // Replace confirm register fragment with animation
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frameContainer,
                                new ConfirmRegister_Fragment(),
                                Constants.ConfirmRegister_Fragment).commit();
                break;

            case R.id.already_user:

                // Replace login fragment
                new MainActivity().replaceLoginFragment();
                break;
            // Replace login fragment
            case R.id.back:
                new MainActivity().replaceLoginFragment();
                break;
        }

    }

}

