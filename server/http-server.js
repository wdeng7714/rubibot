const http = require('http');
const port = 3000;


const server = http.createServer(requestHandler)

server.listen(port, function() {
	console.log("server listening to %j", server.address());
});

function requestHandler(request, response) {
	response.writeHead(200, {'Content-Type': 'text/html'});
	response.write('Hello World!');
	response.end();
}