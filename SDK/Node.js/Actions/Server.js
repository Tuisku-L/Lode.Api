const request = require("./../Utils/Request");
const common = require("./../Utils/Common");

class Server {
    getServerInfo(){
        return request("/api/Server/GetServerInfo");
    }

    executeCommand(command){
        const data = {
            command
        };

        return request(`/api/Server/ExecuteCommand`, common.createJsonData(data));
    }

    setMaxPlayer(maxPlayer){
        const data = {
            maxPlayer
        };

        return request(`/api/Server/SetMaxPlayer`, common.createJsonData(data));
    }

    setAutoSave(isAutoSave){
        const data = {
            isAutoSave
        };

        return request(`/api/Server/SetAutoSave`, common.createJsonData(data));
    }

    sendBroadcastMessage(message){
        const data = {
            message
        };

        return request(`/api/Server/SendBroadcastMessage`, common.createJsonData(data));
    }

    setServerProps(key, value){
        const data = {
            key,
            value
        };

        return request(`/api/Server/SetServerProps`, common.createJsonData(data));
    }
}

module.exports = Server;