pragma solidity ^0.4.4;

contract BinaryBets {
	uint public settleOffsetTime = 3600;
	address server;
	Bet[] bets;
	event BetCreated(uint id, address sender);
	event Bad();
	event Change(uint yessss);
	
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
    
    function createBet(address _highBettor, address _lowBettor, uint _expiry, uint _szabo, uint _initialPrice) public returns (uint) {
        // returns id of the bet
        bets.push(Bet(Bettor(_highBettor, 0), Bettor(_lowBettor, 0), _expiry, _szabo * 1000000000000, _initialPrice));
       	BetCreated(bets.length - 1, msg.sender);
        return bets.length - 1;
    }

    function sendBet(uint id) public payable validBetId(id) validAnte(id) eitherBettor(id) {
    	if (msg.sender == bets[id].highBettor.addr) {
    		if (bets[id].highBettor.betAmount > 0) {
    			Bad();
    			revert();
    		}
    		bets[id].highBettor.betAmount += msg.value;
		} else {
			if (bets[id].lowBettor.betAmount > 0) {
				Bad();
				revert();
			}
			bets[id].lowBettor.betAmount += msg.value;
		}
    }

    function settle(uint id, uint finalPrice) public validBetId(id) onlyOwner {
        if (now < bets[id].expiry || bets[id].highBettor.betAmount != bets[id].ante || bets[id].lowBettor.betAmount != bets[id].ante) {
            // not time yet dude OR someone did not send a bet, thus no arbitration
            revert();
        }
        if (finalPrice > bets[id].initialPrice) {
            bets[id].highBettor.addr.send(2 * bets[id].ante);
        } else if (finalPrice < bets[id].initialPrice) {
            bets[id].lowBettor.addr.send(2 * bets[id].ante);
        } else {
            bets[id].highBettor.addr.send(bets[id].ante);
            bets[id].lowBettor.addr.send(bets[id].ante);
        }
    }

    function dissolve(uint id) public validBetId(id) eitherBettor(id) {
        // check address
		if (now > (bets[id].expiry + settleOffsetTime)) {
		    bets[id].highBettor.addr.send(bets[id].ante);
		    bets[id].lowBettor.addr.send(bets[id].ante);
		}
	}
}