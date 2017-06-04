const port = 8080;

var express = require('express');
var http = require('http')
var io = require('socket.io');

var app = express();

app.get('/', function(req, res) {
	// res.status(404).send("Not Found");
	res.status(200).send("Hello World!");
})

var server = app.listen(port, function() {
	console.log("listening to %j", server.address());
});