/* dependencies:
 *   - jquery.min.js
 *   - abcjs_basic_3.0-min.js
 */

// Global Variables Dictionary 
glob = {'path_to_jazz_orig': 'file:///android_asset/json/jazzData_orig.json',
        'music': null,
        'musicID': null,
        'lists': null,
        'collections': null,
        'json': null};

// Read jazzData immediately
if (glob.collections === null ) { 
  glob.json = readFile()
}

// Initialize Home to be the active button
$('#main-navbar li').on('click', function () {
    $('#main-navbar .active').attr("class", ""); 
    $('#'+this.id).attr("class", "active");
});

// Transpose the music snippet to key
function transpose(key) {
  glob.music = util.transpose(glob.music, key);
  $('#musicID').text(glob.musicID + ` (in ${key})`)
  ABCJS.renderAbc('music', glob.music);
}

// Filter Music json by type and name (should be unique)
function filterMusic(type, name) {
  var out = glob.json.filter(function(x) {return type==x.type && name==x.name});
  if (out.length == 0) {
    out = ""
  } else {
    out = out[0].music
  }
  return out
}

// show Music by type and name
function showMusic(type, name) {
  var staffWidth = $('#music').width() * .7;
  glob.music = "%%staffwidth " + staffWidth + "\n" + filterMusic(type, name);
  glob.musicID = name + " " + type //+ staffWidth
  console.log(glob.music)
  $('#musicID').text(glob.musicID)
  ABCJS.renderAbc('music', glob.music);
}


// Generate the list of available music when button 'All' is clicked
$('#all').on('click', function () {
  if ($('#all ul').length == 0) {
    $('#all').append('<ul class="dropdown-menu"></ui>')
    for (var i in glob.json) {
      var item = glob.json[i]
      $('#all ul').append(`
      <li role="presentation"><a role="menuitem" tabindex="-1"
        onclick='showMusic("${item.type}","${item.name}")'>
        ${item.name + " " + item.type}
      </a></li>
      `)
    }
  }
});


// When 'Transpose' is clicked, show list of available keys
$('#transpose').on('click', function () {
  if ($('#transpose ul').length == 0) {
    $('#transpose').append('<ul class="dropdown-menu"></ui>')
    for (var i in util.keySigs) {
      var key = util.keySigs[i]
      $('#transpose ul').append(`
        <li role="presentation"><a role="menuitem" tabindex="-1" 
            onclick='transpose("${key}")'>
            ${key}
        </a></li>`)
    }
  }
});

// Add, Delete, Remove Music /////////////////////////////////////
function fillTextArea(music) {
  $('textarea').val(music);
}
function editMusic() {
  $('textarea').setAttribute('style', 'display:inline');
}
function quitEditMusic() {
  $('textarea').setAttribute('style', 'display:none');
}
function getMusic() {
  return $('textarea').val();
}

function renderMusic() {
  var staffWidth = $('#edit').width() * 0.75;
  var music = '%%staffwidth ' + staffWidth + '\n' + getMusic();
  console.log(music)
  ABCJS.renderAbc('music', music);
}

// Add Music when addmusic is clicked
$('#add').on('click', function () {
  $('#editBox').setAttribute('style', 'display:inline');
});



// Delete Music
// Edit Music

function readFile() {
  return JSON.parse(app.readFile()).jazzData;
}

function writeFile(data) {
    return app.writeInternalFile(data);
}



/*  500: 695
 *  600: 828
 *  700: 961
 *  800: 1094
 *  900: 1227
 * 1000: 1360
 */
