// const SockJS = require("./sockjs-0.3.4");
let stompClient = null;

function connect() {
    var socket = new SockJS('/add');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        // setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', (greeting) => showGreeting(JSON.parse(greeting.body).name));
    });
}

// var socket = new WebSocket('ws://localhost:8080/add/websocket');

connect();



const disconnect = () => {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

const showGreeting = (name) => {
    let tr = document.createElement("tr")
    tr.innerHTML = "<td>" + name + "</td>"
    document
        .getElementById("content")
        .appendChild(tr)
}

function formSubmit() {
    var form = document.getElementById("addDeviceForm");
    var id = form.elements["device_id"].value;


    addDevice(id);
}

function addDevice(name) {
    var DeviceAction = {
        action: "add",
        name: name,
    };
    stompClient.send("/app/add", {}, JSON.stringify(DeviceAction));
}





