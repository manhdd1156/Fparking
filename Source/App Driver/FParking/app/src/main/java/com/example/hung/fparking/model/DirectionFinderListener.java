package com.example.hung.fparking.model;

import java.util.List;

import com.example.hung.fparking.entity.Route;

public interface DirectionFinderListener {

    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
