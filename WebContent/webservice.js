/**
 * 
 */
var service = angular.module('TicTacToe', ['ngResource']);
service.factory('TicTacToe', function($resource) {
return $resource('/service/rest', {}, {
	query: {method: 'GET', params: {},isArray: false},
	create: {method: 'POST' }
	})	
});