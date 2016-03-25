var express = require('express');
var bodyParser = require('body-parser');
var mongo = require('mongodb').MongoClient;
var app = express();
var fs = require("fs");
var jwt = require("jsonwebtoken");
var crypto = require('crypto');

var secret = "vaibhavshreyanaayancop290assignment2";

//TODO: Add Hierarchy JSON object here

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
  *  API for LOGIN authentication and Token generation for special users (src: https://scotch.io/tutorials/authenticate-a-node-js-api-with-json-web-tokens)
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

  /**
  *  API to check and decode token
  */

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

  /**
  *  API to add a user to the database
  */

  //TODO: Add checks for the request being made by a special user only

  apiRoutes.post('/add_user', function(req, res) {
    var password = crypto.createHash('sha1').update(req.body.password).digest('hex'); // Hash the string password to SHA-1
    
    users.find({"unique_id":req.body.unique_id}).toArray(function(err,result)
    {
      if (err)
        throw err;

      if (result.length == 0)
        users.insert({"unique_id":req.body.unique_id,"name":req.body.name,"password":password,"department":req.body.department,"contact_info":req.body.contact_info,"tags":req.body.tags,"course_list":req.body.course_list,"complaint_list":[]},function(err,result1)
        {
          if (err)
            throw err;

            res.send({success:true,message:"User Added Successfully"});
        });    
      else
        res.send({success:false,message:"User Already Exists"});
    });
  });

  /**
  *  API to delete a user from the database
  */

  apiRoutes.post('/delete_user', function(req, res) {
    
    users.find({"unique_id":req.body.unique_id}).toArray(function(err,result)
    {
      if (err)
        throw err;

      if (result.length != 0)
        users.remove({"unique_id":req.body.unique_id},function(err,result1)
        {
          if (err)
            throw err;

            res.send({success:true,message:"User Deleted Successfully"});
        });    
      else
        res.send({success:false,message:"User Not Found"});
    });
  });

  /**
  *  API to update the details of a user
  */

  apiRoutes.post('/update_user_details', function(req, res) {
    var password = crypto.createHash('sha1').update(req.body.password).digest('hex'); // Hash the string password to SHA-1
    
    users.find({"unique_id":req.body.unique_id}).toArray(function(err,result)
    {
      if (err)
        throw err;

      if (result.length != 0)
        users.update({"unique_id":req.body.unique_id},{$set:{"unique_id":req.body.unique_id,"name":req.body.name,"password":password,"department":req.body.department,"contact_info":req.body.contact_info,"tags":req.body.tags,"course_list":req.body.course_list,"complaint_list":[]}},function(err,result1)
        {
          if (err)
            throw err;

            res.send({success:true,message:"User Updated Successfully"});
        });    
      else
        res.send({success:false,message:"User Not Found"});
    });
  });

  /**
  *  API to get list of complaints of a user
  */

  // parameter passed in URL as /api/complaintlist?unique_id=<unique_id_of_the_user>

  apiRoutes.get('/complaintlist', function(req, res) {
    
    users.find({"unique_id":req.query.unique_id}).toArray(function(err,result)
    {
      if (err)
        throw err;

      if (result.length == 0)
            res.send({success:false,message:"User Not found"});
      else
        res.send({success:false,complaintlist:result[0].complaint_list});
    });
  });

  /**
  *  API to get complaint details of a user
  */

  apiRoutes.post('/complaint_details', function(req, res) {

      (complaints.find({"complaint_id":req.body.complaint_id}).toArray(function(err,result)
      {
        if (err)
          throw err;

        if (result.length == 0)
          res.send({success:false,message:"Complaint not found"});
        else
        {
          res.send({success:true,complaint:result[0]});
        }
      }));
  });

  app.use('/api', apiRoutes);

});