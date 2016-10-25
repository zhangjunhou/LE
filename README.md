# react-native-easemob    



npm install react-native-easemob     



在seettings.gradle中设置
include ':app',':react-native-easemob'  
project(':react-native-easemob').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-easemob/android')


onCreate   
EMChat.getInstance().init(getApplicationContext());   

getPackages  
new HXPackage()  




    

import  EasemobLibrary from 'react-native-easemob';  
var Emitter = require('RCTDeviceEventEmitter');  


所有监听事件名称  
var EasemobEvent = {  
  NewMessage:"NewMessage",// 有新消息发来  
  DeliveryAck:"DeliveryAck",////接收已发送回执  
  NewCMDMessage:"NewCMDMessage",//透传消息发来  
  ReadAck:"ReadAck",////接收已读回执  
  CallReceiver:"CallReceiver",//有电话打来  
  CONNECTING:"CONNECTING",// 正在连接对方  
  CONNECTED:"CONNECTED",// 双方已经建立连接  
  ACCEPTED:"ACCEPTED",// 电话接通成功  
  DISCONNNECTED:"DISCONNNECTED",// 电话断了  
  CONNNECTERR:"CONNNECTERR"// 拨打电话失败  
}  



//登录     


 Login(String username,String pwd,Callback successCallback,Callback errorCallback)  


easemob.Login("zhang","123456",(success)=>{   
    alert(msg);    
  },(error)=>{    
    alert(errorMsg)    
});  

//创建账号  

 Create(String username,String pwd,Callback successCallback,Callback errorCallback)  

easemob.Create("zhang","123456",(success)=>{  

    alert(msg);  

  },(error)=>{  

    alert(errorMsg)  

});  

//拨打电话   

Call(String username)   

easemob.Call("zhang"）;   

//回答    

Answer    

easemob.Answer（）;    

//挂断    

easemob.endCall（）;    

//注册实时通话监听    

Initim    

easemob.Initim（）;    

//拒绝接听   

Reject    

easemob.Reject（）;   


//通话状态监听    

StateChangeListener()    
EasemobLibrary.StateChangeListener()  
Emitter.addListener(EasemobEvent.CONNECTING, () => {  
  this.setState({"callState":"正在连接对方"});  
})  
Emitter.addListener(EasemobEvent.CONNECTED, () => {  
  this.setState({"callState":"双方已经建立连接"});  
})  
Emitter.addListener(EasemobEvent.ACCEPTED, () => {  
  this.setState({"callState":"电话接通成功"});  
})  
Emitter.addListener(EasemobEvent.DISCONNNECTED, () => {  
  this.setState({"callState":"电话断了"});  
})  
Emitter.addListener(EasemobEvent.CONNNECTERR, () => {  
  this.setState({"callState":"拨打电话失败"});  
})  

 

//注册接收新消息的监听广播    

NewMessage    

easemob.NewMessage（）;    


//发送文本消息及表情    

SendText(String username,String content,Callback successCallback,Callback errorCallback)    

easemob.SendText（'zhang','aaaaaaaa',    

(success)=>{alert('CONNECTING');},    

(err)=>{alert('CONNECTING');},    

）;    


//发送语音消息     

SendVoice(String username,String filePath,int len,Callback successCallback,Callback errorCallback)    

easemob.SendVoice（'zhang','c://aaaaaaaa.mp4',3,    

(success)=>{alert(success);},    

(err)=>{alert(err);},   

）;    


//发送图片消息     

SendImage(String username,String filePath,Callback successCallback,Callback errorCallback)    

easemob.SendImage（'zhang','c://aaaaaaaa.jpg',    

(success)=>{alert(success);},  

(err)=>{alert(err);},    

）;    

 
//发送地理位置消息     

SendLocation(String username,String locationAddress,Double latitude,Double longitude,Callback successCallback,Callback errorCallback)     

easemob.SendLocation（'zhang','aaa',45.54645,64.4564     

(success)=>{alert(success);},    

(err)=>{alert(err);},    

）;     


//发送文件消息     

SendFile(String username,String filePath,Callback successCallback,Callback errorCallback)    

easemob.SendFile（'zhang','c://aaa',    

(success)=>{alert(success);},    

(err)=>{alert(err);},    

）;   


