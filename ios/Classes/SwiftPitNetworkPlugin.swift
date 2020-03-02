import Flutter
import UIKit

public class SwiftPitNetworkPlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "pit_network", binaryMessenger: registrar.messenger())
    let instance = SwiftPitNetworkPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
     if (call.method.elementsEqual("getConnectedWifi")){
        result(Dictionary<String, String>())
     } else if (call.method.elementsEqual("getNetworkState")){
        result(Dictionary<String, String>())
     } else if (call.method.elementsEqual("getSavedWifi")){
        result([String]())
     } else if (call.method.elementsEqual("getWifiArround")){
        result([String]())
     }
  }
}
