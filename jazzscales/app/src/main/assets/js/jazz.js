define(['exports', 'kotlin'], function (_, Kotlin) {
  'use strict';
  var CharRange = Kotlin.kotlin.ranges.CharRange;
  var listOf = Kotlin.kotlin.collections.listOf_mh5how$;
  var flatten = Kotlin.kotlin.collections.flatten_u0ad8z$;
  var IntRange = Kotlin.kotlin.ranges.IntRange;
  var listOf_0 = Kotlin.kotlin.collections.listOf_i5x0yv$;
  var drop = Kotlin.kotlin.collections.drop_ba2ldo$;
  var take = Kotlin.kotlin.collections.take_ba2ldo$;
  var plus = Kotlin.kotlin.collections.plus_mydzjv$;
  var plus_0 = Kotlin.kotlin.collections.plus_qloxvw$;
  var get_indices = Kotlin.kotlin.collections.get_indices_m7z4lg$;
  var toList = Kotlin.kotlin.collections.toList_us0mfu$;
  var toInt = Kotlin.kotlin.text.toInt_pdl1vz$;
  var drop_0 = Kotlin.kotlin.text.drop_6ic1pp$;
  var first = Kotlin.kotlin.collections.first_2p1efm$;
  var IllegalArgumentException = Kotlin.kotlin.IllegalArgumentException;
  var dropLast = Kotlin.kotlin.collections.dropLast_yzln2o$;
  var withIndex = Kotlin.kotlin.collections.withIndex_7wnvza$;
  var get_indices_0 = Kotlin.kotlin.collections.get_indices_gzk92b$;
  Chord.prototype = Object.create(ListNotes.prototype);
  Chord.prototype.constructor = Chord;
  Scale.prototype = Object.create(ListNotes.prototype);
  Scale.prototype.constructor = Scale;
  function Chord(notes, key) {
    if (key === void 0)
      key = null;
    ListNotes.call(this, notes);
    this.notes_igddhn$_0 = notes;
    this.key = key;
  }
  Object.defineProperty(Chord.prototype, 'notes', {
    get: function () {
      return this.notes_igddhn$_0;
    }
  });
  Chord.prototype.transpose_za3lpa$ = function (halfSteps) {
    return new Chord(this.generalTranspose_za3lpa$(halfSteps), this.key);
  };
  Chord.prototype.circle_za3lpa$ = function (direction) {
    return new Chord(this.generalCircle_za3lpa$(direction), this.key);
  };
  Chord.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'Chord',
    interfaces: [ListNotes]
  };
  function Degree(d, accidental) {
    if (accidental === void 0)
      accidental = '';
    this.d = d;
    this.accidental = accidental;
  }
  Degree.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'Degree',
    interfaces: []
  };
  Degree.prototype.component1 = function () {
    return this.d;
  };
  Degree.prototype.component2 = function () {
    return this.accidental;
  };
  Degree.prototype.copy_19mbxw$ = function (d, accidental) {
    return new Degree(d === void 0 ? this.d : d, accidental === void 0 ? this.accidental : accidental);
  };
  Degree.prototype.toString = function () {
    return 'Degree(d=' + Kotlin.toString(this.d) + (', accidental=' + Kotlin.toString(this.accidental)) + ')';
  };
  Degree.prototype.hashCode = function () {
    var result = 0;
    result = result * 31 + Kotlin.hashCode(this.d) | 0;
    result = result * 31 + Kotlin.hashCode(this.accidental) | 0;
    return result;
  };
  Degree.prototype.equals = function (other) {
    return this === other || (other !== null && (typeof other === 'object' && (Object.getPrototypeOf(this) === Object.getPrototypeOf(other) && (Kotlin.equals(this.d, other.d) && Kotlin.equals(this.accidental, other.accidental)))));
  };
  var letterNames;
  var octaves;
  var accidentals;
  var lowestNote;
  var HighestNote;
  var sharpableNotes;
  var pianoNotes;
  function resolve(s) {
    throw new Kotlin.kotlin.NotImplementedError();
  }
  function ionian(startNote) {
    var tmp$, tmp$_0, tmp$_1, tmp$_2;
    var increments = listOf_0([2, 2, 1, 2, 2, 2, 1]);
    var idx = letterNames.indexOf_11rb$(startNote.letter);
    var letters = plus_0(plus(drop(letterNames, idx), take(letterNames, idx)), startNote.letter);
    var array = Array(8);
    var tmp$_3;
    tmp$_3 = array.length - 1 | 0;
    for (var i = 0; i <= tmp$_3; i++) {
      array[i] = startNote;
    }
    var scale = array;
    tmp$ = get_indices(scale);
    tmp$_0 = tmp$.first;
    tmp$_1 = tmp$.last;
    tmp$_2 = tmp$.step;
    for (var i_0 = tmp$_0; i_0 <= tmp$_1; i_0 += tmp$_2) {
      if (i_0 === 0)
        scale[i_0] = startNote;
      else {
        scale[i_0] = scale[i_0 - 1 | 0].transpose_za3lpa$(increments.get_za3lpa$(i_0 - 1 | 0)).toEnharmonic_61zpoe$(letters.get_za3lpa$(i_0));
      }
    }
    return new Scale(toList(scale), new Key(startNote.letter));
  }
  function flexScale$lambda(it) {
    return ionian(it);
  }
  function flexScale(degrees, startNote, baseScale) {
    if (baseScale === void 0)
      baseScale = flexScale$lambda;
    var bs = baseScale(startNote);
    var destination = Kotlin.kotlin.collections.ArrayList_init_ww73n8$(Kotlin.kotlin.collections.collectionSizeOrDefault_ba2ldo$(degrees, 10));
    var tmp$;
    tmp$ = degrees.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      destination.add_11rb$(bs.degree_i148pp$(item));
    }
    return new Scale(destination, new Key(startNote.letter + startNote.accidental));
  }
  function Key(key) {
    this.key = key;
  }
  Key.prototype.isValid = function () {
    return Note_init(this.key).isValid();
  };
  Key.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'Key',
    interfaces: []
  };
  function ListNotes(notes) {
    this.notes_b7tvfy$_0 = notes;
  }
  Object.defineProperty(ListNotes.prototype, 'notes', {
    get: function () {
      return this.notes_b7tvfy$_0;
    }
  });
  ListNotes.prototype.toString = function () {
    return this.notes.toString();
  };
  ListNotes.prototype.isValid = function () {
    var $receiver = this.notes;
    var all$result;
    all$break: do {
      var tmp$;
      tmp$ = $receiver.iterator();
      while (tmp$.hasNext()) {
        var element = tmp$.next();
        if (!element.isValid()) {
          all$result = false;
          break all$break;
        }
      }
      all$result = true;
    }
     while (false);
    return all$result;
  };
  ListNotes.prototype.generalTranspose_za3lpa$ = function (halfSteps) {
    var $receiver = this.notes;
    var destination = Kotlin.kotlin.collections.ArrayList_init_ww73n8$(Kotlin.kotlin.collections.collectionSizeOrDefault_ba2ldo$($receiver, 10));
    var tmp$;
    tmp$ = $receiver.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      destination.add_11rb$(item.transpose_za3lpa$(halfSteps));
    }
    return destination;
  };
  ListNotes.prototype.generalCircle_za3lpa$ = function (direction) {
    if (direction === void 0)
      direction = 4;
    throw new Kotlin.kotlin.NotImplementedError();
  };
  ListNotes.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'ListNotes',
    interfaces: []
  };
  function Note(letter, octave, accidental) {
    if (accidental === void 0)
      accidental = '';
    this.letter = letter;
    this.octave = octave;
    this.accidental = accidental;
  }
  Note.prototype.toString = function () {
    return this.letter + Kotlin.toString(this.octave) + this.accidental;
  };
  Note.prototype.standardize = function () {
    var tmp$, tmp$_0, tmp$_1;
    tmp$ = this.accidental;
    if (Kotlin.equals(tmp$, '#'))
      tmp$_0 = 1;
    else if (Kotlin.equals(tmp$, 'x'))
      tmp$_0 = 2;
    else if (Kotlin.equals(tmp$, 'b'))
      tmp$_0 = -1;
    else if (Kotlin.equals(tmp$, 'bb'))
      tmp$_0 = -2;
    else
      tmp$_0 = 0;
    var halfSteps = tmp$_0;
    var currentIdx = pianoNotes.indexOf_11rb$(this.letter.toString() + Kotlin.toString(this.octave));
    var enharmonicIdx = currentIdx + halfSteps | 0;
    if (currentIdx > -1 && 0 <= enharmonicIdx && enharmonicIdx < 88) {
      var enharmonic = pianoNotes.get_za3lpa$(enharmonicIdx);
      var eLetter = String.fromCharCode(Kotlin.toBoxedChar(enharmonic.charCodeAt(0)));
      var eOctave = toInt(String.fromCharCode(Kotlin.toBoxedChar(enharmonic.charCodeAt(1))));
      var eAccidental = drop_0(enharmonic, 2);
      tmp$_1 = new Note(eLetter, eOctave, eAccidental);
    }
     else
      tmp$_1 = null;
    var note = tmp$_1;
    return note;
  };
  Note.prototype.equals = function (other) {
    if (Kotlin.isType(other, Note))
      return Kotlin.equals(Kotlin.toString(this.standardize()), Kotlin.toString(other.standardize()));
    else
      return false;
  };
  Note.prototype.toSharp = function () {
    throw new Kotlin.kotlin.NotImplementedError();
  };
  Note.prototype.toFlat = function () {
    throw new Kotlin.kotlin.NotImplementedError();
  };
  Note.prototype.toEnharmonic_61zpoe$ = function (e) {
    var $receiver = accidentals;
    var destination = Kotlin.kotlin.collections.ArrayList_init_ww73n8$(Kotlin.kotlin.collections.collectionSizeOrDefault_ba2ldo$($receiver, 10));
    var tmp$;
    tmp$ = $receiver.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      var tmp$_0 = destination.add_11rb$;
      var tmp$_1;
      if (Kotlin.equals(e, 'B') && Kotlin.equals(this.letter, 'C'))
        tmp$_1 = this.octave - 1 | 0;
      else if (Kotlin.equals(e, 'C') && Kotlin.equals(this.letter, 'B'))
        tmp$_1 = this.octave + 1 | 0;
      else
        tmp$_1 = this.octave;
      var oct = tmp$_1;
      tmp$_0.call(destination, Note_init(e + oct.toString() + item));
    }
    var candidates = destination;
    var destination_0 = Kotlin.kotlin.collections.ArrayList_init_ww73n8$();
    var tmp$_2;
    tmp$_2 = candidates.iterator();
    while (tmp$_2.hasNext()) {
      var element = tmp$_2.next();
      if (element != null ? element.equals(this) : null)
        destination_0.add_11rb$(element);
    }
    return first(destination_0);
  };
  Note.prototype.isValid = function () {
    if (this.standardize() == null)
      return false;
    else
      return true;
  };
  Note.prototype.transpose_za3lpa$ = function (halfSteps) {
    if (!(-12 <= halfSteps && halfSteps <= 12)) {
      var message = 'halfSteps must be between -12 and 12 (inclusive).';
      throw new Kotlin.kotlin.IllegalArgumentException(message.toString());
    }
    var idx = pianoNotes.indexOf_11rb$(Kotlin.toString(this.standardize())) + halfSteps | 0;
    return Note_init(pianoNotes.get_za3lpa$(idx));
  };
  Note.prototype.dist_hy3q2x$ = function (that) {
    return pianoNotes.indexOf_11rb$(Kotlin.toString(this.standardize())) - pianoNotes.indexOf_11rb$(Kotlin.toString(that.standardize())) | 0;
  };
  Note.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'Note',
    interfaces: []
  };
  function Note_init(s, $this) {
    $this = $this || Object.create(Note.prototype);
    Note.call($this, String.fromCharCode(Kotlin.toBoxedChar(s.charCodeAt(0))), toInt(String.fromCharCode(Kotlin.toBoxedChar(s.charCodeAt(1)))), drop_0(s, 2));
    return $this;
  }
  function Scale(notes, key) {
    ListNotes.call(this, notes);
    this.notes_aynge1$_0 = notes;
    this.key = key;
    var $receiver = drop(get_indices_0(this.notes), 1);
    var destination = Kotlin.kotlin.collections.ArrayList_init_ww73n8$(Kotlin.kotlin.collections.collectionSizeOrDefault_ba2ldo$($receiver, 10));
    var tmp$;
    tmp$ = $receiver.iterator();
    while (tmp$.hasNext()) {
      var item = tmp$.next();
      destination.add_11rb$(this.notes.get_za3lpa$(item).dist_hy3q2x$(this.notes.get_za3lpa$(item - 1 | 0)));
    }
    this.increments = destination;
  }
  Object.defineProperty(Scale.prototype, 'notes', {
    get: function () {
      return this.notes_aynge1$_0;
    }
  });
  Scale.prototype.transpose_za3lpa$ = function (halfSteps) {
    return new Scale(this.generalTranspose_za3lpa$(halfSteps), this.key);
  };
  Scale.prototype.degree_i148pp$ = function (deg) {
    var tmp$;
    var d = deg.d;
    var a = deg.accidental;
    if (!(1 <= d && d <= 8)) {
      var message = 'The degree(d) must be between 1 and 8 (inclusive).';
      throw new Kotlin.kotlin.IllegalArgumentException(message.toString());
    }
    if (Kotlin.equals(a, '#'))
      tmp$ = 1;
    else if (Kotlin.equals(a, 'b'))
      tmp$ = -1;
    else if (Kotlin.equals(a, '') || Kotlin.equals(a, 'n'))
      tmp$ = 0;
    else
      throw new IllegalArgumentException('Only # and b are accepted as accidentals!');
    var increment = tmp$;
    var note = this.notes.get_za3lpa$(d - 1 | 0);
    return note.transpose_za3lpa$(increment).toEnharmonic_61zpoe$(note.letter);
  };
  Scale.prototype.blockChord_pqoyrt$ = function (intervals) {
    var all$result;
    all$break: do {
      var tmp$;
      tmp$ = intervals.iterator();
      while (tmp$.hasNext()) {
        var element = tmp$.next();
        if (!(1 <= element && element <= this.notes.size)) {
          all$result = false;
          break all$break;
        }
      }
      all$result = true;
    }
     while (false);
    if (!all$result) {
      var message = 'intervals have to be between 1 and ' + this.notes.size;
      throw new Kotlin.kotlin.IllegalArgumentException(message.toString());
    }
    var scale = dropLast(this.notes, 1);
    var destination = Kotlin.kotlin.collections.ArrayList_init_ww73n8$(Kotlin.kotlin.collections.collectionSizeOrDefault_ba2ldo$(intervals, 10));
    var tmp$_0;
    tmp$_0 = intervals.iterator();
    while (tmp$_0.hasNext()) {
      var item = tmp$_0.next();
      var tmp$_1 = destination.add_11rb$;
      var tmp$_2 = drop(scale, item - 1 | 0);
      var $receiver = take(scale, item - 1 | 0);
      var destination_0 = Kotlin.kotlin.collections.ArrayList_init_ww73n8$(Kotlin.kotlin.collections.collectionSizeOrDefault_ba2ldo$($receiver, 10));
      var tmp$_3;
      tmp$_3 = $receiver.iterator();
      while (tmp$_3.hasNext()) {
        var item_0 = tmp$_3.next();
        destination_0.add_11rb$(item_0.transpose_za3lpa$(12));
      }
      tmp$_1.call(destination, plus_0(plus(tmp$_2, destination_0), first(drop(scale, item - 1 | 0)).transpose_za3lpa$(12)));
    }
    var parScale = destination;
    var $receiver_0 = withIndex(flatten(parScale));
    var destination_1 = Kotlin.kotlin.collections.LinkedHashMap_init_q3lmfv$();
    var tmp$_4;
    tmp$_4 = $receiver_0.iterator();
    while (tmp$_4.hasNext()) {
      var element_0 = tmp$_4.next();
      var key = (element_0.index + 1 | 0) % this.notes.size;
      var tmp$_0_0;
      var value = destination_1.get_11rb$(key);
      if (value == null) {
        var answer = Kotlin.kotlin.collections.ArrayList_init_ww73n8$();
        destination_1.put_xwzc9p$(key, answer);
        tmp$_0_0 = answer;
      }
       else {
        tmp$_0_0 = value;
      }
      var list = tmp$_0_0;
      list.add_11rb$(element_0);
    }
    var $receiver_1 = destination_1.values;
    var destination_2 = Kotlin.kotlin.collections.ArrayList_init_ww73n8$(Kotlin.kotlin.collections.collectionSizeOrDefault_ba2ldo$($receiver_1, 10));
    var tmp$_5;
    tmp$_5 = $receiver_1.iterator();
    while (tmp$_5.hasNext()) {
      var item_1 = tmp$_5.next();
      var tmp$_6 = destination_2.add_11rb$;
      var destination_3 = Kotlin.kotlin.collections.ArrayList_init_ww73n8$(Kotlin.kotlin.collections.collectionSizeOrDefault_ba2ldo$(item_1, 10));
      var tmp$_7;
      tmp$_7 = item_1.iterator();
      while (tmp$_7.hasNext()) {
        var item_2 = tmp$_7.next();
        destination_3.add_11rb$(item_2.value);
      }
      tmp$_6.call(destination_2, new Chord(destination_3));
    }
    return destination_2;
  };
  Scale.$metadata$ = {
    kind: Kotlin.Kind.CLASS,
    simpleName: 'Scale',
    interfaces: [ListNotes]
  };
  var package$Jazz = _.Jazz || (_.Jazz = {});
  package$Jazz.Chord = Chord;
  package$Jazz.Degree = Degree;
  Object.defineProperty(package$Jazz, 'letterNames', {
    get: function () {
      return letterNames;
    }
  });
  Object.defineProperty(package$Jazz, 'octaves', {
    get: function () {
      return octaves;
    }
  });
  Object.defineProperty(package$Jazz, 'accidentals', {
    get: function () {
      return accidentals;
    }
  });
  Object.defineProperty(package$Jazz, 'lowestNote', {
    get: function () {
      return lowestNote;
    }
  });
  Object.defineProperty(package$Jazz, 'HighestNote', {
    get: function () {
      return HighestNote;
    }
  });
  Object.defineProperty(package$Jazz, 'sharpableNotes', {
    get: function () {
      return sharpableNotes;
    }
  });
  Object.defineProperty(package$Jazz, 'pianoNotes', {
    get: function () {
      return pianoNotes;
    }
  });
  package$Jazz.resolve_61zpoe$ = resolve;
  package$Jazz.ionian_hy3q2x$ = ionian;
  package$Jazz.flexScale_gzjmyn$ = flexScale;
  package$Jazz.Key = Key;
  package$Jazz.ListNotes = ListNotes;
  package$Jazz.Note_init_61zpoe$ = Note_init;
  package$Jazz.Note = Note;
  package$Jazz.Scale = Scale;
  var $receiver = flatten(listOf(new CharRange(65, 71)));
  var destination = Kotlin.kotlin.collections.ArrayList_init_ww73n8$(Kotlin.kotlin.collections.collectionSizeOrDefault_ba2ldo$($receiver, 10));
  var tmp$;
  tmp$ = $receiver.iterator();
  while (tmp$.hasNext()) {
    var item = tmp$.next();
    destination.add_11rb$(String.fromCharCode(Kotlin.toBoxedChar(item)));
  }
  letterNames = destination;
  octaves = flatten(listOf(new IntRange(1, 7)));
  accidentals = listOf_0(['#', 'b', 'x', 'bb', '', 'n']);
  lowestNote = 'A0';
  HighestNote = 'C8';
  sharpableNotes = listOf_0(['C', 'D', 'F', 'G', 'A']);
  pianoNotes = listOf_0(['A0', 'A0#', 'B0', 'C1', 'C1#', 'D1', 'D1#', 'E1', 'F1', 'F1#', 'G1', 'G1#', 'A1', 'A1#', 'B1', 'C2', 'C2#', 'D2', 'D2#', 'E2', 'F2', 'F2#', 'G2', 'G2#', 'A2', 'A2#', 'B2', 'C3', 'C3#', 'D3', 'D3#', 'E3', 'F3', 'F3#', 'G3', 'G3#', 'A3', 'A3#', 'B3', 'C4', 'C4#', 'D4', 'D4#', 'E4', 'F4', 'F4#', 'G4', 'G4#', 'A4', 'A4#', 'B4', 'C5', 'C5#', 'D5', 'D5#', 'E5', 'F5', 'F5#', 'G5', 'G5#', 'A5', 'A5#', 'B5', 'C6', 'C6#', 'D6', 'D6#', 'E6', 'F6', 'F6#', 'G6', 'G6#', 'A6', 'A6#', 'B6', 'C7', 'C7#', 'D7', 'D7#', 'E7', 'F7', 'F7#', 'G7', 'G7#', 'A7', 'A7#', 'B7', 'C8']);
  Kotlin.defineModule('output', _);
  return _;
});

//# sourceMappingURL=output.js.map
