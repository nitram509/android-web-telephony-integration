package com.github.nitram509.wti;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Button;
import android.widget.TextView;
import com.github.nitram509.wti.firebase.FirebaseService;
import com.github.nitram509.wti.log.UserLogService;
import com.github.nitram509.wti.phonestate.PhoneStateChangedHandler;

import static com.github.nitram509.wti.TelephonyManagerTools.getLine1Number;

public class WtiDemo extends Activity {

  TelephonyManager telephonyManager;

  TextView txtUserLog;
  TextView txtOwnNumber;
  Button btnConnectFireBase;
  Button btnHangUpFireBase;

  private FirebaseService firebaseService;
  private PhoneStateListener phoneStateListener;
  private UserLogService userLogService;

  @Override
  public void onCreate(Bundle bundle) {
    super.onCreate(bundle);
    setContentView(R.layout.main);

    initUIElements();
    initServices();
    initFirebaseCallbackHandlers();

    txtOwnNumber.setText("Own number: " + getLine1Number(telephonyManager));

    telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);

    btnConnectFireBase.setOnClickListener(new ConnectFirebaseClickListener(telephonyManager, firebaseService, userLogService));
    btnHangUpFireBase.setOnClickListener(new HangUpFirebaseClickListener(firebaseService, userLogService));
  }

  @Override
  protected void onDestroy() {
    firebaseService.sendConnected(false);
    super.onDestroy();
  }

  private void initServices() {
    userLogService = new UserLogService(txtUserLog);

    firebaseService = new FirebaseService(userLogService);
    phoneStateListener = new PhoneStateChangedHandler(firebaseService, userLogService);
    telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
  }

  private void initFirebaseCallbackHandlers() {
    firebaseService.registerOutgoingCallbackHandler(new OutgoingCallHandler(this, userLogService));
  }

  private void initUIElements() {
    txtUserLog = (TextView) findViewById(R.id.txtUserLog);
    txtOwnNumber = (TextView) findViewById(R.id.txtOwnNumber);
    btnConnectFireBase = (Button) findViewById(R.id.btnConnectFirebase);
    btnHangUpFireBase = (Button) findViewById(R.id.btnHangUpFirebase);
  }

}
