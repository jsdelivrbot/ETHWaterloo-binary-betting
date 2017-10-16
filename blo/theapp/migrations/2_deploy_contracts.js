var Adoption = artifacts.require("./Adoption.sol");
var BinaryBets = artifacts.require("./BinaryBets.sol");

module.exports = function(deployer) {
	deployer.deploy(Adoption);
	deployer.deploy(BinaryBets);
};