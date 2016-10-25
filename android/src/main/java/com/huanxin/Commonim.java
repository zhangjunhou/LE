package com.huanxin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telecom.Call;
import android.util.Log;


import com.easemob.EMCallBack;
import com.easemob.EMEventListener;
import com.easemob.EMNotifierEvent;
import com.easemob.chat.EMCallStateChangeListener;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chat.ImageMessageBody;
import com.easemob.chat.LocationMessageBody;
import com.easemob.chat.NormalFileMessageBody;
import com.easemob.chat.TextMessageBody;
import com.easemob.chat.VoiceMessageBody;
import com.easemob.exceptions.EMNetworkUnconnectedException;
import com.easemob.exceptions.EMNoActiveCallException;
import com.easemob.exceptions.EMServiceNotReadyException;
import com.easemob.exceptions.EaseMobException;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.io.File;
import java.util.List;

public class Commonim {

    private ReactContext mReactContext;
    public Commonim(ReactContext reactContext) {
        mReactContext = reactContext;
    }
    //发送参数到js端
    private void sendEvent(String eventName, WritableMap params) {
        if (mReactContext != null) {
            mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit(eventName, params);
            Log.i("RNEasemobModule", "mReactContext is not null");
        }else{
            Log.i("RNEasemobModule", "mReactContext is null");
        }
    }

    private void sendEvent(String eventName) {
        if (mReactContext != null) {
            mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit(eventName,null);
            Log.i("RNEasemobModule", "mReactContext is not null");
        }else{
            Log.i("RNEasemobModule", "mReactContext is null");
        }
    }

    //注册设备用户
    public void Create(final Context context, final String username, final String pwd,final Callback successCallback, final Callback errorCallback){
        new Thread(new Runnable() {
            public void run() {
                try {
                    // 调用sdk注册方法
                    EMChatManager.getInstance().createAccountOnServer(username, pwd);
                    successCallback.invoke("success");
                } catch (final EaseMobException e) {
                    //注册失败
                    int errorCode=e.getErrorCode();
                    errorCallback.invoke(errorCode);
                }
            }
        }).start();

    }

    //登录设备用户
    public void Login(final Context context, final String username, final String pwd,final Callback successCallback, final Callback errorCallback){
        EMChatManager.getInstance().login(username, pwd, new EMCallBack() {
            @Override
            public void onSuccess() {
                successCallback.invoke("Login_success");
            }

            @Override
            public void onError(int i, String s) {
                errorCallback.invoke(s);
            }

            @Override
            public void onProgress(int i, String s) {
            }
        });
    }

    //发送文本消息及表情
    public void SendText(final String username,final String chatType, final String content,final String NickName,final String HeadIcon,final String BetInfo ,final Callback successCallback, final Callback errorCallback){
        EMConversation conversation = EMChatManager.getInstance().getConversation(username);
        EMMessage message = EMMessage.createSendMessage(EMMessage.Type.TXT);
    TextMessageBody txtBody;
        if(BetInfo!=null){
    txtBody=new TextMessageBody(BetInfo);
     message.setAttribute("betinfo",BetInfo);
    }else{
    txtBody = new TextMessageBody(content);
    }
        message.addBody(txtBody);
        message.setReceipt(username);

 message.setAttribute("NickName",NickName);
 message.setAttribute("HeadIcon",HeadIcon);

    switch (chatType){
        case "chat":
            message.setChatType(EMMessage.ChatType.Chat);
            break;
        case "room":
            message.setChatType(EMMessage.ChatType.ChatRoom);
            break;
        case "group":
            message.setChatType(EMMessage.ChatType.GroupChat);
            break;
    }

        conversation.addMessage(message);
        //发送消息
        EMChatManager.getInstance().sendMessage(message, new EMCallBack() {
            @Override
            public void onSuccess() {
                successCallback.invoke("SendText_success");
            }

            @Override
            public void onError(int i, String s) {
                errorCallback.invoke(s);
            }

            @Override
            public void onProgress(int i, String s) {
            }
        });
    }

