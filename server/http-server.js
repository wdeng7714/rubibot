const http = require('http');
const port = 8080;


const server = http.createServer(requestHandler)

server.listen(port, function() {
	console.log("server listening to %j", server.address());
});

function requestHandler(request, response) {
	// 200 success
	response.writeHead(200, {'Content-Type': 'text/html'});
	response.write('Hello World!');
	response.end();
	
	// 404 not found
	// response.writeHead(404, {"Content-Type": "text/plain"});
	// response.write("404 Not Found\n");
	// response.end();
}