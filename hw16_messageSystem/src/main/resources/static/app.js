// const SockJS = require("./sockjs-0.3.4");
let stompClient = null;

function connect() {
    var socket = new SockJS('/add');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        // setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', (greeting) => showGreeting(JSON.parse(greeting.body).id));
    });
}

// var socket = new WebSocket('ws://localhost:8080/add/websocket');

connect();

// const connect = () => {
//   stompClient = Stomp.over(new SockJS('/gs-guide-websocket'));
//   stompClient.connect({}, (frame) => {
//     setConnected(true);
//     console.log('Connected: ' + frame);
//     stompClient.subscribe('/topic/response.' + $("#roomId").val(), (greeting) => showGreeting(JSON.parse(greeting.body).messageStr));
//   });
// }

const disconnect = () => {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

const showGreeting = (id) => {
    let tr = document.createElement("tr")
    tr.innerHTML = "<td>" + id + "</td>"
    document
        .getElementById("content")
        .appendChild(tr)
}

function formSubmit() {
    var form = document.getElementById("addDeviceForm");
    var id = form.elements["device_id"].value;


    addDevice(id);
}

function addDevice(id) {
    var DeviceAction = {
        action: "add",
        id: id,
    };
    stompClient.send("/app/add", {}, JSON.stringify(DeviceAction));
}





