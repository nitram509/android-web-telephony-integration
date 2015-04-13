package com.github.nitram509.wti.phonestate;

import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import com.github.nitram509.wti.firebase.FirebaseService;
import com.github.nitram509.wti.log.UserLogService;

public class PhoneStateChangedHandler extends PhoneStateListener {

  private FirebaseService firebaseService;
  private UserLogService userLogService;

  public PhoneStateChangedHandler(FirebaseService firebaseService, UserLogService userLogService) {
    this.firebaseService = firebaseService;
    this.userLogService = userLogService;
  }

  @Override
  public void onCallStateChanged(int state, String incomingNumber) {
    switch (state) {
      case TelephonyManager.CALL_STATE_IDLE:
        userLogService.log("Idle: " + incomingNumber);
        firebaseService.sendIncomingCall(null);
        break;
      case TelephonyManager.CALL_STATE_OFFHOOK:
        userLogService.log("Off Hook: " + incomingNumber);
        firebaseService.sendIncomingCall(null);
        break;
      case TelephonyManager.CALL_STATE_RINGING:
        userLogService.log("RINGING: " + incomingNumber);
        firebaseService.sendIncomingCall(incomingNumber);
        break;
      default:
        firebaseService.sendIncomingCall(null);
    }
  }
}
