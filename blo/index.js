var express = require('express')
var Web3 = require('Web3')
var app = express()
var contractAddress = '0x823ca3c843cc8dc479dc03cf3eb1c3afa3dc9992'
var contractOwner = '0x4f7874a72c06b9bb9775e5ee6b2932c32e8e47fd'

var web3Provider = new Web3.providers.HttpProvider('http://localhost:8545');
var web3 = new Web3(web3Provider);

app.set('port', (process.env.PORT || 5000))
app.use(express.static(__dirname + '/public'))

app.get('/:betid-:price', function(request, response) {
  web3.eth.getAccounts(function(error, accounts) {
    if (error) {
      console.log(error);
    }
    var account = accounts[0];
    console.log("using account " + account + " to settle id " + request.params.betid + " with price " + request.params.price);
    // App.contracts.BinaryBets.deployed().then(function(instance) {
    //   binaryBetsInstance = instance;
    //   return binaryBetsInstance.settle(betid, price * 100, {from: account});
    // }).then(function(result) {
    //   console.log(result);
    // }).catch(function(err) {
    //   console.log(err.message);
    // });
    var betid = request.params.betid;
    var price = request.params.price;
    var abi = [
	    {
	      "constant": true,
	      "inputs": [],
	      "name": "settleOffsetTime",
	      "outputs": [
	        {
	          "name": "",
	          "type": "uint256"
	        }
	      ],
	      "payable": false,
	      "type": "function"
	    },
	    {
	      "constant": false,
	      "inputs": [
	        {
	          "name": "_highBettor",
	          "type": "address"
	        },
	        {
	          "name": "_lowBettor",
	          "type": "address"
	        },
	        {
	          "name": "_expiry",
	          "type": "uint256"
	        },
	        {
	          "name": "_szabo",
	          "type": "uint256"
	        },
	        {
	          "name": "_initialPrice",
	          "type": "uint256"
	        }
	      ],
	      "name": "createBet",
	      "outputs": [
	        {
	          "name": "",
	          "type": "uint256"
	        }
	      ],
	      "payable": false,
	      "type": "function"
	    },
	    {
	      "constant": false,
	      "inputs": [
	        {
	          "name": "id",
	          "type": "uint256"
	        },
	        {
	          "name": "finalPrice",
	          "type": "uint256"
	        }
	      ],
	      "name": "settle",
	      "outputs": [],
	      "payable": false,
	      "type": "function"
	    },
	    {
	      "constant": false,
	      "inputs": [
	        {
	          "name": "id",
	          "type": "uint256"
	        }
	      ],
	      "name": "sendBet",
	      "outputs": [],
	      "payable": true,
	      "type": "function"
	    },
	    {
	      "constant": false,
	      "inputs": [
	        {
	          "name": "id",
	          "type": "uint256"
	        }
	      ],
	      "name": "dissolve",
	      "outputs": [],
	      "payable": false,
	      "type": "function"
	    },
	    {
	      "anonymous": false,
	      "inputs": [
	        {
	          "indexed": false,
	          "name": "id",
	          "type": "uint256"
	        },
	        {
	          "indexed": false,
	          "name": "sender",
	          "type": "address"
	        }
	      ],
	      "name": "BetCreated",
	      "type": "event"
	    },
	    {
	      "anonymous": false,
	      "inputs": [],
	      "name": "Bad",
	      "type": "event"
	    },
	    {
	      "anonymous": false,
	      "inputs": [
	        {
	          "indexed": false,
	          "name": "yessss",
	          "type": "uint256"
	        }
	      ],
	      "name": "Change",
	      "type": "event"
	    }
	  ];
    var contract = new web3.eth.Contract(abi, contractAddress);
    return contract.methods.settle(betid, price * 100).send({from: contractOwner}, function(error, txhash) {
    	console.log(error);
    	console.log(txhash);
    });
  }).then(function(result) {
    // console.log(result);
  }).catch(function(err) {
    // console.log(err.message);
  });
  response.send(request.params);
})

app.listen(app.get('port'), function() {
  console.log("Node app is running at localhost:" + app.get('port'))
})
