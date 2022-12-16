function ban_post(post_id) {
    if (!confirm("В результате пост будет удалён, вы уверены?")) {
        return;
    }
    let response = http_post("delete_post/" + post_id);
    if (response[1] == 200) {
        window.location.href = "/";
    } else {
        alert(response[0]);
    }
}


function justify_post(post_id) {
    let response = http_post("justify_post/" + post_id);
    if (response[1] == 200) {
        window.location.href = "/";
    } else {
        alert(response[0]);
    }
}


function double_ban_post(post_id) {
    if (!confirm("В результате пост будет удалён, а пользователь - заблокирован. Уверены в выборе?")) {
        return;
    }
    let response = http_post("block_user/" + post_id);
    if (response[1] == 200) {

        let response = http_post("delete_post/" + post_id);
        if (response[1] == 200) {
            window.location.href = "/";
        } else {
            alert(response[0]);
        }

        window.location.href = "/";
    } else {
        alert(response[0]);
    }
}


function http_post(theUrl) {
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", theUrl, false);
    xmlHttp.setRequestHeader("Content-Type", "application/json");
    xmlHttp.send();
    return [xmlHttp.response, xmlHttp.status];
}