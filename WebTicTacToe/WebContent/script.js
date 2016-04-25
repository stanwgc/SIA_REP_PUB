"use strict";
var myApp = angular.module('TicTacToe', []);

myApp.controller('TicTacToeCtrl', [function() {

    var self = this;
    self.fields = ["1", "2", "3", "4", "5", "6", "7", "8", "9"];
    self.activePlayer = true;
    self.anzFeld = self.fields.length;
    self.playerOne = "x";
    self.playerTwo = "o";
    self.aP = self.playerOne;
    self.pcMove;
    self.pcBlock = "o";
    self.combo = [
        [1, 2, 3],
        [4, 5, 6],
        [7, 8, 9],
        [1, 4, 7],
        [2, 5, 8],
        [3, 6, 9],
        [1, 5, 9],
        [3, 5, 7]
    ];

    self.classes = {
        def: ["fieldDefault"]
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

    self.select = function(index) {
        if (self.fields[index] !== "x" && self.fields[index] !== "o") {
            self.makeMove(self.activePlayer, index);
            //PC Spielt 
            if (!self.winCheck()) {
                if (self.anzFeld > 1) {
                    self.pcMove = self.freeFieldChoice();
                    self.makeMove(self.activePlayer, self.pcMove);
                }
            }
        }
    };

   

    self.makeMove = function(activePlayer, index) {
        if (activePlayer) {
            self.classes[index] = ["pone"];
            self.fields[index] = self.playerOne;
        } else {
            self.classes[index] = ["ptwo"];
            self.fields[index] = self.playerTwo;
        }
        self.check();
    };

    self.freeFieldChoice = function() {
     var Choice = 0;
          Choice = self.random();
                if (self.pcBlock !== "o") {
                Choice = self.pcBlock - 1;
                    self.pcBlock = "o";
            }
                 return Choice;       
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
                            if(self.fields[self.combo[i][k] - 1] !== "x" && self.fields[self.combo[i][k]-1] !== "o") {
                                
                                self.pcBlock = self.fields[self.combo[i][k]-1];
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
            self.endGame("The round was draw! Want to play again?");
        } else {
            self.playerChange();
        }
    };
    
      self.random = function(){
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

    return self;
}]);