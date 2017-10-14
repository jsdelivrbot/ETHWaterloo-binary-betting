user flow
=========
1. person A wants to bet person B on price action of bitcoin
2. person A obtains person B's wallet address
3. person A calls BinaryBets.createBet(highAddr, lowAddr, expiry, ante, initialPrice)
	-> realistically, we will have to do work here to handle:
		- expiry conversion to a timestamp
		- initialPrice (how to get? should be populated by server api call)
		- AND WE NEED TO GET THE BET ID FROM THE RETURN VALUE
	-> this will be like a html button that triggers a web3 call to the contract
		- can populate initialPrice @ this call handler
		- expiry will be handled in the form of an offset specified by the user
			- 1 hour, 1 day, etc
			- and just add it to the current timestamp
		- ante is specified in ether, allow decimals
		- oh, while we make the contract call, also hit some api on the server to
			add the bet to the watchlist
4. <background> at this point, the server needs to have "added" the bet to
	some kind of watchlist. aka waiting for the timestamp to happen so it can poll
	the api and feed the price to the smart contract through settle(id, finalPrice)
5. person A calls betHigh(id), sending the agreed upon value of ether
6. person B calls betLow(id), sending the agreed upon value of ether
7. <background> assume settle calls after 5 and 6
8. the funds have been allocated to the user

server
======
 - serve a page on GET /
 - serve a registerBet on POST /registerbet
 - run a loop/interrupt check on the time
 - on timestamp match/pass, make api call for price then call BinaryBets.settle(id, finalPrice)

client
======
 - need to be able to parse the form/button fields and formulate a call to BinaryBets.createBet()
 - and i guess also submit bet? aka call BinaryBets.betHigh() and BinaryBets.betLow()
 - and finally, expose a form/button for BinaryBets.dissolve()