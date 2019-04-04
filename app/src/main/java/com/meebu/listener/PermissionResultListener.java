package com.meebu.listener;

public interface PermissionResultListener {
    public void onAllPermissionGranted();

    public void onPermissionGranted();

    public void onPermissionPermanentlyDenied();

    public void onPermissionDenied();

}
