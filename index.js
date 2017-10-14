const express = require('express')
const app = express()
const port = 3000
const rp = require('request-promise')
const exphbs = require('express-handlebars')
const path = require('path')
const request = require('request')
const Web3 = require('Web3')

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

var asset_name = ""
var asset_previous_date = "Oct 7, 2017 18:27:00 UTC" // TODO: Replace fake data
var asset_previous_price = 5500 // TOOD: Replace fake data
var asset_updated_date = ""
var asset_updated_price = 0 
var winner = ""

//TODO: Build the loop that will check for duration of time

// calls the coindesk API 
request('https://api.coindesk.com/v1/bpi/currentprice.json', function (error, response, body) {
	if (!error && response.statusCode == 200) {
		coindesk_current = JSON.parse(body)
		asset_name = coindesk_current.chartName
		asset_updated_date = coindesk_current.time.updated
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

// generate the private key from a passphrase 
var web3 = new Web3()
var privateKeyRaw = web3.sha3("Have you ever seen too much as to go to there")
console.log(privateKeyRaw)