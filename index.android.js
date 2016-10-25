/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */
'use strict';
import React, {
  AppRegistry,
  Component,
  StyleSheet,
  Text,
  View,
} from 'react-native';

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
  CONNNECTERR:"CONNNECTERR",// 拨打电话失败


}

import  EasemobLibrary from 'react-native-easemob';
var Emitter = require('RCTDeviceEventEmitter');


class easemob extends Component {
  constructor(props){
    super(props);
    this.state = {
      userName: 'zw123',
      passWord: '123',
      toUser:'zhang',
      content:'56789',
      callState:'未注册通话监听'
    };
  }
  login(){
    EasemobLibrary.Login(this.state.userName,this.state.passWord,(success)=>{
      alert(success);
    },(error)=>{
      alert(error)
    });
  }
  SendText(){
    EasemobLibrary.SendText(this.state.toUser,this.state.content,
        (success)=>{alert(success);},
        (error)=>{alert(error)}
    );
  }
  Call(){
    EasemobLibrary.Call(this.state.toUser);
  }
  Initim(){ //注册实时通话监听
    EasemobLibrary.Initim();
    Emitter.addListener(EasemobEvent.CallReceiver, ev => {
      EasemobLibrary.Answer()
    })
  }
  StateChangeListener(){ //通话状态监听
    this.setState({"callState":"已设置通话监听"});
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
  }
  setAppInited(){//完成注册和监听后调用
    EasemobLibrary.setAppInited();
  }
  registerEventListener(){ //回调监听事件注册
    EasemobLibrary.registerEventListener();
    Emitter.addListener(EasemobEvent.NewMessage, ev => {
      alert(ev.from+":"+ev.type+":"+ev.body);
    })
  }
  logout(){
    EasemobLibrary.logout(
        (success)=>{alert(success);},
        (error)=>{alert(error)}
    );
  }
  render() {
    return (
        <View style={styles.container}>
        <Text style={styles.welcome} onPress={()=>this.login()}>
          Login
        </Text>
          <Text style={styles.welcome} onPress={()=>this.SendText()}>
            SendText
          </Text>
          <Text style={styles.welcome} onPress={()=>this.Call()}>
            Call
          </Text>
          <Text style={styles.welcome} onPress={()=>this.Initim()}>
            注册实时通话监听
          </Text>
          <Text style={styles.welcome} onPress={()=>this.registerEventListener()}>
            回调监听事件注册
          </Text>
          <Text style={styles.welcome} onPress={()=>this.StateChangeListener()}>
            {this.state.callState}
          </Text>
          <Text style={styles.welcome} onPress={()=>this.logout()}>
            登出
          </Text>
        </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});

AppRegistry.registerComponent('easemob', () => easemob);
