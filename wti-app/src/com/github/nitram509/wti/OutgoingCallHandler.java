package com.github.nitram509.wti;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import com.github.nitram509.wti.firebase.CallHandler;
import com.github.nitram509.wti.log.UserLogService;

class OutgoingCallHandler implements CallHandler {

  private final Activity activity;
  private final UserLogService userLogService;

  public OutgoingCallHandler(Activity activity, UserLogService userLogService) {
    this.activity = activity;
    this.userLogService = userLogService;
  }

  @Override
  public void doCall(String number) {
    userLogService.log("Starting call to: " + number);
    String uriString = "tel:" + number.trim();
    Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(uriString));
    try {
      activity.startActivity(callIntent);
    } catch (Exception e) {
      userLogService.log("Error: " + e.getMessage());
    }
  }
}
