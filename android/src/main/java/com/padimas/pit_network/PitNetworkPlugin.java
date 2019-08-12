package com.padimas.pit_network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * PitNetworkPlugin
 */
public class PitNetworkPlugin implements MethodCallHandler {
    public PitNetworkPlugin(Registrar registrar) {
        this.registrar = registrar;
    }

    Registrar registrar;

    /**
     * Plugin registration.
     */
    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "pit_network");
        channel.setMethodCallHandler(new PitNetworkPlugin(registrar));
    }

    @Override
    public void onMethodCall(MethodCall call, Result result) {
        if (call.method.equals("getSavedWifi")) {
            List<Map<String, Object>> res = getSavedWifi();
            result.success(res);
        } else if (call.method.equals("getConnectedWifi")) {
            Map<String, Object> res = getConnectedWifi();
            result.success(res);
        } else if (call.method.equals("getWifiArround")) {
            List<Map<String, Object>> res = getWifiArround();
            result.success(res);
        } else if (call.method.equals("getNetworkState")) {
            List<String> res = getNetworkState();
            result.success(res);
        } else {
            result.notImplemented();
        }
    }

    public List<Map<String, Object>> getWifiArround() {
        List<Map<String, Object>> list = new ArrayList<>();
        Context context = registrar.context();
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo.isConnected()) {
            final WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            List<ScanResult> scanResult = wifiManager.getScanResults();
            for (ScanResult scanRes : scanResult) {
                Map<String, Object> result = new HashMap<>();
                result.put("SSID", scanRes.SSID);
                result.put("BSSID", scanRes.BSSID);
                result.put("capabilities", scanRes.capabilities);
                result.put("frequency", scanRes.frequency);
                result.put("timeStamp", scanRes.timestamp);
                list.add(result);
            }
        }
        return list;
    }

    public List<Map<String, Object>> getSavedWifi() {
        List<Map<String, Object>> list = new ArrayList<>();
        Context context = registrar.context();
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo.isConnected()) {
            final WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            List<WifiConfiguration> configs = wifiManager.getConfiguredNetworks();
            for (WifiConfiguration config : configs) {
                Map<String, Object> result = new HashMap<>();
                result.put("SSID", config.SSID);
                result.put("BSSID", config.BSSID);
                list.add(result);
            }
        }
        return list;
    }

    public Map<String, Object> getConnectedWifi() {
        Map<String, Object> result = new HashMap<>();
        Context context = registrar.context();
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo.isConnected()) {
            final WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            result.put("SSID", connectionInfo.getSSID());
            result.put("BSSID", connectionInfo.getBSSID());
            result.put("hiddenSSID", connectionInfo.getHiddenSSID());
            result.put("IPAddress", connectionInfo.getIpAddress());
            result.put("MacAddress", connectionInfo.getMacAddress());
            result.put("Net_Id", connectionInfo.getNetworkId());
        }
        return result;
    }

    private List<String> getNetworkState() {
        Context context = registrar.context();
        List<String> listString = new ArrayList<>();
        final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {
            if (Build.VERSION.SDK_INT < 23) {
                final NetworkInfo ni = cm.getActiveNetworkInfo();

                if (ni != null) {
                    if (ni.getType() == ConnectivityManager.TYPE_WIFI) {
                        listString.add("Connection Type : WIFI");
                    } else if (ni.getType() == ConnectivityManager.TYPE_MOBILE) {
                        listString.add("Connection Type : MOBILE NETWORK");
                        switch (ni.getSubtype()) {
                            case TelephonyManager.NETWORK_TYPE_1xRTT:
                                listString.add("Network Type : NETWORK_TYPE_1xRTT NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_CDMA:
                                listString.add("Network Type : NETWORK_TYPE_CDMA NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_EDGE:
                                listString.add("Network Type : NETWORK_TYPE_EDGE NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                                listString.add("Network Type : NETWORK_TYPE_EVDO_0 NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                                listString.add("Network Type : NETWORK_TYPE_EVDO_A NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_GPRS:
                                listString.add("Network Type : NETWORK_TYPE_GPRS NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_HSDPA:
                                listString.add("Network Type : NETWORK_TYPE_HSDPA NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_HSPA:
                                listString.add("Network Type : NETWORK_TYPE_HSPA NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_HSUPA:
                                listString.add("Network Type : NETWORK_TYPE_HSPA NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_UMTS:
                                listString.add("Network Type : NETWORK_TYPE_UMTS NETWORK");
                                break;
                            /*
                             * Above API level 7, make sure to set android:targetSdkVersion
                             * to appropriate level to use these
                             */
                            case TelephonyManager.NETWORK_TYPE_EHRPD: // API level 11
                                listString.add("Network Type : NETWORK_TYPE_EHRPD NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_EVDO_B: // API level 9
                                listString.add("Network Type : NETWORK_TYPE_EVDO_B NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_HSPAP: // API level 13
                                listString.add("Network Type : NETWORK_TYPE_HSPAP NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_IDEN: // API level 8
                                listString.add("Network Type : NETWORK_TYPE_IDEN NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_LTE: // API level 11
                                listString.add("Network Type : NETWORK_TYPE_LTE NETWORK");
                                break;
                            // Unknown
                            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                            default:
                                listString.add("Network Type : UNKNOWM NETWORK");
                                break;
                        }
                    }
                }
            } else {
                final Network n = cm.getActiveNetwork();
                final NetworkInfo ni = cm.getActiveNetworkInfo();
                if (n != null && ni != null) {
                    final NetworkCapabilities nc = cm.getNetworkCapabilities(n);

                    if (nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        listString.add("Connection Type : WIFI");
                    } else if (nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        listString.add("Connection Type : MOBILE NETWORK");

                        switch (ni.getSubtype()) {
                            case TelephonyManager.NETWORK_TYPE_1xRTT:
                                listString.add("Connection Type : NETWORK_TYPE_1xRTT NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_CDMA:
                                listString.add("Connection Type : NETWORK_TYPE_CDMA NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_EDGE:
                                listString.add("Connection Type : NETWORK_TYPE_EDGE NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                                listString.add("Connection Type : NETWORK_TYPE_EVDO_0 NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                                listString.add("Connection Type : NETWORK_TYPE_EVDO_A NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_GPRS:
                                listString.add("Connection Type : NETWORK_TYPE_GPRS NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_HSDPA:
                                listString.add("Connection Type : NETWORK_TYPE_HSDPA NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_HSPA:
                                listString.add("Connection Type : NETWORK_TYPE_HSPA NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_HSUPA:
                                listString.add("Connection Type : NETWORK_TYPE_HSPA NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_UMTS:
                                listString.add("Connection Type : NETWORK_TYPE_UMTS NETWORK");
                                break;
                            /*
                             * Above API level 7, make sure to set android:targetSdkVersion
                             * to appropriate level to use these
                             */
                            case TelephonyManager.NETWORK_TYPE_EHRPD: // API level 11
                                listString.add("Connection Type : NETWORK_TYPE_EHRPD NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_EVDO_B: // API level 9
                                listString.add("Connection Type : NETWORK_TYPE_EVDO_B NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_HSPAP: // API level 13
                                listString.add("Connection Type : NETWORK_TYPE_HSPAP NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_IDEN: // API level 8
                                listString.add("Connection Type : NETWORK_TYPE_IDEN NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_LTE: // API level 11
                                listString.add("Connection Type : NETWORK_TYPE_LTE NETWORK");
                                break;
                            // Unknown
                            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                            default:
                                listString.add("Connection Type : UNKNOWM NETWORK");
                                break;
                        }

                    }
                }
            }
        }
        return listString;
    }
}
