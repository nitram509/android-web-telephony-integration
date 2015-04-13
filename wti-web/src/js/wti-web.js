function onIncomingCall(number) {

  function _normalizeTelefonNumber(number) {
    return ("" + number)
        .replace(/\s/g, "")
        .replace("0049", "0")
        .replace("+49", "0")
        .replace(/[^\d]/g, "")
  }

  function _findCalleeInfo(number) {
    number = _normalizeTelefonNumber(number);
    if (number.length > 0) {
      for (var i = 0; i < phonebook.length; i++) {
        var calleeDto = phonebook[i];
        if (calleeDto.phone_1 === number || calleeDto.mobile === number) {
          return {name: calleeDto.name, avatar: "phonebook/" + calleeDto.avatar_url};
        }
      }
    }
    return {name: "Unbekannt", avatar: "phonebook/nophoto.gif"}
  }


  if (number && number !== null) {
    var calleeInfo = _findCalleeInfo(number);
    $("#calleeNumber").text(number);
    $("#calleeName").text(calleeInfo.name);
    $("#calleeAvatar").attr("src", calleeInfo.avatar);
    $("#calleeInfo").fadeIn();
  } else {
    $("#calleeInfo").fadeOut();
  }
}

(function cti_demo_web() {

  var rootRef = new Firebase('https://cti-demo.firebaseIO.com/');
  var appNumberRef = new Firebase('https://cti-demo.firebaseIO.com/app/number');
  var appConnectedRef = new Firebase('https://cti-demo.firebaseIO.com/app/connected');
  var appIncomingRef = new Firebase('https://cti-demo.firebaseIO.com/app/incoming');

  function _initDomEventHandlers() {
    $(window).on('unload', function (event) {
      _sendWebConnected(false);
    });
    $('#btnCall').click(function (e) {
      var nr = $('#txtOutgoingNumber').val();
      if (nr && nr.length > 0) {
        _sendOutgoingCallWithNumber(""); // only to initiate a new change event - ugly hack, but works ;-)
        _sendOutgoingCallWithNumber(nr);
      }
    });
  }

  function _initFirebaseHandlers() {
    appNumberRef.on('value', function (snapshot) {
      if (snapshot.val() !== null) {
        var ownMobileNumber = snapshot.val();
        ownMobileNumber = ownMobileNumber != '00000000' ? ownMobileNumber : "Connected, but number not provided by Android app";
        $("#appNumber").text(ownMobileNumber);
      } else {
        $("#appNumber").text("");
      }
    });

    appConnectedRef.on('value', function (snapshot) {
      if (snapshot.val()) {
        $("#waitingMessageBlock").hide();
        $("#outgoingCallBox").show('fast');
      } else {
        $("#waitingMessageBlock").show();
        $("#outgoingCallBox").hide('fast');
      }
    });

    appIncomingRef.on('value', function (snapshot) {
      onIncomingCall(snapshot.val());
    });
  }

  function _sendWebConnected(statusFlag) {
    rootRef.set(
        {
          'web': {
            'connected': statusFlag
          }
        }
    );
  }

  function _sendOutgoingCallWithNumber(number) {
    rootRef.set(
        {
          'app': {
            'outgoing': number
          }
        }
    );
  }

  function _showPhoneBookEntries() {
    var $table = $('<table>');

    function createTD(text, isHeader) {
      var $td = $('<td>');
      if (isHeader) {
        $bold = $('<span>');
        $bold.addClass('phonebook-header');
        $bold.text(text);
        $td.append($bold)
      } else {
        $td.text(text);
      }
      return $td;
    }

    var $tr = $('<tr>');
    $table.append($tr);
    $tr.append(createTD("Avatar", true));
    $tr.append(createTD("Name", true));
    $tr.append(createTD("Phone 1", true));
    $tr.append(createTD("Mobile", true));

    for (var i = 0; i < phonebook.length; i++) {
      var entry = phonebook[i];
      $tr = $('<tr>');
      $table.append($tr);
      var $img = $('<img>');
      $img.attr('src', 'phonebook/' + entry.avatar_url);
      $img.attr('height', '32px');
      $tr.append($img);
      $tr.append(createTD(entry.name));
      $tr.append(createTD(entry.phone_1));
      $tr.append(createTD(entry.mobile));
    }
    $('#phonebook').replaceWith($table);
  }

  _initDomEventHandlers();
  _initFirebaseHandlers();
  _sendWebConnected(true);
  _showPhoneBookEntries();

})();
