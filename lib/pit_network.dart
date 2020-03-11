import 'dart:async';

import 'package:flutter/services.dart';
import 'package:pit_network/model.dart';

export 'package:pit_network/model.dart';

class PitNetwork {
  static const MethodChannel _channel = const MethodChannel('pit_network');

  static Future<List<SavedWifiModel>> getSavedWifi() async {
    var list = await _channel.invokeMethod('getSavedWifi');
    List<SavedWifiModel> finalResult = [];

    for (int i = 0; i < list.length; i++) {
      finalResult.add(SavedWifiModel.fromJson(Map<String, dynamic>.from(list[i])));
    }

    return finalResult;
  }

  static Future<List<dynamic>> getWifiArround() async {
    var list = await _channel.invokeMethod('getWifiArround');
    List<WifiArroundModel> finalResult = [];

    for(var item in list){
      finalResult.add(WifiArroundModel.fromJson(Map<String, dynamic>.from(item)));
    }

    return finalResult;
  }

  static Future<ConnectedWifiModel> getConnectedWifi() async {
    var res = await _channel.invokeMethod('getConnectedWifi');
    ConnectedWifiModel finalResult = ConnectedWifiModel.fromJson(Map<String, dynamic>.from(res));

    return finalResult;
  }

  static Future<NetworkStateModel> getNetworkState() async {
    var res = await _channel.invokeMethod('getNetworkState');
    NetworkStateModel finalResult = NetworkStateModel.fromJson(Map<String, dynamic>.from(res));

    return finalResult;
  }
}
