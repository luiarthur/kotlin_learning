/* dependencies:
 *   - jquery.min.js
 *   - abcjs_basic_3.0-min.js
 */

// Global Variables Dictionary 
global_vars = {'path_to_jazz_orig': '/assets/jazzData_orig.txt',
               'path_to_user_jazz': '/assets/userCreated/jazzData.txt'}

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

function readFile(path=global_vars.path_to_jazz_orig, var_name='fileContent') {
  $.ajax({ url: path, success: function(file_content) {
      global_vars[var_name] = file_content;
    }
  });
}

function writeFile(path=global_vars.path_to_user_jazz, var_name='fileContent') {
  $.ajax({ url: path, success: function(file_content) {
      global_vars[var_name] = file_content;
    }
  });
}

function showExample() {
  readFile();
  fillTextArea(global_vars.fileContent);
}


/*  500: 695
 *  600: 828
 *  700: 961
 *  800: 1094
 *  900: 1227
 * 1000: 1360
 */
