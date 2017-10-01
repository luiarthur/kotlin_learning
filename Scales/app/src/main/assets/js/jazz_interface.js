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

//function listenLongClick(tagObj, funcToExecute) {
//  var timer;
//  var longTime = 1 * 1000; // 1 second or 1000 milli-seconds
//
//  tagObj.on("mousedown touchstart", function(){
//    timer = setTimeout(funcToExecute , longTime);
//  }).on("mouseup touchend",function(){
//    //app.printLog(timer / 1000 + "seconds has passed (mouseup).");
//    clearTimeout(timer);
//  });
//}

function addEditDropdown(tagObj) {
  // make button open the menu
  tagObj.contextMenu();

  $.contextMenu({
      selector: tagObj.attr('id'), 
      trigger: 'none',
      callback: function(key, options) {
          var m = "clicked: " + key;
          window.console && console.log(m) || alert(m); 
      },
      items: {
          "edit": {name: "Edit", icon: "edit"},
          "cut": {name: "Cut", icon: "cut"},
          "copy": {name: "Copy", icon: "copy"},
          "paste": {name: "Paste", icon: "paste"},
          "delete": {name: "Delete", icon: "delete"},
          "sep1": "---------",
          "quit": {name: "Quit", icon: function($element, key, item){ return 'context-menu-icon context-menu-icon-quit'; }}
      }
  });
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

  //thisTag.on('contextmenu', function() {
  //  app.printLog("Long time has passed (long press).");
  //  addEditDropdown(thisTag);
  //});
  var menu = [{
          name: 'create',
          title: 'create button',
          fun: function () {
              app.printLog('i am add button')
          }
      }, {
          name: 'update',
          title: 'update button',
          fun: function () {
              alert('i am update button')
          }
      }, {
          name: 'delete',
          title: 'delete button',
          fun: function () {
              alert('i am delete button')
          }
      }];
   
  thisTag.contextMenu(menu, {triggerOn:"click"});

  //listenLongClick(thisTag, function() {
  //  app.printLog("Long time has passed (long press).");
  //  addEditDropdown(thisTag);
  //});

  // on long click:  allow user to edit or delete music selected
  //https://www.google.com/search?q=js+long+press&oq=js+long+press&aqs=chrome.0.0l4.3273j0j9&sourceid=chrome&ie=UTF-8
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

    var s = JSON.stringify(glob.json)

    writeFile(' {"jazzData":' + s + "}")
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
