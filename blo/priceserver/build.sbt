name := "m1"

version := "0.1"

scalaVersion := "2.12.1"

cancelable in Global := true

libraryDependencies ++= Seq(
	"com.typesafe.akka"			%%	"akka-http" 			%		"10.0.10",
	"com.typesafe.akka"			%%	"akka-actor"			%		"2.5.4",
	"com.typesafe.akka"			%%	"akka-stream"			%		"2.5.4",
	"com.typesafe.play"			%%	"play-json"				%		"2.6.3",
	"com.github.scopt"			%%	"scopt" 				%		"3.7.0",
	"com.github.tototoshi"		%%	"scala-csv"				%		"1.3.5",
	"com.google.code.gson"		%	"gson"					%		"2.8.2",
	"com.typesafe.akka"			%%	"akka-http-spray-json"	%		"10.0.10",
	"ch.megard"					%%	"akka-http-cors"		%		"0.2.2",
	"org.web3j"					%	"core"					%		"2.3.1"
)
