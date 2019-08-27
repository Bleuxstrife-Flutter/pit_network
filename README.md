# PIT Network

Use this Plugin to get wifi connected, list of saved wifi, list of active wifi, and network state.

*Note*: This plugin is still under development, and some Components might not be available yet or still has so many bugs and this plugin just for android only.

## Installation

First, add `pit_network` as a [dependency in your pubspec.yaml file](https://flutter.io/platform-plugins/).

```
pit_network: ^0.1.1+1
```

## Important

this plugin depends on other plugins, you must have a permission to use this plugin, you can use `pit_permission` plugin or other permission plugin.

You must add this permission in AndroidManifest.xml for Android

```
for read wifi state = <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
```

## Example for Get Saved Wifi List
```
   List<SavedWifiModel> savedWifi = await PitNetwork.getSavedWifi();
```
## Example for Get Wifi Arround List
```
    List<WifiArroundModel> wifiArround = await PitNetwork.getWifiArround();
```
## Example for Get Connected Wifi List
```
    ConnectedWifiModel connectedWifi = await PitNetwork.getConnectedWifi();
```
## Example for Get Network State
```
   NetworkStateModel networkState =  await PitNetwork.getNetworkState();
```