    //发送语音消息
    public void SendVoice(final String username, final String filePath,final int len,final Callback successCallback, final Callback errorCallback){
        EMConversation conversation = EMChatManager.getInstance().getConversation(username);
        EMMessage message = EMMessage.createSendMessage(EMMessage.Type.VOICE);
        VoiceMessageBody body = new VoiceMessageBody(new File(filePath), len);
        message.addBody(body);
        message.setReceipt(username);
        conversation.addMessage(message);
        EMChatManager.getInstance().sendMessage(message, new EMCallBack() {
            @Override
            public void onSuccess() {successCallback.invoke("SendVoice_success");}
            @Override
            public void onError(int i, String s){errorCallback.invoke(s);}
            @Override
            public void onProgress(int i, String s) {}
        });
    }

    //发送图片消息
    public void SendImage(final String username, final String filePath,final Callback successCallback, final Callback errorCallback){
        EMConversation conversation = EMChatManager.getInstance().getConversation(username);
        EMMessage message = EMMessage.createSendMessage(EMMessage.Type.IMAGE);
        ImageMessageBody body = new ImageMessageBody(new File(filePath));
        // 默认超过100k的图片会压缩后发给对方，可以设置成发送原图
        // body.setSendOriginalImage(true);
        message.addBody(body);
        message.setReceipt(username);
        conversation.addMessage(message);
        EMChatManager.getInstance().sendMessage(message, new EMCallBack() {
            @Override
            public void onSuccess() {successCallback.invoke("SendImage_success");}
            @Override
            public void onError(int i, String s)  {errorCallback.invoke(s);}
            @Override
            public void onProgress(int i, String s) {}
        });
    }

    //发送地理位置消息
    public void SendLocation(final String username, final String locationAddress, final Double latitude, final Double longitude,final Callback successCallback, final Callback errorCallback){
        EMConversation conversation = EMChatManager.getInstance().getConversation(username);
        EMMessage message = EMMessage.createSendMessage(EMMessage.Type.LOCATION);
        LocationMessageBody locBody = new LocationMessageBody(locationAddress, latitude, longitude);
        message.addBody(locBody);
        message.setReceipt(username);
        conversation.addMessage(message);
        EMChatManager.getInstance().sendMessage(message, new EMCallBack() {
            @Override
            public void onSuccess() {successCallback.invoke("SendLocation_success");}
            @Override
            public void onError(int i, String s)  {errorCallback.invoke(s);}
            @Override
            public void onProgress(int i, String s) {}
        });
    }

    //发送文件消息
    public void SendFile(final String username, final String filePath,final Callback successCallback, final Callback errorCallback){
        EMConversation conversation = EMChatManager.getInstance().getConversation(username);
        EMMessage message = EMMessage.createSendMessage(EMMessage.Type.FILE);
        message.setReceipt(username);
        NormalFileMessageBody body = new NormalFileMessageBody(new File(filePath));
        message.addBody(body);
        conversation.addMessage(message);
        EMChatManager.getInstance().sendMessage(message, new EMCallBack() {
            @Override
            public void onSuccess() {
                successCallback.invoke("SendLocation_success");
            }

            @Override
            public void onError(int i, String s) {
                errorCallback.invoke(s);
            }

            @Override
            public void onProgress(int i, String s) {
            }
        });
    }


