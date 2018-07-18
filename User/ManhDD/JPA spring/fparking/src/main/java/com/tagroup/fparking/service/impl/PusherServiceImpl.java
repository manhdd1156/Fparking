package com.tagroup.fparking.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pusher.rest.Pusher;
import com.tagroup.fparking.service.PusherService;

@Service
public class PusherServiceImpl implements PusherService {
	@Value("${pusher.appId}")
	private String appId;
	@Value("${pusher.key}")
	private String key;
	@Value("${pusher.secret}")
	private String secret;
	@Value("${pusher.cluster}")
	private String cluster;
	private Pusher pusher;

	@PostConstruct
	private void init() {
		pusher = new Pusher(appId, key, secret);
		pusher.setCluster(cluster);
		pusher.setEncrypted(true);
	}

	@Override
	public void trigger(String channal, String event, String data) {
		// TODO Auto-generated method stub
		pusher.trigger(channal, event, data);

	}

}
