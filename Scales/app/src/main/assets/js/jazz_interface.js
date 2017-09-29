/* dependencies:
 *   - jquery.min.js
 *   - abcjs_basic_3.0-min.js
 */

app.restoreAppDefaults()

// Global Variables Dictionary 
glob = {'music': null,
        'musicID': null,
        'lists': null,
        'collections': null,
        'json': null};

// Read jazzData immediately
if (glob.collections === null ) { 
  glob.json = readFile()
}

// Hide textarea
//$('#taEdit').append($('#taEdit').style);
//$('#taEdit').style.display = 'none';
//$('#taEdit').setAttribute('style', 'display:none;');

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


function readFile() {
  return JSON.parse(app.readFile()).jazzData;
}

function writeFile(data) {
    return app.writeInternalFile(data);
}


// Add, Delete, Remove Music /////////////////////////////////////
function fillTextArea(music) {
  $('textarea').val(music);
}
function editMusic() {
  // This doesn't work in android...
  //$'(textarea').style.display = 'inline';
  $('.toggle-hide').toggleClass('hide unhide')
}
function quitEditMusic() {
  $('.toggle-hide').toggleClass('hide unhide');
  // save music
}
function getMusic() {
  return $('#taEdit').val();
}

function renderMusic() {
  var staffWidth = $('#music').width() * 0.7;
  var music = '%%staffwidth ' + staffWidth + '\n' + getMusic();
  ABCJS.renderAbc('music', music);
}


// Add Music when addmusic is clicked
$('#add').on('click', function () {
  editMusic()

  // refresh on change
  $('#taEdit').on('keyup input', function () {
    renderMusic()
  });

  // save
  $('#save').on('click', function() {
    var music = getMusic()

    //glob.json.push({
    //  "type": $("#inputType").value(), 
    //  "name": $("#inputName").value(),
    //  "music": music});

    writeFile(' {"jazzData":' + JSON.stringify(glob.json) + "}")
    quitEditMusic()
  });
});



// Delete Music
// Edit Music



/*  500: 695
 *  600: 828
 *  700: 961
 *  800: 1094
 *  900: 1227
 * 1000: 1360
 */
