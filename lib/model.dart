class ConnectedWifiModel {
  final String SSID;
  final String BSSID;
  final int netId;
  final bool hiddenSSSID;
  final int ipAddress;
  final String macAddress;

  ConnectedWifiModel({this.SSID, this.BSSID, this.netId, this.hiddenSSSID, this.ipAddress, this.macAddress});

  factory ConnectedWifiModel.fromJson(Map<String, dynamic> json) {
    return ConnectedWifiModel(
        SSID: json["SSID"] as String,
        BSSID: json["BSSID"] as String,
        netId: json["Net_Id"] as int,
        ipAddress: json["IPAddress"] as int,
        macAddress: json["MacAddress"] as String,
        hiddenSSSID: json["hiddenSSID"] as bool);
  }
}

class SavedWifiModel {
  final String SSID;
  final String BSSID;

  SavedWifiModel({this.SSID, this.BSSID});

  factory SavedWifiModel.fromJson(Map<String, dynamic> json) {
    return SavedWifiModel(
      SSID: json["SSID"] as String,
      BSSID: json["BSSID"] as String,
    );
  }
}

class WifiArroundModel {
  final String SSID;
  final String BSSID;
  final int frequency;
  final String capabilities;
  final int timeStamp;

  WifiArroundModel({this.SSID, this.BSSID, this.frequency, this.capabilities, this.timeStamp});

  factory WifiArroundModel.fromJson(Map<String, dynamic> json) {
    return WifiArroundModel(
        SSID: json["SSID"] as String,
        BSSID: json["BSSID"] as String,
        capabilities: json["capabilities"] as String,
        frequency: json["frequency"] as int,
        timeStamp: (json["timeStamp"] as int) ~/ 1000);
  }
}

class NetworkStateModel {
  final List<String> connectionType;

  NetworkStateModel({this.connectionType});

  factory NetworkStateModel.fromJson(Map<String, dynamic> json) {

    return NetworkStateModel(connectionType: List<String>.from(json["connectionType"] ?? []));
  }
}
