package com.github.nitram509.wti;

import android.view.View;
import com.github.nitram509.wti.firebase.FirebaseService;

class HangUpFirebaseClickListener implements View.OnClickListener {

  private final FirebaseService firebaseService;

  public HangUpFirebaseClickListener(FirebaseService firebaseService) {
    this.firebaseService = firebaseService;
  }

  @Override
  public void onClick(View view) {
    firebaseService.sendNumber(null);
    firebaseService.sendIncomingCall(null);
    firebaseService.sendConnected(false);
  }
}
