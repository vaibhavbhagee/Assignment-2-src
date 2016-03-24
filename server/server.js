var express = require('express');
var bodyParser = require('body-parser');
var mongo = require('mongodb').MongoClient;
var app = express();
var fs = require("fs");
var jwt = require("jsonwebtoken");

var secret = "vaibhavshreyanaayancop290assignment2";

var port = process.env.PORT || 8081;

app.use(bodyParser.urlencoded({
  extended: true
}))

app.use(bodyParser.json())

app.set('superSecret',secret); //Set the secret variable

var server = app.listen(port, function () {

  var host = server.address().address
  var port = server.address().port

  console.log("Example app listening at http://%s:%s", host, port)

})

/**
 * 	HTTP-POST REQUEST
 */
mongo.connect('mongodb://127.0.0.1/complaint_system', function(err,db) {
	if(err) throw err;

  // Collections in the database

  // API definitions
  app.get('/', function(req, res) {
    res.send('Hello! The API is at http://localhost:' + port + '/api');
  });

});