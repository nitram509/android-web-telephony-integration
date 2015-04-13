package com.github.nitram509.wti;

import android.view.View;
import com.github.nitram509.wti.firebase.FirebaseService;
import com.github.nitram509.wti.log.UserLogService;

class HangUpFirebaseClickListener implements View.OnClickListener {

  private final FirebaseService firebaseService;
  private UserLogService userLogService;

  public HangUpFirebaseClickListener(FirebaseService firebaseService, UserLogService userLogService) {
    this.firebaseService = firebaseService;
    this.userLogService = userLogService;
  }

  @Override
  public void onClick(View view) {
    firebaseService.sendNumber(null);
    firebaseService.sendIncomingCall(null);
    firebaseService.sendConnected(false);
    userLogService.log("send hangup");
  }
}
