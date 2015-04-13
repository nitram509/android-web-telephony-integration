package com.github.nitram509.wti.firebase;

import android.util.Log;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;
import com.github.nitram509.wti.log.UserLogService;

public class FirebaseService {

  private static final String TAG = "FirebaseService";

  private UserLogService userLogService;

  public FirebaseService(UserLogService userLogService) {
    this.userLogService = userLogService;
  }

  public void sendConnected(boolean connected) {
    Firebase appConnectedRef = new Firebase("https://cti-demo.firebaseIO.com/app/connected");
    appConnectedRef.setValue(Boolean.valueOf(connected));
    Log.d(TAG, "send connected=true");
  }

  public void sendNumber(String number) {
    Firebase appNumberRef = new Firebase("https://cti-demo.firebaseIO.com/app/number");
    appNumberRef.setValue(number);
    Log.d(TAG, "send number=" + number);
  }

  public void sendIncomingCall(String incomingNumber) {
    Firebase appNumberRef = new Firebase("https://cti-demo.firebaseIO.com/app/incoming");
    appNumberRef.setValue(incomingNumber);
    Log.d(TAG, "send incoming=" + incomingNumber);
  }

  public void registerOutgoingCallbackHandler(final CallHandler callHandler) {
    final Firebase appOutgoingNumberRef = new Firebase("https://cti-demo.firebaseIO.com/app/outgoing");
    appOutgoingNumberRef.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        String number = null;
        Object dataSnapshotValue = dataSnapshot.getValue();
        if (dataSnapshotValue != null && !dataSnapshotValue.toString().isEmpty()) {
          number = dataSnapshotValue.toString();
        }
        userLogService.log("Event: app/outgoing number = " + number);
        if (number != null) {
          callHandler.doCall(number);
          appOutgoingNumberRef.setValue(null);
        }
      }

      @Override
      public void onCancelled() {
        Log.d(TAG, "Event: app/outgoing canceled");
      }
    });
  }
}
