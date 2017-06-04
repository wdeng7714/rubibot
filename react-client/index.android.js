/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import SocketIOClient from 'socket.io-client';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View,
  TextInput,
  Button,
  Alert
} from 'react-native';

const SERVER_ADDRESS = '192.168.2.24:8080';

const onBegin = () => {

  Alert.alert('Begin...');
}; 

const onSend = () => {
  var socket = SocketIOClient(SERVER_ADDRESS);
  socket.on('connect',function(){
    Alert.alert('Send...');
    socket.emit('message', 'Hello server');
  });
}; 

const onCamera = () => {

  Alert.alert('Camera...');
}; 
export default class Rubibot extends Component {
  render() {
    return (
      <View style = {styles.container}>
      <Text style = {styles.welcome}>
      &lt;RubiBot&gt;
        </Text>
        <View style = {styles.send_container}>
          <Text style = {styles.group_title}>
            Get started
          </Text>
          <Text style = {styles.instructions}>
            To get started, make sure the node.js server is on. The arduino and python clients must be ready to go. Place the rubiks cube into the seat, and press 'Begin'.
          </Text>
           <Button
            onPress = {onBegin}
            title = "Begin"/>
      </View>

        <View style = {styles.send_container}>
          <Text style = {styles.group_title}>
            Test server
          </Text>
          <TextInput
            style = {styles.message_input}
            placeholder = 'Send a message to server'
          />
           <Button
            onPress = {onSend}
            title = "Send Message" 
            color = "#a96fa7"/>
      </View>

        <View style = {styles.send_container}>
          <Text style = {styles.group_title}>
            Test camera
          </Text>
          <Text style = {styles.instructions}>
            Test the react-native camera API. Make sure camera permissions are on.
          </Text>
           <Button
            disabled
            onPress = {onCamera}
            title = "Camera" />
      </View>
      </View>

    );
  }
}


const styles = StyleSheet.create({
  container: {
    flex: 1,
    // justifyContent: 'center',
    // alignItems: 'center',
    backgroundColor: '#f0f0f0',  },
  send_container: {
    margin: 20,
    backgroundColor: '#ffffff',
    borderRadius: 1,
  },
  group_title: {
    color: '#000000',
    fontWeight: '900',
    fontSize: 16,
    fontFamily: 'sans-serif-condensed',
    margin: 10,
    marginBottom: 0,
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    color: '#111111',
    margin: 5,
    marginTop: 20,
    // fontWeight: '500',sans-serif-light
    fontFamily: 'sans-serif-light',
  },
  instructions: {
    // textAlign: 'center',
    color: '#000000',
    margin: 10,
    marginTop: 0,
  },
  message_input: {
    padding: 10,
    margin: 10,
    paddingTop: 0,
    marginTop: 0,
    borderColor: '#ffffff',
    // borderWidth: 1,
  }
});

AppRegistry.registerComponent('Rubibot', () => Rubibot);
