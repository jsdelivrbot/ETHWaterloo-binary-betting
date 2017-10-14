user flow
=========
1. person A wants to bet person B on price action of bitcoin
2. person A obtains person B's wallet address
3. person A calls BinaryBets.createBet(highAddr, lowAddr, expiry, ante, initialPrice)
	-> realistically, we will have to do work here to handle:
		- expiry conversion to a timestamp
		- initialPrice (how to get? should be populated by server api call)
		- AND WE NEED TO GET THE BET ID FROM THE RETURN VALUE
4. <background> at this point, the server needs to have "added" the bet to
	some kind of watchlist. aka waiting for the timestamp to happen so it can poll
	the api and feed the price to the smart contract through settle(id, finalPrice)
5. person A calls betHigh(id), sending the agreed upon value of ether
6. person B calls betLow(id), sending the agreed upon value of ether
7. <background> assume settle calls after 5 and 6
8. the funds have been allocated to the user