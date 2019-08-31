const Request = require('request-promise');
const common = require("./Common");

function HttpPost(url, data = "", headers = {}) {
    return new Promise(function (resolve) {
        const { userName, password, baseUrl } = global;
        Request({
            url: baseUrl + url + (data === "" ? "" : `?Data=${data}`),
            method: "POST",
            headers: {
                "X-JsonApi-Authentication": common.createAuthToken(userName, password, url),
                ...headers
            },
            json: true
        }).then((res) => {
            if (res.code >= 200 && res.code < 300) {
                return resolve(res.returnObject);
            } else {
                throw res;
            }
        }).catch((err) => {
            throw err;
        })
    })
}

module.exports = HttpPost;