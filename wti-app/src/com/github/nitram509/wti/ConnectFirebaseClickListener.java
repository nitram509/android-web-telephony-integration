package com.github.nitram509.wti;

import android.telephony.TelephonyManager;
import android.view.View;
import com.github.nitram509.wti.firebase.FirebaseService;
import com.github.nitram509.wti.log.UserLogService;

class ConnectFirebaseClickListener implements View.OnClickListener {

  private TelephonyManager telephonyManager;

  private final FirebaseService firebaseService;
  private UserLogService userLogService;

  public ConnectFirebaseClickListener(TelephonyManager telephonyManager, FirebaseService firebaseService, UserLogService userLogService) {
    this.telephonyManager = telephonyManager;
    this.firebaseService = firebaseService;
    this.userLogService = userLogService;
  }

  @Override
  public void onClick(View view) {
    firebaseService.sendConnected(true);
    firebaseService.sendNumber(TelephonyManagerTools.getLine1Number(telephonyManager));
    userLogService.log("send connect");
  }

}
