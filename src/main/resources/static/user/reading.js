

function http_post(theUrl, inputData) {
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", theUrl, false);
    xmlHttp.setRequestHeader("Content-Type", "application/json");
    xmlHttp.send(inputData);
    return [xmlHttp.response, xmlHttp.status];
}


function like_tap(element, postId) {
    let color = element.style.backgroundColor;

    let body = {
        "id": postId
    }
    let json = JSON.stringify(body);
    let responce = http_post("like", json);
    let like_count_elem = document.getElementById("like_count");
    if (responce[1] != 200) {
        alert(responce[0]);
    } else {
        if (color == 'blue') {
            element.style.backgroundColor = 'red';
            like_count_elem.innerText = '' + (Number(like_count_elem.innerText) + 1);
        } else if (color == 'red') {
            element.style.backgroundColor = 'blue';
            like_count_elem.innerText = '' + (Number(like_count_elem.innerText) - 1);
        }
    }
}





function go_to_creator_page(creatorId) {
    window.location.href = "check_creator?userId=" + creatorId;
}