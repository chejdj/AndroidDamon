package com.chejdj.androiddamon;

public class nativeCall {
    static {
        System.loadLibrary("keeplive");
    }

    public static native void keepLiveFromnative();
}
