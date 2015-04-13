package com.github.nitram509.wti;

import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import com.github.nitram509.wti.firebase.FirebaseService;

class ConnectFirebaseClickListener implements View.OnClickListener {

  public static final String ERR_NO_LINE_NUMBER_PRESENT = "00000000";

  private Bundle bundle;
  private TelephonyManager telephonyManager;

  private final FirebaseService firebaseService;

  public ConnectFirebaseClickListener(Bundle bundle, TelephonyManager telephonyManager, FirebaseService firebaseService) {
    this.bundle = bundle;
    this.telephonyManager = telephonyManager;
    this.firebaseService = firebaseService;
  }

  @Override
  public void onClick(View view) {
    firebaseService.sendConnected(true);
    firebaseService.sendNumber(telephonyManager.getLine1Number() != null ? telephonyManager.getLine1Number() : ERR_NO_LINE_NUMBER_PRESENT);
  }
}
