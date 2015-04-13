package com.github.nitram509.wti.log;

import android.text.format.DateFormat;
import android.widget.TextView;

import java.util.Date;

public class UserLogService {

  private TextView txtUserLog;

  public UserLogService(TextView txtUserLog) {
    this.txtUserLog = txtUserLog;
  }

  public void log(String text) {
    CharSequence time = DateFormat.format("hh:mm", new Date());
    txtUserLog.append(time + "> " + text + "\n");
  }
}
