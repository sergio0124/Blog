function delete_post(id, element){
    let data = {
        "id": id
    };
    let json = JSON.stringify(data);
    let rsp = http_post("delete_post", json);
    let code = rsp[1];
    let message = rsp[0];

    if(code==200){
        let parent = element.parentNode;
        parent.parentNode.removeChild(parent);
    }
    else {
        alert(message);
    }
}


function http_post(theUrl, inputData) {
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", theUrl, false);
    xmlHttp.setRequestHeader("Content-Type", "application/json");
    xmlHttp.send(inputData);
    return [xmlHttp.response, xmlHttp.status];
}