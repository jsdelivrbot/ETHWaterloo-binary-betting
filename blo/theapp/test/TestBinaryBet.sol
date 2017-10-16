pragma solidity ^0.4.11;

import "truffle/Assert.sol";
import "truffle/DeployedAddresses.sol";
import "../contracts/BinaryBet.sol";

contract TestBinaryBet {
	BinaryBet bb = BinaryBet(DeployedAddresses.BinaryBet());

	function testBetHigh() {
		address expected = this;
		address high = bb.betHigh();
		Assert.equal(high, expected, "high is good");
	}

	function testBetLow() {
		address expected = this;
		address low = bb.betLow();
		Assert.equal(low, expected, "low is good");
	}
}