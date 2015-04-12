var draw_qrcode = function (text, typeNumber, errorCorrectLevel) {
  document.write(create_qrcode(text, typeNumber, errorCorrectLevel));
};

var create_qrcode = function (text, typeNumber, errorCorrectLevel, table) {

  var qr = qrcode(typeNumber || 4, errorCorrectLevel || 'M');
  qr.addData(text);
  qr.make();

  return qr.createImgTag(4, 4);
};

var update_qrcode = function () {
  var text = $('#msg').val().replace(/^[\s\u3000]+|[\s\u3000]+$/g, '');
  $('#qr').html(create_qrcode(text));
};