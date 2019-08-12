import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:pit_network/pit_network.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  List<dynamic> wifiArround = [];
  List<dynamic> savedWifi = [];
  Map<String, dynamic> connectedWifi;
  List<String> networkState;

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  Future<void> initPlatformState() async {
    List<dynamic> saveWifi = [];
    List<dynamic> wifiArrounds = [];
    Map<String, dynamic> connect;
    List<String> netState;
    try {
      saveWifi = await PitNetwork.getSavedWifi();
      wifiArrounds = await PitNetwork.getWifiArround();
      connect = await PitNetwork.getConnectedWifi();
      netState = await PitNetwork.getNetworkState();
    } on PlatformException {
      print('error');
    }
    if (!mounted) return;

    setState(() {
      savedWifi = saveWifi;
      wifiArround = wifiArrounds;
      connectedWifi = connect;
      networkState = netState;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
          appBar: AppBar(
            title: const Text('Plugin example app'),
          ),
          body: SingleChildScrollView(
            child: Center(
              child: Text(
                  'Connected to Wifi: $connectedWifi\n\n Network State : $networkState\n\nSaved Wifi : $savedWifi\n\n Arround : $wifiArround'),
            ),
          )),
    );
  }
}
