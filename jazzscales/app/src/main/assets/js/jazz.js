function fillTextArea(music) {
  document.getElementById('edit').value = music;
}

function editMusic() {
  //$('#edit').setAttribute('style', 'display:inline');
  document.getElementById('edit').setAttribute('style', 'display:inline');
}

function quitEditMusic() {
  //$('#edit').setAttribute('style', 'display:none');
  document.getElementById('edit').setAttribute('style', 'display:none');
}

function getMusic() {
  return document.getElementById('edit').value;
}

function renderMusic() {
  // Need to test
  var staffWidth = $('#edit').width() * 0.75;
  var music = '%%staffwidth ' + staffWidth + '\n' + getMusic();
  ABCJS.renderAbc('music', music);

  //ABCJS.renderAbc('music', '%%staffwidth ' + screen.width+ '\n' + getMusic());
}