    //注册实时通话监听
    public void Initim(Context context) {
        IntentFilter callFilter = new IntentFilter(EMChatManager.getInstance().getIncomingCallBroadcastAction());
        context.registerReceiver(new CallReceiver(), callFilter);
    }
    private class CallReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String msgId = intent.getStringExtra("msgid");
            String from = intent.getStringExtra("from");
            // call type
            String type = intent.getStringExtra("type");
            WritableMap params = Arguments.createMap();
            params.putString("from", from);
            params.putString("type", type);
            params.putString("msgId", msgId);
            sendEvent("CallReceiver", params);
        }
    }

    //拨打语音通话
    public void Call(String username){
        try {
            EMChatManager.getInstance().makeVoiceCall(username);
        } catch (EMServiceNotReadyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    //接听通话
    public void Answer() {
        try {
            EMChatManager.getInstance().answerCall();
        } catch (EMNoActiveCallException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (EMNetworkUnconnectedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    //拒绝接听
    public void Reject() {
        try {
            EMChatManager.getInstance().rejectCall();
        } catch (EMNoActiveCallException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //回调监听事件注册(最后要通知sdk，UI 已经初始化完毕，注册了相应的receiver和listener, 可以接受broadcast了)
    public void setAppInited() {
        EMChat.getInstance().setAppInited();
    }

    //回调监听事件注册
    public void registerEventListener() {
        EMChatManager.getInstance().registerEventListener(new EMEventListener() {
            @Override
            public void onEvent(EMNotifierEvent event) {
                switch (event.getEvent()) {
                    case EventNewMessage: // 接收新消息
                    {
                        Log.d("EventNewMessage", "EventNewMessage");
                        EMMessage message = (EMMessage) event.getData();
                        sendRegisterEvent("OnMessageCome",message);
                        break;
                    }
                    case EventDeliveryAck: {//接收已发送回执
                        Log.d("EventDeliveryAck", "EventDeliveryAck");
                        EMMessage message = (EMMessage) event.getData();
                        sendRegisterEvent("DeliveryAck",message);
                        break;
                    }
                    case EventNewCMDMessage: {//接收透传消息
                        Log.d("EventNewCMDMessage", "EventNewCMDMessage");
                        EMMessage message = (EMMessage) event.getData();
                        sendRegisterEvent("NewCMDMessage",message);
                        break;
                    }
                    case EventReadAck: {//接收已读回执
                        EMMessage message = (EMMessage) event.getData();
                        sendRegisterEvent("ReadAck",message);
                        break;
                    }
                    case EventOfflineMessage: {//接收离线消息
                        Log.d("EventOfflineMessage", "EventOfflineMessage");
                        List<EMMessage> messages = (List<EMMessage>) event.getData();
                        break;
                    }
                    case EventConversationListChanged: {//通知会话列表通知event注册（在某些特殊情况，SDK去删除会话的时候会收到回调监听）
                        Log.d("EventConversationList", "EventConversationListChanged");
                        break;
                    }
                    default:
                        break;
                }
            }
        });
    }

    private void sendRegisterEvent(String eventName,EMMessage message){
        WritableMap params = Arguments.createMap();
        String fromuser = message.getFrom();
        String from =message.getTo();
     

        String type = message.getType().toString();

if(type.equals("TXT")){

     TextMessageBody bodys = (TextMessageBody)message.getBody();
     params.putString("body", bodys.getMessage());

}

if(type.equals("IMAGE")){

     ImageMessageBody bodys = (ImageMessageBody)message.getBody();
     params.putString("body", bodys.getThumbnailUrl());

}
 String betinfo=message.getStringAttribute("betinfo","");

        String body = message.getBody().toString();
        long time = message.getMsgTime();
        params.putString("fromuser", fromuser);
        params.putString("from", from);
        params.putString("bodytype", type);
        params.putDouble("time", time);
        params.putString("id", message.getMsgId());
        if(betinfo!=""){
        params.putString("type","BetInfo");
        }else{
              params.putString("type",message.getChatType().toString());
        }
        params.putString("nickname",message.getStringAttribute("NickName",""));
        params.putString("headicon",message.getStringAttribute("HeadIcon",""));
        sendEvent(eventName, params);
    }
    //挂断通话
    public void endCall() {
        EMChatManager.getInstance().endCall();
    }
    //退出聊天登陆
    public void logout(final Callback successCallback,final Callback errorCallback) {
        EMChatManager.getInstance().logout(new EMCallBack() {
            @Override
            public void onSuccess() {successCallback.invoke("success");}
            @Override
            public void onProgress(int progress, String status) {}
            @Override
            public void onError(int code, String message) {errorCallback.invoke("error");}
        });
    }

    //通话状态监听
    public void StateChangeListener() {
        /**
         * 设置通话状态监听
         * @param listener
         */
        EMChatManager.getInstance().addVoiceCallStateChangeListener(new EMCallStateChangeListener() {
            @Override
            public void onCallStateChanged(CallState callState, CallError error) {
                switch (callState) {
                    case CONNECTING: // 正在连接对方
                        sendEvent("CONNECTING");
                        break;
                    case CONNECTED: // 双方已经建立连接
                        sendEvent("CONNECTED");
                        break;
                    case ACCEPTED: // 电话接通成功
                        sendEvent("ACCEPTED");
                        break;
                    case DISCONNNECTED: // 电话断了
                        sendEvent("DISCONNNECTED");
                        break;
                    default:
                        sendEvent("CONNNECTERR");
                        break;
                }
            }
        });
    }
}
