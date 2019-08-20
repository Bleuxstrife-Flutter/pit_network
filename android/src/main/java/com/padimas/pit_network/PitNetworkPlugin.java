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
import android.os.SystemClock;
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
        this.context = registrar.context();
    }

    Context context;

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
            try {
                List<Map<String, Object>> res = getSavedWifi();
                result.success(res);
            } catch (Exception e) {
                result.error("error", "getSavedWifi", e.getLocalizedMessage());
            }
        } else if (call.method.equals("getConnectedWifi")) {
            try{
                Map<String, Object> res = getConnectedWifi();
                result.success(res);
            } catch (Exception e) {
                result.error("error", "getConnectedWifi", e.getLocalizedMessage());
            }
        } else if (call.method.equals("getWifiArround")) {
            try{
                List<Map<String, Object>> res = getWifiArround();
                result.success(res);
            } catch (Exception e) {
                result.error("error", "getWifiArround", e.getLocalizedMessage());
            }
        } else if (call.method.equals("getNetworkState")) {
            try{
                Map<String, List<String>> res = getNetworkState();
                result.success(res);
            } catch (Exception e) {
                result.error("error", "getNetworkState", e.getLocalizedMessage());
            }
        } else {
            result.notImplemented();
        }
    }

    private List<Map<String, Object>> getWifiArround() throws Exception {
        List<Map<String, Object>> list = new ArrayList<>();
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
                result.put("timeStamp", System.currentTimeMillis() - SystemClock.elapsedRealtime() + (scanRes.timestamp / 1000));
                list.add(result);
            }
        }
        return list;
    }

    private List<Map<String, Object>> getSavedWifi() throws Exception{
        List<Map<String, Object>> list = new ArrayList<>();
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

    private Map<String, Object> getConnectedWifi() throws Exception{
        Map<String, Object> result = new HashMap<>();
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

    private Map<String, List<String>> getNetworkState() throws Exception {
        List<String> listString = new ArrayList<>();
        Map<String, List<String>> result = new HashMap<>();
        final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {
            if (Build.VERSION.SDK_INT < 23) {
                final NetworkInfo ni = cm.getActiveNetworkInfo();

                if (ni != null) {
                    if (ni.getType() == ConnectivityManager.TYPE_WIFI) {
                        listString.add("WIFI");
                    } else if (ni.getType() == ConnectivityManager.TYPE_MOBILE) {
                        listString.add("MOBILE NETWORK");
                        switch (ni.getSubtype()) {
                            case TelephonyManager.NETWORK_TYPE_1xRTT:
                                listString.add("NETWORK_TYPE_1xRTT NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_CDMA:
                                listString.add("NETWORK_TYPE_CDMA NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_EDGE:
                                listString.add("NETWORK_TYPE_EDGE NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                                listString.add("NETWORK_TYPE_EVDO_0 NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                                listString.add("NETWORK_TYPE_EVDO_A NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_GPRS:
                                listString.add("NETWORK_TYPE_GPRS NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_HSDPA:
                                listString.add("NETWORK_TYPE_HSDPA NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_HSPA:
                                listString.add("NETWORK_TYPE_HSPA NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_HSUPA:
                                listString.add("NETWORK_TYPE_HSPA NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_UMTS:
                                listString.add("NETWORK_TYPE_UMTS NETWORK");
                                break;
                            /*
                             * Above API level 7, make sure to set android:targetSdkVersion
                             * to appropriate level to use these
                             */
                            case TelephonyManager.NETWORK_TYPE_EHRPD: // API level 11
                                listString.add("NETWORK_TYPE_EHRPD NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_EVDO_B: // API level 9
                                listString.add("NETWORK_TYPE_EVDO_B NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_HSPAP: // API level 13
                                listString.add("NETWORK_TYPE_HSPAP NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_IDEN: // API level 8
                                listString.add("NETWORK_TYPE_IDEN NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_LTE: // API level 11
                                listString.add("NETWORK_TYPE_LTE NETWORK");
                                break;
                            // Unknown
                            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                            default:
                                listString.add("UNKNOWM NETWORK");
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
                        listString.add("WIFI");
                    } else if (nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        listString.add("MOBILE NETWORK");

                        switch (ni.getSubtype()) {
                            case TelephonyManager.NETWORK_TYPE_1xRTT:
                                listString.add("NETWORK_TYPE_1xRTT NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_CDMA:
                                listString.add("NETWORK_TYPE_CDMA NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_EDGE:
                                listString.add("NETWORK_TYPE_EDGE NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                                listString.add("NETWORK_TYPE_EVDO_0 NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                                listString.add("NETWORK_TYPE_EVDO_A NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_GPRS:
                                listString.add("NETWORK_TYPE_GPRS NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_HSDPA:
                                listString.add("NETWORK_TYPE_HSDPA NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_HSPA:
                                listString.add("NETWORK_TYPE_HSPA NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_HSUPA:
                                listString.add("NETWORK_TYPE_HSPA NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_UMTS:
                                listString.add("NETWORK_TYPE_UMTS NETWORK");
                                break;
                            /*
                             * Above API level 7, make sure to set android:targetSdkVersion
                             * to appropriate level to use these
                             */
                            case TelephonyManager.NETWORK_TYPE_EHRPD: // API level 11
                                listString.add("NETWORK_TYPE_EHRPD NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_EVDO_B: // API level 9
                                listString.add("NETWORK_TYPE_EVDO_B NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_HSPAP: // API level 13
                                listString.add("NETWORK_TYPE_HSPAP NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_IDEN: // API level 8
                                listString.add("NETWORK_TYPE_IDEN NETWORK");
                                break;
                            case TelephonyManager.NETWORK_TYPE_LTE: // API level 11
                                listString.add("NETWORK_TYPE_LTE NETWORK");
                                break;
                            // Unknown
                            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                            default:
                                listString.add("UNKNOWM NETWORK");
                                break;
                        }

                    }
                }
            }
        }
        result.put("connectionType", listString);
        return result;
    }
}
