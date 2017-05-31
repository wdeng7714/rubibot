const port = 3000;

var express = require('express');
var app = express();

app.get('/', function(req, res) {
	// res.status(404).send("Not Found");
	res.status(200).send("Hello World!");
})

var server = app.listen(port, function() {
	console.log("listening to %j", server.address());
});