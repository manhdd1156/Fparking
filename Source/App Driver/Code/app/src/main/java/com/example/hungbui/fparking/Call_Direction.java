package com.example.hungbui.fparking;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Call_Direction extends Fragment {
    @Nullable
    ImageView imageViewCall, imageViewDirection;

    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.call_direction_fragment, container, false);

        imageViewCall = view.findViewById(R.id.imageViewCall);
        imageViewDirection = view.findViewById(R.id.imageViewDirection);

        imageViewCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:01665195808"));
                startActivity(intent);
            }
        });

        imageViewCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
        return view;
    }
}
