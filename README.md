# react-native-easemob    



npm install react-native-easemob     



��seettings.gradle������
include ':app',':react-native-easemob'  
project(':react-native-easemob').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-easemob/android')


onCreate   
EMChat.getInstance().init(getApplicationContext());   

getPackages  
new HXPackage()  




    

import  EasemobLibrary from 'react-native-easemob';  
var Emitter = require('RCTDeviceEventEmitter');  


���м����¼�����  
var EasemobEvent = {  
  NewMessage:"NewMessage",// ������Ϣ����  
  DeliveryAck:"DeliveryAck",////�����ѷ��ͻ�ִ  
  NewCMDMessage:"NewCMDMessage",//͸����Ϣ����  
  ReadAck:"ReadAck",////�����Ѷ���ִ  
  CallReceiver:"CallReceiver",//�е绰����  
  CONNECTING:"CONNECTING",// �������ӶԷ�  
  CONNECTED:"CONNECTED",// ˫���Ѿ���������  
  ACCEPTED:"ACCEPTED",// �绰��ͨ�ɹ�  
  DISCONNNECTED:"DISCONNNECTED",// �绰����  
  CONNNECTERR:"CONNNECTERR"// ����绰ʧ��  
}  



//��¼     


 Login(String username,String pwd,Callback successCallback,Callback errorCallback)  


easemob.Login("zhang","123456",(success)=>{   
    alert(msg);    
  },(error)=>{    
    alert(errorMsg)    
});  

//�����˺�  

 Create(String username,String pwd,Callback successCallback,Callback errorCallback)  

easemob.Create("zhang","123456",(success)=>{  

    alert(msg);  

  },(error)=>{  

    alert(errorMsg)  

});  

//����绰   

Call(String username)   

easemob.Call("zhang"��;   

//�ش�    

Answer    

easemob.Answer����;    

//�Ҷ�    

easemob.endCall����;    

//ע��ʵʱͨ������    

Initim    

easemob.Initim����;    

//�ܾ�����   

Reject    

easemob.Reject����;   


//ͨ��״̬����    

StateChangeListener()    
EasemobLibrary.StateChangeListener()  
Emitter.addListener(EasemobEvent.CONNECTING, () => {  
  this.setState({"callState":"�������ӶԷ�"});  
})  
Emitter.addListener(EasemobEvent.CONNECTED, () => {  
  this.setState({"callState":"˫���Ѿ���������"});  
})  
Emitter.addListener(EasemobEvent.ACCEPTED, () => {  
  this.setState({"callState":"�绰��ͨ�ɹ�"});  
})  
Emitter.addListener(EasemobEvent.DISCONNNECTED, () => {  
  this.setState({"callState":"�绰����"});  
})  
Emitter.addListener(EasemobEvent.CONNNECTERR, () => {  
  this.setState({"callState":"����绰ʧ��"});  
})  

 

//ע���������Ϣ�ļ����㲥    

NewMessage    

easemob.NewMessage����;    


//�����ı���Ϣ������    

SendText(String username,String content,Callback successCallback,Callback errorCallback)    

easemob.SendText��'zhang','aaaaaaaa',    

(success)=>{alert('CONNECTING');},    

(err)=>{alert('CONNECTING');},    

��;    


//����������Ϣ     

SendVoice(String username,String filePath,int len,Callback successCallback,Callback errorCallback)    

easemob.SendVoice��'zhang','c://aaaaaaaa.mp4',3,    

(success)=>{alert(success);},    

(err)=>{alert(err);},   

��;    


//����ͼƬ��Ϣ     

SendImage(String username,String filePath,Callback successCallback,Callback errorCallback)    

easemob.SendImage��'zhang','c://aaaaaaaa.jpg',    

(success)=>{alert(success);},  

(err)=>{alert(err);},    

��;    

 
//���͵���λ����Ϣ     

SendLocation(String username,String locationAddress,Double latitude,Double longitude,Callback successCallback,Callback errorCallback)     

easemob.SendLocation��'zhang','aaa',45.54645,64.4564     

(success)=>{alert(success);},    

(err)=>{alert(err);},    

��;     


//�����ļ���Ϣ     

SendFile(String username,String filePath,Callback successCallback,Callback errorCallback)    

easemob.SendFile��'zhang','c://aaa',    

(success)=>{alert(success);},    

(err)=>{alert(err);},    

��;   


