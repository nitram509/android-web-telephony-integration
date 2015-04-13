package com.github.nitram509.wti;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Button;
import android.widget.TextView;
import com.github.nitram509.wti.firebase.FirebaseService;
import com.github.nitram509.wti.firebase.InitiereAnrufHandler;
import com.github.nitram509.wti.log.UserLogService;
import com.github.nitram509.wti.phonestate.PhoneStateChangedHandler;

public class WtiDemo extends Activity {

  TelephonyManager telephonyManager;

  TextView txtUserLog;
  TextView txtOwnNumber;
  Button btnConnectFireBase;
  Button btnHangUpFireBase;

  private FirebaseService firebaseService;
  private PhoneStateListener phoneStateListener;
  private UserLogService userLogService;

  /**
   * Called when the activity is first created.
   */
  @Override
  public void onCreate(Bundle bundle) {
    super.onCreate(bundle);
    setContentView(R.layout.main);

    initUIElements();
    initServices();
    initFirebaseCallbackHandlers();

    txtOwnNumber.setText(telephonyManager.getLine1Number());

    telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);

    btnConnectFireBase.setOnClickListener(new ConnectFirebaseClickListener(bundle, telephonyManager, firebaseService));
    btnHangUpFireBase.setOnClickListener(new HangUpFirebaseClickListener(firebaseService));
  }

  @Override
  protected void onDestroy() {
    firebaseService.sendConnected(false);
    super.onDestroy();
  }

  private void initServices() {
    userLogService = new UserLogService(txtUserLog);

    firebaseService = new FirebaseService();
    phoneStateListener = new PhoneStateChangedHandler(firebaseService, userLogService);
    telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
  }

  private void initFirebaseCallbackHandlers() {
    firebaseService.registerOutgoingCallbackHandler(new InitiereAnrufHandler() {
      @Override
      public void rufeAn(String nummer) {
        userLogService.log("Starte Anruf an: " + nummer);
        String uriString = "tel:" + nummer.trim();
        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(uriString));
        startActivity(callIntent);
      }
    });
  }

  private void initUIElements() {
    txtUserLog = (TextView) findViewById(R.id.txtUserLog);
    txtOwnNumber = (TextView) findViewById(R.id.txtOwnNumber);
    btnConnectFireBase = (Button) findViewById(R.id.btnConnectFirebase);
    btnHangUpFireBase = (Button) findViewById(R.id.btnHangUpFirebase);
  }

}
