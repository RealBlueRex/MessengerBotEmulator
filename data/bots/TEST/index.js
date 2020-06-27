const InetSocketAddress = java.net.InetSocketAddress;
const HttpServer = com.sun.net.httpserver.HttpServer;
const HttpContext = com.sun.net.httpserver.HttpContext;
const OutputStream = java.io.OutputStream;
function response(room, msg, sender, isGroupChat, replier, imageDB) {
	var server = HttpServer.create(InetSocketAddress(8080), 0);
	var context = server.createContext("/");
	context.setHandler(function(a){
		var response = "Hello World!";
		exchange.sendResponseHeaders(200, response.getBytes().length);
		var os = exchange.getResponseBody();
		os.write(response.getBytes());
		os.close();
	});
	server.start();
}
