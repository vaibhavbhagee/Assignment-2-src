var express = require('express');
var bodyParser = require('body-parser');
var mongo = require('mongodb').MongoClient;
var app = express();
var fs = require("fs");
var jwt = require("jsonwebtoken");
var crypto = require('crypto');

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

var apiRoutes = express.Router();

mongo.connect('mongodb://127.0.0.1/complaint_system', function(err,db) {
	if(err) throw err;

  // Collections in the database
  var users = db.collection("users");
  var special_users = db.collection("special_users");
  var complaints = db.collection("complaints");
  var notifications = db.collection("notifications");

  // API definitions

  app.get('/', function(req, res) {
    res.send('Hello! The API is at http://localhost:' + port + '/api');
  });

  apiRoutes.get('/', function(req, res) {
    res.send({ message: 'Welcome to the coolest API on earth!' });
  });

  /**
  *  API to initialize the database with values
  */

  apiRoutes.get('/populate', function(req, res) {
    users.find().toArray(function(err,result)
    {
      if (err)
        throw err;

      if (result.length == 0)
        users.insert({"unique_id":"2014CS50297","name":"Vaibhav Bhagee","password":"d7218156761eff363a67505eca3fd90fde37ce08","department":"Computer Science","contact_info":"9999988888","tags":["Vindhyachal","NSS"],"course_list":["COP290","COL226"],"complaint_list":[]},function(error,result1)
        {
          if (err)
            throw err;

            res.send({success:true,message:"Successfully Populated"});
        });
      else
        res.send({success:false,message:"Database contains data which can be overwritten"});
    });
  });

  /**
  *  API for LOGIN authentication and Token generation for normal users
  */

  apiRoutes.post('/login',function(req,res)
  {
    var username = req.body.username;
    var password = crypto.createHash('sha1').update(req.body.password).digest('hex'); // Hash the string password to SHA-1

    users.find({"unique_id":username,"password":password}).toArray(function(err,result)
    {
      if (err)
        throw err;

      if (result.length == 0)
        res.send({"Success":false,"Message":"Incorrect Credentials"});
      else
      {
        var token = jwt.sign(result[0], app.get('superSecret'), {
          expiresIn: 86400 // expires in 24 hours
        });

        // return the information including token as JSON
        res.send({
          success: true,
          token: token,
          unique_id: result[0].unique_id,
          name: result[0].name,
          department: result[0].department,
          contact_info: result[0].contact_info,
          tags: result[0].tags,
          course_list: result[0].course_list,
          complaint_list: result[0].complaint_list
        });
      }
    });

  });

  /**
  *  API for LOGIN authentication and Token generation for special users
  */

  apiRoutes.post('/special_login',function(req,res)
  {
    var username = req.body.username;
    var password = crypto.createHash('sha1').update(req.body.password).digest('hex'); // Hash the string password to SHA-1

    special_users.find({"unique_id":username,"password":password}).toArray(function(err,result)
    {
      if (err)
        throw err;

      if (result.length == 0)
        res.send({"Success":false,"Message":"Incorrect Credentials"});
      else
      {
        var token = jwt.sign(result[0], app.get('superSecret'), {
          expiresInMinutes: 86400 // expires in 24 hours
        });

        // return the information including token as JSON
        res.send({
          success: true,
          token: token,
          unique_id: result[0].unique_id,
          name: result[0].name,
          department: result[0].department,
          contact_info: result[0].contact_info,
          tags: result[0].tags,
          course_list: result[0].course_list,
          complaint_list: result[0].complaint_list
        });
      }
    });

  });

  apiRoutes.use(function(req, res, next) {

  // check header or url parameters or post parameters for token
  var token = req.body.token || req.query.token || req.headers['x-access-token'];

  // decode token
  if (token) {

    // verifies secret and checks exp
    jwt.verify(token, app.get('superSecret'), function(err, decoded) {      
      if (err) {
        return res.json({ success: false, message: 'Failed to authenticate token.' });    
      } else {
        // if everything is good, save to request for use in other routes
        req.decoded = decoded;    
        next();
      }
    });

  } else {

    // if there is no token
    // return an error
    return res.status(403).send({ 
        success: false, 
        message: 'No token provided.' 
    });
    
  }
});

//Temporary API to get users
  apiRoutes.get('/users', function(req, res) {
    users.find().toArray(function(err,result)
    {
      if (err)
        throw err;

      if (result.length == 0)
        res.send("Empty collection");
      else
        res.send(result);
    });
  });


  app.use('/api', apiRoutes);

});