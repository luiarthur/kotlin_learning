/* dependencies:
 *   - jquery.min.js
 *   - abcjs_basic_3.0-min.js
 */

//app.restoreAppDefaults()

$('#jazzScales').on('click', function () {
  $('#main-navbar').toggleClass('hide unhide')
})

function drop(s,n=1) {
  return s.substring(0, s.length - n);
}

function readFile() {
  var content = drop(app.readFile(),1) // drop last character for android
  var jsonData = JSON.parse(content).jazzData;
  return jsonData;
}

function writeFile(data) {
    data = " " + data // pad with space for android
    return app.writeInternalFile(data);
}

function saveJson(globJson) {
  var s = JSON.stringify(globJson);
  writeFile(' {"jazzData":' + s + "}");
}

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
  glob.musicID = name + " " + type;
  console.log(glob.music);
  $('#musicID').text(glob.musicID);
  ABCJS.renderAbc('music', glob.music);
}


function appendToSelect(iType, iName) {
  var thisID = iName+iType

  $('#select ul').append(`
    <li role="presentation" id="${thisID}">
      <a role="menuitem" tabindex="-1"
        onclick='showMusic("${iType}","${iName}")'>
        ${iName + " " + iType}
      </a>
    </li>
  `);
    
  var thisTag = $("#"+thisID)

  var menu = [{
          name: 'Edit',
          title: 'edit button',
          fun: function () {
            app.printLog('Clicked Edit Button');
            // TODO
            
            // 1. open edit box
            // 2. on keyup, refresh music
            // 3. on click save, save music
          }
      }, {
          name: 'Delete',
          title: 'delete button',
          fun: function () {
            app.printLog('Clicked Delete Button')

            // change the global json
            glob.json = glob.json.filter(function(x) {
              return (x.type != iType || x.name != iName);
            });

            // remove this tag from selection
            thisTag.remove();
            
            // TODO: Clean Display
            //if (thisID == $("#musicID").attr("id")) {
            //  $("#musicID").text("");
            //  ABCJS.renderAbc('music', '');
            //}

            // TODO: Promp --- ARE YOU SURE YOU WANT TO DELETE?
            saveJson(glob.json);
          }
      }];
   
  thisTag.contextMenu(menu, {triggerOn:"click"});
}

// Generate the list of available music when button 'All' is clicked
$('#select').on('click', function () {
  if ($('#select ul').length == 0) {
    $('#select').append('<ul class="dropdown-menu"></ui>')
    for (var i in glob.json) {
      var item = glob.json[i]
      appendToSelect(item.type, item.name)
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
// End of Add, Delete, Remove Music /////////////////////////////////


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
    app.printLog("got music")

    var inType = $("#inputType").val()
    var inName = $("#inputName").val()
    var inList = $("#inputList").val()

    if (inType.trim() == "") {
      throw new Error("No type provided.");
    }

    if (inName.trim() == "") {
      throw new Error("No Name provided.");
    }

    if (inList.trim() == "") {
      inList = []
    }

    glob.json.push({
      "type": inType,
      "name": inName,
      "list": inList,
      "music": music});

    //var s = JSON.stringify(glob.json)
    //writeFile(' {"jazzData":' + s + "}")
    saveJson(glob.json);

    appendToSelect(inType, inName)

    app.printLog("music written")
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
