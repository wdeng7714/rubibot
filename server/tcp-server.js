var port = 8080;

var net = require("net");

var server = net.createServer();

server.on('connection', handleConnection);

server.listen(port, function() {
	console.log('server listening to %j', server.address());
});

function handleConnection(con) {
	var clientAddress = con.remoteAddress + ':' + con.remotePort;
	console.log("client connection from %s", clientAddress);
	
	con.on('data', onConnectionData);
	con.once('close', onConnectionClose);
	con.on('error', onConnectionError);

	function onConnectionData(data) {
		console.log('connection data from %s: %j', clientAddress, data);
		con.write(data);
	}

	function onConnectionClose() {
		console.log('connection from %s closed', clientAddress);
	}

	function onConnectionError(err) {
		console.log('connection %s error: %s', clientAddress, err.message);
	} 

}