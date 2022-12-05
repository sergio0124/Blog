function subscribe(element, creatorId){
    let button_text = element.textContent;
    let body = {
        "id": creatorId
    }
    let json = JSON.stringify(body);
    let responce = http_post("subscribe", json);
    if (responce[1] != 200) {
        alert(responce[0]);
    } else {
        if (button_text.includes('Отписаться от пользователя')) {
            element.innerText = 'Подписаться на пользователя';
        } else {
            element.innerText = 'Отписаться от пользователя';
        }
    }
}


function http_post(theUrl, inputData) {
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", theUrl, false);
    xmlHttp.setRequestHeader("Content-Type", "application/json");
    xmlHttp.send(inputData);
    return [xmlHttp.response, xmlHttp.status];
}