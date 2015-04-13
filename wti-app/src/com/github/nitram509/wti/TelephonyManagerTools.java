package com.github.nitram509.wti;

import android.telephony.TelephonyManager;

class TelephonyManagerTools {

  public static final String ERR_NO_LINE_NUMBER_PRESENT = "00000000";

  public static String getLine1Number(TelephonyManager telephonyManager) {
    String line1Number = telephonyManager.getLine1Number();
    return (line1Number != null && !line1Number.trim().isEmpty()) ? line1Number : ERR_NO_LINE_NUMBER_PRESENT;
  }
}
