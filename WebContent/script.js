"use strict";
var myApp = angular.module('TicTacToe', []);
myApp
		.controller(
				'TicTacToeCtrl',
				function($http, $location, $timeout) {

					var self = this;
					self.fields = [ "1", "2", "3", "4", "5", "6", "7", "8", "9" ];
					self.activePlayer = true;
					self.anzFeld = self.fields.length;
					self.zug;
					self.playerOne = "x";
					self.playerTwo = "o";
					self.aP = self.playerOne;
					self.pcMove;
					self.loading = 2;
					self.pcBlock = "o";
					self.temp;
					self.timeout;
					self.warte;
					self.wow;
					self.combo = [ [ 1, 2, 3 ], [ 4, 5, 6 ], [ 7, 8, 9 ],
							[ 1, 4, 7 ], [ 2, 5, 8 ], [ 3, 6, 9 ], [ 1, 5, 9 ],
							[ 3, 5, 7 ] ];

					self.classes = {
						def : [ "fieldDefault" ]
					};

					self.getClass = function(index) {
						var ret = [];
						if (self.classes[index] != null) {
							ret = self.classes[index];
						} else {
							ret = self.classes["def"];
						}
						return ret;
					};

					self.playerConnection = function() {

						self.temp = $http
								.get(
										'/JaxRSEducation/service/rest/playerConnection/')
								.then(function successCallback(response) {
									// self.loading = response.data.test;
									console.log(self.loading + " Loading");
									// self.playerCheck();
								});
					};
					
					
					self.getZuege = function(){
						self.wow = $http.get('/JaxRSEducation/service/rest/getSpielzug/');
						console.log(self.wow + " hier ist Stupid String")
					}

					// self.playerCheck = function() {
					// console.log(" Bin in playerCheck");
					// $timeout(function() {
					// console.log(" Bin in timeout");
					// $http.get('/JaxRSEducation/service/rest/playerCheck/')
					// .then(function successCallback(response) {
					// self.warte = response.data.test;
					// console.log(self.warte + "Player checkk");
					// self.wau();
					// });
					// }, 3000);
					//						
					//
					// };

					self.wau = function() {
						if (self.warte == self.loading) {
							$timeout(function() {
								console.log("Waiting for 3 sek");
								self.playerCheck();
							}, 3000);
						} else {
							self.loading = self.warte;
						}
					}

					self.createZug = function() {
						$http.post('/JaxRSEducation/service/rest/Spielzug/',
								"{'FC':'" + self.zug + "'}"); // sending the
						self.startTimer(10);
						// PlayerChoice
					};

					self.ActivePlayer = function() {
						var AP;
						if (activePlayer) {
							AP = 1;
						} else {
							AP = 2;
						}
						$http.post(
								'/JaxRSEducation/service/rest/ActivePlayer/',
								"{'AP':'" + AP + "'}"); // sending the
						// PlayerChoice
					};

					self.select = function(index) {
						if (self.fields[index] !== "x"
								&& self.fields[index] !== "o") {
							self.makeMove(self.activePlayer, index);
						}
					};

					self.makeMove = function(activePlayer, index) {
						if (activePlayer) {
							self.zug = self.fields[index];
							self.classes[index] = [ "pone" ];
							self.fields[index] = self.playerOne;

						} else {
							self.zug = self.fields[index];
							self.classes[index] = [ "ptwo" ];
							self.fields[index] = self.playerTwo;

						}

						console.log(self.zug);
						self.createZug();
						self.check();
					};

					self.winCheck = function() {
						var gewinn = 0;
						if (self.activePlayer) {
							self.aP = self.playerOne;
						} else {
							self.aP = self.playerTwo;
						}
						for (var i = 0; i < self.combo.length && gewinn <= 3; i++) {
							gewinn = 0;
							for (var j = 0; j < self.combo[i].length; j++) {
								if (self.fields[self.combo[i][j] - 1] == self.aP) {
									gewinn++;
									if (gewinn == 3) {
										return true;
									}
									if (self.activePlayer && gewinn == 2) {
										for (var k = 0; k < 3; k++) {
											if (self.fields[self.combo[i][k] - 1] !== "x"
													&& self.fields[self.combo[i][k] - 1] !== "o") {

												self.pcBlock = self.fields[self.combo[i][k] - 1];
												return false;
											}
										}
									}
								}
							}
						}
						return false;
					};

					self.check = function() {
						if (self.winCheck()) {
							self.endGame(self.playerName(self.aP));
						} else if (self.anzFeld == 1 && !self.winCheck()) {
							self
									.endGame("The round was draw! Want to play again?");
						} else {
							self.playerChange();
						}
					};

					self.random = function() {
						while (true) {
							var a = Math.floor((Math.random() * 8) + 1);
							if (self.fields[a] == "x" || self.fields[a] == "o") {
								a = Math.floor((Math.random() * 8) + 1);
							} else {
								return a;
							}
						}
					};

					self.restart = function() {
						window.location.reload();
					};

					self.playerChange = function() {
						self.anzFeld--;
						self.activePlayer = !self.activePlayer;
					};

					self.endGame = function(txt) {
						setTimeout(function() {
							alert(txt);
							self.restart();
						}, 1);
					};

					self.playerName = function(player) {
						if (player == "x") {
							return "You win!";
						} else {
							return "You lost";
						}
					};


					self.timer;
					self.result = document.getElementById('result');
							var n = +result.innerHTML;
					self.startTimer = function(n) {
						clearInterval(self.timer);
						self.timeout = n - 1; // fix 1 sec start delay
						document.getElementById('pm').style.display = 'none'; 																										self.timer = setInterval(function() {
							self.result.innerHTML = self.timeout--;

							if (self.timeout < 20) {
								self.result.style.color = '#ED3E42';
							} // hurry up!

							if (self.timeout == -1) {
								alert("Time is out. You lost");
								clearInterval(self.timer);
								self.result.innerHTML = self.timeout + 1;
								
							} // finish
						}, 1000); // every sec

					};

					return self;
				});