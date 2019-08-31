const JsonApi = require("./JsonApi");

const api = new JsonApi({
    baseUrl: "http://127.0.0.1:19133",
    userName: "root",
    passWord: "password"
});

const server = api.server;

server.getServerInfo()
      .then(res => console.info(res));