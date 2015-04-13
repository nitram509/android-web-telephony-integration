package com.github.nitram509.wti.log;

import android.text.format.DateFormat;
import android.widget.TextView;

import java.util.ArrayDeque;
import java.util.Date;

public class UserLogService {

  private static final int MAX_ELEMENTS = 20;

  private TextView txtUserLog;
  private ArrayDeque<String> entries = new ArrayDeque<>();


  public UserLogService(TextView txtUserLog) {
    this.txtUserLog = txtUserLog;
  }

  public void log(String text) {
    txtUserLog.setText(appendEntryAndCreateText(text));
  }

  private String appendEntryAndCreateText(String text) {
    CharSequence time = DateFormat.format("hh:mm", new Date());
    entries.offer(time + "> " + text + "\n");
    while (entries.size() > MAX_ELEMENTS) {
      entries.removeFirst();
    }
    StringBuilder sb = new StringBuilder();
    for (String entry : entries) {
      sb.append(entry);
    }
    return sb.toString();
  }
}
