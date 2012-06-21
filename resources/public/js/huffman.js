function delprev(id) {
	var prev = document.getElementById(id);
	prev.innerHTML = "";
}

function showEncode() {
	var encodeInput = document.getElementById('inputstring');
	var encodeSubmit = document.getElementById("encodeSubmit");
	
	
	encodeInput.style.visibility = "visible";
	encodeSubmit.style.visibility = "visible";

	delprev('res');
}

function hideEncode() {
	var encodeInput = document.getElementById('inputstring');
	var encodeSubmit = document.getElementById("encodeSubmit");
	
	
	encodeInput.style.visibility = "hidden";
	encodeSubmit.style.visibility = "hidden";	
}

function addField() {
	var p = document.getElementById('freqTable');

	var n_ch = document.createElement('input');
	n_ch.setAttribute("id", "character");
	n_ch.setAttribute("class", "ch");
	n_ch.setAttribute("type", "text");
	n_ch.setAttribute("placeholder", "Character");
	n_ch.setAttribute("name", "character");
	n_ch.style.visibility = "visible";

	var n_f = document.createElement('input');
	n_f.setAttribute("id", "frequency");
	n_f.setAttribute("class", "freq");
	n_f.setAttribute("type", "text");
	n_f.setAttribute("placeholder", "Frequency");
	n_f.setAttribute("name", "frequency");
	n_f.style.visibility = "visible";

	var nl = document.createElement('br');

	p.appendChild(n_ch);
	p.appendChild(n_f);
	p.appendChild(nl);
}

function showDecode() {
	
	addField();

	var decodeInput = document.getElementById('bitstringinput');
	var charInput = document.getElementById('character');
	var freqInput = document.getElementById('frequency');
	var addFieldButt = document.getElementById('addField');
	var decodeSubmit = document.getElementById('decodeSubmit');

	decodeInput.style.visibility = "visible";
	charInput.style.visibility = "visible";
	freqInput.style.visibility = "visible";
	addFieldButt.style.visibility = "visible";
	decodeSubmit.style.visibility = "visible";

	delprev('resD');
}

function hideDecode() {

	var decodeInput = document.getElementById('bitstringinput');
	var addFieldButt = document.getElementById('addField');
	var decodeSubmit = document.getElementById('decodeSubmit');

	decodeInput.style.visibility = "hidden";
	addFieldButt.style.visibility = "hidden";
	decodeSubmit.style.visibility = "hidden";

	delprev('freqTable');
}


/*
 * Sends string to encode.
 */

function sendString() {

	var http = new XMLHttpRequest();
	var url = "/encode"
	var bitstring = document.getElementById("inputstring").value

	if(bitstring == "") {
		alert("Please give input to encode!");
		return;
	}

	var params = "inputstring="+bitstring;

	http.open("POST", url, true);

	http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	http.setRequestHeader("Content-length", params.length);
	http.setRequestHeader("Connection", "close");

	http.onreadystatechange = function() {
		if(http.readyState == 4 && http.status == 200) {
			var n = document.getElementById('res');
			n.innerHTML = http.responseText;
		}
	}

	http.send(params);
}

/*
 * Makes frequency table from character-frequency input
 * to be passed for decoding.
 */

function makeTable() {
	var chs = document.getElementsByClassName('ch');
	var freqs = document.getElementsByClassName('freq');

	var res = {};

	if(chs.length != freqs.length) {
		alert("Number of characters and frequencies don't match.");
		return;
	}

	for(var i = 0; i < chs.length; i++) res[""+chs[i].value] = ""+freqs[i].value;

	return res;
}

/*
 * Sends data to be decoded
 */

function sendDecode() {
	var http = new XMLHttpRequest();
	var url = "/decode"
	var decodestring = document.getElementById("bitstringinput").value
	var freqTable = makeTable();

	console.log(freqTable);

	if(decodestring == "") {
		alert("Please give input to encode!");
		return;
	}

	if(freqTable == undefined) {
		alert("Invalid Frequency Table");
		return;
	}

	var params = "bitstring="+decodestring+"&table="+JSON.stringify(freqTable);

	http.open("POST", url, true);

	http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	http.setRequestHeader("Content-length", params.length);
	http.setRequestHeader("Connection", "close");

	http.onreadystatechange = function() {
		if(http.readyState == 4 && http.status == 200) {
			hideDecode();
			var n = document.getElementById('resD');
			n.innerHTML = http.responseText;
		}
	}

	http.send(params);

}
