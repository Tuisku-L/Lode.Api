const Server = require("./Actions/Server");

class JsonApi{
    constructor({
        baseUrl,
        userName,
        passWord
    }){
        global.userName = userName;
        global.password = passWord;
        global.baseUrl = baseUrl;

        this.server = new Server();
    }
}

module.exports = JsonApi;