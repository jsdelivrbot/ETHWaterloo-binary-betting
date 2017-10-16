App = {
  web3Provider: null,
  contracts: {},

  init: function() {
    return App.initWeb3();
  },

  initWeb3: function() {
    // Is there is an injected web3 instance?
    if (typeof web3 !== 'undefined') {
      App.web3Provider = web3.currentProvider;
      if (window.web3.isConnected()) {
        console.log("Web3 connected");
      }
    } else {
      // If no injected web3 instance is detected, fallback to the TestRPC
      App.web3Provider = new Web3.providers.HttpProvider('http://localhost:8545');
    }
    web3 = new Web3(App.web3Provider);
    return App.initContract();
  },

  initContract: function() {
    $.getJSON('BinaryBets.json', function(data) {
      // Get the necessary contract artifact file and instantiate it with truffle-contract
      var BinaryBetsArtifact = data;
      App.contracts.BinaryBets = TruffleContract(BinaryBetsArtifact);
      // Set the provider for our contract
      App.contracts.BinaryBets.setProvider(App.web3Provider);
      // Use our contract to retrieve and mark the adopted pets
      // return App.markAdopted();
      App.contracts.BinaryBets.deployed().then(function(instance) {
        var events = instance.allEvents();
        // watch for changes
        events.watch(function(error, event){
          if (!error)
            console.log(event);
        });
      })
      return App;
    });
    return App.bindEvents();
    // end of entry point!!!!
  },

  bindEvents: function() {
    // bind the buttons to handler
    $(document).on('click', '#createBetButton', App.handleCreateBet);
    $(document).on('click', '#sendBetButton', App.handleSendBet);
  },

  handleSendBet: function() {
    var binaryBetsInstance;
    var betId = $('#betId').val();
    var betAmount = parseInt($('#betAmount').val());
    web3.eth.getAccounts(function(error, accounts) {
      if (error) {
        console.log(error);
      }
      var account = accounts[0];
      App.contracts.BinaryBets.deployed().then(function(instance) {
        binaryBetsInstance = instance;
        return binaryBetsInstance.sendBet(betId, {from: account, value: web3.toWei(betAmount, 'szabo')});
      }).then(function(result) {
        console.log(result);
      }).catch(function(err) {
        console.log(err.message);
      });
    });
  },

  handleCreateBet: function() {
    // event.preventDefault();
    var binaryBetsInstance;
    var highAddr = $('#highAddr').val();
    var lowAddr = $('#lowAddr').val();
    var expiry = parseInt($('#expiry').val());
    var ante = parseInt($('#ante').val());
    var initialPrice = 0;
    var betid = 0;

    // price api for initial price /////////////////////
    var request = new XMLHttpRequest();
    request.open('GET', 'https://api.coinmarketcap.com/v1/ticker/bitcoin/', false);  // `false` makes the request synchronous
    request.send(null);
    if (request.status === 200) {
      initialPrice = JSON.parse(request.responseText)[0].price_usd * 100;
      console.log("btc: " + initialPrice);
    }
    ////////////////////////////////////////////////////

    web3.eth.getAccounts(function(error, accounts) {
      if (error) {
        console.log(error);
      }
      var account = accounts[0];
      App.contracts.BinaryBets.deployed().then(function(instance) {
        binaryBetsInstance = instance;
        console.log("highAddr: " + highAddr);
        console.log("lowAddr: " + lowAddr);
        console.log("expiry: " + expiry);
        console.log("ante: " + ante);
        console.log("initialPrice: " + initialPrice);
        return binaryBetsInstance.createBet(highAddr, lowAddr, expiry, ante, initialPrice, {from: account});
      }).then(function(result) {
        betid = result.logs[0].args.id.c[0];
        // our server endpoint to register a water or something //////////
        // var request = new XMLHttpRequest();
        // request.open('POST', 'http://localhost:8080/register', false);  // `false` makes the request synchronous
        // var registration = {
        //   betid: betid,
        //   timestamp: expiry
        // }
        // request.send(JSON.stringify(registration));
        // if (request.status === 200) {
        //   console.log(request.responseText);
        // }
        //////////////////////////////////////////////////////////////////
        alert("Please submit your bet of " + ante + " szabo with bet ID " + betid);
      }).catch(function(err) {
        console.log(err.message);
      });
    });


        // // our server endpoint to register a water or something //////////
        // var request = new XMLHttpRequest();
        // request.open('GET', 'http://localhost:8080/register?betid=' + betid + '&timestamp=' + expiry, false);  // `false` makes the request synchronous
        // request.send(null);
        // if (request.status === 200) {
        //   console.log(request.responseText);
        // }
        // //////////////////////////////////////////////////////////////////
  }
};

$(function() {
  $(window).load(function() {
    App.init();
  });
});
