let stompClient = null;
let currentAmount = null;

const formatterCurrency = new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency: 'USD',
    minimumFractionDigits: 2,
    maximumFractionDigits: 2,
});

function connect() {
    var socket = new SockJS('/transaction');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/result', function(data) {
            showResultTransaction(JSON.parse(data.body));
        });
    });
}

function disconnect() {
    if(stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendTransaction() {
    const card_number = document.getElementById('card-number').value;
    currentAmount = document.getElementById('withdrawal-amount').value;
    if(!card_number || !currentAmount) {
        document.getElementById('response').innerText = "";
        return;
    }
    let transaction = {
        "action": "withdraw",
        "cardnumber": card_number,
        "amount": currentAmount
    };
    stompClient.send("/app/transaction", {}, JSON.stringify(transaction));
}

function showResultTransaction(result) {
    const response = document.getElementById('response');
    const p = document.createElement('p');
    p.style.wordWrap = 'break-word';

    const results = {
        "00": `Take your money: 💵 $${currentAmount}`,
        "51": "❌ 💲 Insufficient funds",
        "14": "❌ 💳 Invalid card number",
        "96": "❌ An error has occurred. Try again later."
    }

    p.appendChild(document.createTextNode(results[result.code]));

    response.innerText = p.innerHTML;

    document.getElementById('withdrawal-amount').value = "";
}

function getCardInfo() {
    let cardNumber = document.getElementById('card-number-info').value;
    let responseDiv = document.getElementById('response-card-info');
    if(!cardNumber) {
        responseDiv.classList.add("d-none");
        return;
    }
    fetch(`/card/${cardNumber}/info`)
        .then(res => res.json()) // parse response as JSON (can be res.text() for plain response)
        .then(response => {
            if(response && response.status !== 500) {
                responseDiv.classList.remove("d-none");
                document.getElementById('available-amount-value').innerText =
                    formatterCurrency.format(response.availableAmount) ;
                populateTable(response.transactions);
            } else {
                responseDiv.classList.add("d-none");
            }
        });
}

function populateTable(data){
    let tableBody = document.getElementById('table-transactions-body');
    tableBody.innerText =  "";
    data.forEach(function(object) {
        let tr = document.createElement('tr');
        let date = new Date(object.date);
        tr.innerHTML =
            '<td>' + date.toLocaleDateString() + ' ' + date.toLocaleTimeString() + '</td>' +
            '<td>' + formatterCurrency.format(object.amount) + '</td>';
        tableBody.appendChild(tr);
    });
}

function toggleMenuItems(item) {
    let cwDiv = document.getElementById("withdrawal-container");
    let ciDiv = document.getElementById("card-info-container");
    if (item === "CW") {
        cwDiv.style.display = "block";
        ciDiv.style.display = "none";
    } else {
        cwDiv.style.display = "none";
        ciDiv.style.display = "block";
    }
}