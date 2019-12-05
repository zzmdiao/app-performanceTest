package com.iqianjin.appperformance.core.ios;


public interface IosDeviceChangeListener {
    void onDeviceConnected(String deviceId);
    void onDeviceDisconnected(String deviceId);
}
