pragma solidity ^0.4.4;

contract BinaryBets {
	uint public settleOffsetTime = 3600;
	address server;
	Bet[] bets;
	event receivedBet(address from, uint value);
	
	struct Bettor {
        address addr;
        uint betAmount;             // is in WEI
	}

	struct Bet {
	    Bettor highBettor;
	    Bettor lowBettor;
		uint expiry;
		uint ante;
		uint initialPrice;
	}

	modifier onlyOwner() {
		if (msg.sender != server) {
			revert();
		}
		_;
	}
	
	modifier validBetId(uint id) {
	    if (id >= bets.length) {
	        revert();
	    }
	    _;
	}
	
	modifier validAnte(uint id) {
	    if (msg.value != bets[id].ante) {
	        revert();
	    }
	    _;
	}

	modifier eitherBettor(uint id) {
		if (msg.sender != bets[id].highBettor.addr && msg.sender != bets[id].lowBettor.addr) {
			revert();
		}
		_;
	}

    function BinaryBets() public {
        server = msg.sender;
    }
    
    function createBet(address _highBettor, address _lowBettor, uint _expiry, uint _ante, uint _initialPrice) public returns (uint) {
        // returns id of the bet
        bets.push(Bet(Bettor(_highBettor, 0), Bettor(_lowBettor, 0), _expiry, _ante, _initialPrice));
        return bets.length - 1;
    }

    function betHigh(uint id) public payable validBetId(id) validAnte(id) eitherBettor(id) {
        if (bets[id].highBettor.betAmount != 0) {
            revert();
        }
        bets[id].highBettor.betAmount = msg.value;
	}
    
    function betLow(uint id) public payable validBetId(id) validAnte(id) eitherBettor(id) {
        if (bets[id].lowBettor.betAmount != 0) {
            revert();
        }
        bets[id].lowBettor.betAmount = msg.value;
    }

    function settle(uint id, uint finalPrice) public validBetId(id) onlyOwner {
        if (now < bets[id].expiry) {
            // not time yet dude
            revert();
        }
        Bet memory bet = bets[id];
        if (finalPrice > bet.initialPrice) {
            bet.highBettor.addr.send(2 * bet.ante);
        } else if (finalPrice < bet.initialPrice) {
            bet.lowBettor.addr.send(2 * bet.ante);
        } else {
            bet.highBettor.addr.send(bet.ante);
            bet.lowBettor.addr.send(bet.ante);
        }
    }

    function dissolve(uint id) public validBetId(id) eitherBettor(id) {
        // check address
		Bet memory bet = bets[id];
		if (now > (bet.expiry + settleOffsetTime)) {
		    bet.highBettor.addr.send(bet.ante);
		    bet.lowBettor.addr.send(bet.ante);
		}
	}
}