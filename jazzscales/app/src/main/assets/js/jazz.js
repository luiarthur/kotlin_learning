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
  ABCJS.renderAbc('music', '%%staffwidth ' + screen.width+ '\n' + getMusic());
}
