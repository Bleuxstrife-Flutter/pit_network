import 'dart:async';

import 'package:flutter/services.dart';

class PitNetwork {
  static const MethodChannel _channel = const MethodChannel('pit_network');

  static Future<List<dynamic>> getSavedWifi() async {
    var list = await _channel.invokeMethod('getSavedWifi');
    return list;
  }

  static Future<List<dynamic>> getWifiArround() async {
    var list = await _channel.invokeMethod('getWifiArround');
    return list;
  }

  static Future<Map<String, dynamic>> getConnectedWifi() async {
    var res = await _channel.invokeMethod('getConnectedWifi');
    Map<String, dynamic> finalResult = Map.from(res);
    return finalResult;
  }

  static Future<List<String>> getNetworkState() async {
    var res = await _channel.invokeMethod('getNetworkState');
    List<String> finalResult = List.from(res);
    return finalResult;
  }
}
