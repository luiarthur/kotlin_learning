/* dependencies:
 *   - jquery.min.js
 *   - abcjs_basic_3.0-min.js
 */

//app.restoreAppDefaults();

$("#reset").on('click', function() {
  app.restoreAppDefaults();
})

$('#jazzScales').on('click', function () {
  $('#main-navbar').toggleClass('hide unhide')
})

function drop(s,n) {
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

function saveJson(jsonObj) {
  var s = JSON.stringify(jsonObj);
  writeFile(' {"jazzData":' + s + "}");
}

// Add, Delete, Remove Music /////////////////////////////////////
function fillTextArea(music) {
  $('textarea').val(music);
}
function editMusic() {
  // This doesn't work in android...
  //$'(textarea').style.display = 'inline';

  // better
  //$('.toggle-hide').toggleClass('hide unhide');

  // best
  if ($('.toggle-hide').hasClass('hide')) {
    $('.toggle-hide').toggleClass('hide unhide');
  }
}
function quitEditMusic() {
  // ok
  //$('.toggle-hide').toggleClass('hide unhide');

  // best
  if ($('.toggle-hide').hasClass('unhide')) {
    $('.toggle-hide').toggleClass('hide unhide');
  }
}
function getMusic() {
  return $('#taEdit').val();
}

function clearFields() {
  $("#taEdit").val("K:C\n");
  $("input").each(function () {
    $(this).val("");
  });
  $("#musicID").text("");
  renderMusic();
}

function renderMusic() {
  var staffWidth = Math.round($('nav').width() * .7 * .95);
  var music = '%%staffwidth ' + staffWidth + '\n' + getMusic();
  app.printLog(music);
  ABCJS.renderAbc('music', music);
}
// End of Add, Delete, Remove Music /////////////////////////////////


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
  app.printLog("before transpose:\n" + glob.music);

  glob.music = util.transpose(glob.music, key);
  $('#musicID').text(glob.musicID + ` (in ${key})`);

  app.printLog("after transpose:\n" + glob.music);

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
  var staffWidth = Math.round($('nav').width() * .7 * .95);
  glob.music = "%%staffwidth " + staffWidth + "\n" + filterMusic(type, name);
  glob.musicID = name + " " + type;
  $('#musicID').text(glob.musicID);
  ABCJS.renderAbc('music', glob.music);
}

function findIndex(arr, f) {
  // this does what `arr.findIndex(f)` does on browsers
  // that do not support `arr.findIndex`
  var index = -1;
  for (var i = 0; i < arr.length; ++i) {
    if (f(arr[i])) {
      index = i;
      break;
    }
  }
  return index;
}

function indexOfItem(arr, iType, iName) {
  idx = findIndex(arr, function(x) {
    return x.type == iType && x.name == iName;
  });
  return idx;
}

function saveMusic() {
    var music = getMusic()

    // abcjs_ext check for key sig
    var m = util.parse(music);
    var header = m.header;
    var text = m.text;
    if (!/K:.*/.test(header)) {
      music = header.trim() + '\nK:C\n' + text;
    }
    music = music.trim();

    app.printLog("got music");

    var inType = $("#inputType").val().trim();
    var inName = $("#inputName").val().trim();
    var inList = $("#inputList").val().trim();

    if (inType == "") {
      var msg = "No type provided.";
      app.printLog("Error:" + msg);
      throw new Error(msg);
    }

    if (inName == "") {
      var msg = "No Name:provided.";
      app.printLog("Error:" + msg);
      throw new Error(msg);
    }

    if (inList == "") {
      inList = [];
    }

    var idx = indexOfItem(glob.json, inType, inName);
    var item = {
      "type": inType,
      "name": inName,
      "list": inList,
      "music": music};
    
    if (idx == -1) {
      glob.json.push(item);
      appendToSelect(inType, inName)
    } else {
      glob.json[idx] = item;
    }

    saveJson(glob.json);

    app.printLog("music written")
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
    
  var thisTag = $("#"+thisID);

  var menu = [{
          name: 'Edit',
          title: 'edit button',
          fun: function () {
            app.printLog('Clicked Edit Button');
            // TODO

            // 0. get index of current item
            //var idx = glob.json.findIndex(function(x) {
            //  return x.type == iType && x.name == iName;
            //});
            var idx = indexOfItem(glob.json, iType, iName);

            var iList = glob.json[idx].lists;
            
            //// 1. open edit box
            editMusic();
            $("#inputType").val(iType); 
            $("#inputName").val(iName); 
            $("#inputList").val(iList); 

            glob.music = glob.json[idx].music;
            glob.musicID = thisID;
            glob.lists = iList;

            $("#taEdit").val(glob.music); 
            $('#musicID').text(glob.musicID);
            renderMusic();


            // 2. on keyup, refresh music
            // refresh on change
            $('#taEdit').on('keyup input', function () {
              renderMusic();
            });

            //// 3. on click save, save music
            // save
            $('#save').on('click', function () {
              saveMusic();
              quitEditMusic();
            });
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

            if (glob.json.length == 0) {
              app.printLog("Restoring App Defaults.");
              app.restoreAppDefaults();
            }
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



// Add Music when addmusic is clicked
$('#add').on('click', function () {
  editMusic();
  clearFields();

  // refresh on change
  $('#taEdit').on('keyup input', function () {
    renderMusic();
  });

  // save
  $('#save').on('click', function () {
    saveMusic();
    quitEditMusic();
  });

});

$('#cancel').on('click', function () {
  quitEditMusic();
});


currentOctave = 0;
$("#musicID").on('click', function() {
  console.log(glob.music);
  if (currentOctave == 0) {
    glob.music = util.octaveUp(glob.music);
    currentOctave = 1;
  } else if (currentOctave == 1) {
    glob.music = util.octaveDown(glob.music);
    glob.music = util.octaveDown(glob.music);
    currentOctave = -1;
  } else {
    glob.music = util.octaveUp(glob.music);
    currentOctave = 0;
  }

  var staffWidth = $('#edit').width() * 0.75;
  var music = '%%staffwidth ' + staffWidth + '\n' + glob.music;
  console.log(music)
  ABCJS.renderAbc('music', music);
});


/*  500: 695
 *  600: 828
 *  700: 961
 *  800: 1094
 *  900: 1227
 * 1000: 1360
 */
