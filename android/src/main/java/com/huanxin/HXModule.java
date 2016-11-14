package com.huanxin;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class HXModule extends ReactContextBaseJavaModule {
    private Commonim commonim;
    private ReactContext mReactContext;

    public HXModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mReactContext = reactContext;
    }

    @Override
    public String getName() {
        return "EasemobLibrary";
    }

    @ReactMethod
    public void Create(String username,String pwd,Callback successCallback,Callback errorCallback){
        commonim = new Commonim(mReactContext);
        commonim.Create(this.getReactApplicationContext(), username, pwd, successCallback, errorCallback);
    }
    @ReactMethod
    public void Call(String username){
        commonim = new Commonim(mReactContext);
        commonim.Call(username);
    }
    @ReactMethod
    public void Answer(){
        commonim = new Commonim(mReactContext);
        commonim.Answer();
    }
    @ReactMethod
    public void endCall(){
        commonim = new Commonim(mReactContext);
        commonim.endCall();
    }
    @ReactMethod
    public void Initim(){
        commonim = new Commonim(mReactContext);
        commonim.Initim(this.getReactApplicationContext());
    }

    @ReactMethod
    public void Login(String username,String pwd,Callback successCallback,Callback errorCallback){
        commonim = new Commonim(mReactContext);
        commonim.Login(this.getReactApplicationContext(), username, pwd, successCallback, errorCallback);
    }
    @ReactMethod
    public void Reject(){
        commonim = new Commonim(mReactContext);
        commonim.Reject();
    }

    @ReactMethod
    public void StateChangeListener(){
        commonim = new Commonim(mReactContext);
        commonim.StateChangeListener();
    }

    @ReactMethod
    public void SendText(String username,String chatType,String content,String NickName,String HeadIcon,String Betinfo,Callback successCallback,Callback errorCallback){
        commonim = new Commonim(mReactContext);
        commonim.SendText(username,chatType,content,NickName,HeadIcon,Betinfo,successCallback, errorCallback);
    }
    @ReactMethod
    public void SendVoice(String username,String filePath,int len,Callback successCallback,Callback errorCallback){
        commonim = new Commonim(mReactContext);
        commonim.SendVoice(username, filePath, len, successCallback, errorCallback);
    }
    @ReactMethod
    public void SendImage(String username,String filePath,Callback successCallback,Callback errorCallback){
        commonim = new Commonim(mReactContext);
        commonim.SendImage(username, filePath, successCallback, errorCallback);
    }
    @ReactMethod
    public void SendLocation(String username,String locationAddress,Double latitude,Double longitude,Callback successCallback,Callback errorCallback){
        commonim = new Commonim(mReactContext);
        commonim.SendLocation(username, locationAddress, latitude, longitude, successCallback, errorCallback);
    }
    @ReactMethod
    public void SendFile(String username,String filePath,Callback successCallback,Callback errorCallback){
        commonim = new Commonim(mReactContext);
        commonim.SendFile(username, filePath, successCallback, errorCallback);
    }
    @ReactMethod
    public void setAppInited(){
        commonim = new Commonim(mReactContext);
        commonim.setAppInited();
    }
    @ReactMethod
    public void registerEventListener(){
        commonim = new Commonim(mReactContext);
        commonim.registerEventListener();
    }


       @ReactMethod
    public void removeEventListener(){
        commonim = new Commonim(mReactContext);
        commonim.removeEventListener();
    }
    
    @ReactMethod
    public void logout(Callback successCallback,Callback errorCallback){
        commonim = new Commonim(mReactContext);
        commonim.logout(successCallback, errorCallback);
    }
}
