const crypto = require("crypto");

function CreateAuthToken(userName, password, apiUrl) {
    return crypto.createHash('SHA256').update(userName + apiUrl.toLowerCase() + password).digest('hex');;
}

function CreateJsonData(data){
    const jsonData = {
        TimeStamp: new Date().getTime(),
        Data: data
    };

    return JSON.stringify(jsonData);
}

module.exports.createAuthToken = CreateAuthToken;
module.exports.createJsonData = CreateJsonData;