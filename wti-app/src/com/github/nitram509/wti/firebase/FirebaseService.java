package com.github.nitram509.wti.firebase;

import android.util.Log;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;

public class FirebaseService {

  private static final String TAG = "FirebaseService";

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

  public void registerOutgoingCallbackHandler(final InitiereAnrufHandler initiereAnrufHandler) {
    final Firebase appOutgoingNumberRef = new Firebase("https://cti-demo.firebaseIO.com/app/outgoing");
    appOutgoingNumberRef.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        String nummer = null;
        Object dataSnapshotValue = dataSnapshot.getValue();
        if (dataSnapshotValue != null && !dataSnapshotValue.toString().isEmpty()) {
          nummer = dataSnapshotValue.toString();
        }
        Log.d(TAG, "Event: app/outgoing nummer = " + nummer);
        if (nummer != null) {
          initiereAnrufHandler.rufeAn(nummer);
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
