/* dependencies:
 *   - jquery.min.js
 *   - abcjs_basic_3.0-min.js
 */

function fillTextArea(music) {
  $('#edit').val(music);
}

function editMusic() {
  //document.getElementById('edit').setAttribute('style', 'display:inline');
  $('#edit').setAttribute('style', 'display:inline');
}

function quitEditMusic() {
  $('#edit').setAttribute('style', 'display:none');
}

function getMusic() {
  return $('#edit').val();
}

function renderMusic() {
  var staffWidth = $('#edit').width() * 0.75;
  var music = '%%staffwidth ' + staffWidth + '\n' + getMusic();
  console.log(music)
  ABCJS.renderAbc('music', music);
}
/*  500: 695
 *  600: 828
 *  700: 961
 *  800: 1094
 *  900: 1227
 * 1000: 1360
 */
