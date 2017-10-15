const express = require('express')
const app = express()
const port = 3000
const rp = require('request-promise')
const exphbs = require('express-handlebars')
const path = require('path')
const request = require('request')
const Web3 = require('Web3')
const Wallet = require("ethers-wallet")
const solc = require('solc')
const fs = require('fs')
var TestRPC = require("ethereumjs-testrpc"); 

// set up handlebars so variables are rendered onto the homepage 
app.engine('.hbs', exphbs({
  defaultLayout: 'main',
  extname: '.hbs',
  layoutsDir: path.join(__dirname, 'views/layouts')
}))
app.set('view engine', '.hbs')
app.set('views', path.join(__dirname, 'views'))

// set up the port on localhost:3000
app.listen(port, (err) => {
  if (err) {
    return console.log('something bad happened', err)
  }
  console.log(`server is listening on ${port}`)
})

// make a local instance of the Blockchain
var web3 = new Web3(new Web3.providers.HttpProvider("http://localhost:8545"))

web3.setProvider(TestRPC.provider()) 


// read the smart contract 
code = fs.readFileSync('sol/BinaryBets.sol').toString()

// compile the smart contract 
var compiledCode = solc.compile(code)
var abiDefinition = JSON.parse(compiledCode.contracts[':BinaryBets'].interface)
var BinaryBetsContract = web3.eth.contract(abiDefinition)
var byteCode = compiledCode.contracts[':BinaryBets'].byteCode
var account = web3.eth.getAccounts(function(error, result){ 
	console.log(result)
	return result
})
console.log("asdfasfasdf " + account)
// var deployedContract = BinaryBetsContract.new({data: byteCode, from: account, gas: 470000})
// console.log(deployedContract.address)
// var contractInstance = BinaryBetsContract.at(deployedContract.address)



// TODO: These addresses are supposed to be obtained from the user from a form
var player_1_address = "0xca35b7d915458ef540ade6068dfe2f44e8fa733c" // exmaple address 
var player_2_address = "0x14723a09acff6d2a60dcdf7aa4aff308fddc160c" // example address 
var final_price = 5000
// TODO: Verifty that the addresses exist

var asset_name = ""
var asset_previous_date = "2017-10-14" // TODO: Replace fake data
var asset_previous_price = 5500 // TOOD: Replace fake data
var asset_updated_date = ""
var asset_updated_price = 0 
var winner = ""

//TODO: Build the loop that will check for duration of time
// find the address at starting of bet via JSON call 

function getPriceBefore() {
	request('https://api.coindesk.com/v1/bpi/historical/close.json?start=' + asset_previous_date + '&end=' + asset_previous_date, function(error, response, body){
		if (!error && response.statusCode == 200) {
			coindesk_past = JSON.parse(body)
			asset_previous_price = coindesk_past.bpi["2017-10-14"]
		} else {
			console.log("Status Code: " + response.statusCode)
		}
	})

}

// calls the coindesk API 
function getPriceAfter() {
	request('https://api.coindesk.com/v1/bpi/currentprice.json', function (error, response, body) {
		if (!error && response.statusCode == 200) {
			coindesk_current = JSON.parse(body)
			asset_name = coindesk_current.chartName
			asset_updated_date = coindesk_current.time.updatedISO
			var string_asset_updated_price = coindesk_current.bpi.USD.rate
			var int_updated_price = string_asset_updated_price.replace(/[^\d\.\-]/g, "")
			asset_updated_price = parseFloat(int_updated_price)
			if (int_updated_price > asset_previous_price) {
				winner = "User who bet on rise is the winner"
			} else {
				winner = "User who bet on drop is the winner"
			}
		} else { 
			console.log("Status Code: " + response.statusCode)
		}
	})
}


// renders response to page
app.get('/', (request, response) => {
  response.render('home', {
    name: asset_name,
    asset_past_date: asset_previous_date,
    asset_past_price: asset_previous_price,
    asset_current_date: asset_updated_date,
    asset_current_price: asset_updated_price, 
    bet_winner: winner
  })
})

// var options = {
// 	url: 'http://localhost:3000/'
// 	method: "POST", 
// 	json: true, 
// 	body: { "registerBet": 20 }
// 	}
// }

// Start the request 
// request(options, function(error, response, body) {
// 	if (!error && response.statusCode == 200) {
// 		console.log(body)
// 	}
// })

// // generate the private key from a passphrase 
// var web3 = new Web3()
// var privateKeyRaw = web3.sha3("Have you ever seen too much as to go to there")
// console.log(privateKeyRaw)

// // get a matching Ethereum Adderss for private key 
// var wallet = new Wallet(privateKeyRaw)
// console.log(wallet.address)


