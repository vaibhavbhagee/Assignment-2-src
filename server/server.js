var express = require('express');
var bodyParser = require('body-parser');
var mongo = require('mongodb').MongoClient;
var app = express();
var fs = require("fs");
var jwt = require("jsonwebtoken");
var crypto = require('crypto');

var secret = "vaibhavshreyanaayancop290assignment2";

var hierarchy_json = {
          "mess_complaint":{
                  "kumaon":{
                        "director":"director_id",
                        "dosa":"dosa_id",
                        "asso_dean_hm":"asso_dean_hm_id",
                        "warden":"warden_kumaon_id",
                        "house_secy":"house_secy_kumaon_id",
                        "mess_secy":"mess_secy_kumaon_id"
                  },
                  "vindhyachal":{
                        "director":"director_id",
                        "dosa":"dosa_id",
                        "asso_dean_hm":"asso_dean_hm_id",
                        "warden":"warden_vindhyachal_id",
                        "house_secy":"house_secy_vindhyachal_id",
                        "mess_secy":"mess_secy_vindhyachal_id"
                  }
          },
          "maintenance_complaint":{
                  "kumaon":{
                        "director":"director_id",
                        "dosa":"dosa_id",
                        "asso_dean_hm":"asso_dean_hm_id",
                        "warden":"warden_kumaon_id",
                        "house_secy":"house_secy_kumaon_id",
                        "maintenance_secy":"maintenance_secy_kumaon_id"
                  },
                  "vindhyachal":{
                        "director":"director_id",
                        "dosa":"dosa_id",
                        "assodean_hm":"assodean_hm_id",
                        "warden":"warden_vindhyachal_id",
                        "housesecy":"housesecy_vindhyachal_id",
                        "maintenance_secy":"maintenance_secy_vindhyachal_id"
                  }
          },
          "welfare_complaint":{
                  "director":"director_id",
                  "dosa":"dosa_id",
                  "president_sac":"president_sac_id",
                  "sac_gensec":"sac_gensec_id",
                  "bsw_gensec":"bsw_gensec_id"
          },
          "infrastructure_complaint":{
                  "director":"director_id",
                  "dosa":"dosa_id",
                  "president_sac":"president_sac_id",
                  "sac_gensec":"sac_gensec_id",
                  "bhm_gensec":"bhm_gensec_id"
          },
          "course_complaint":{
                  "cop290":{
                        "director":"director_id",
                        "doa":"doa_id",
                        "hod":"hod_cse_id",
                        "course_coordinator":"course_coordinator_cop290_id"
                  },
                  "col202":{
                        "director":"director_id",
                        "doa":"doa_id",
                        "hod":"hod_cse_id",
                        "course_coordinator":"course_coordinator_col202_id"
                  },
                  "ell231":{
                        "director":"director_id",
                        "doa":"doa_id",
                        "hod":"hod_ee_id",
                        "course_coordinator":"course_coordinator_ell231_id"
                  }
          },
          "nnn_complaint":{
            "nso":{
                  "director":"director_id",
                  "doa":"doa_id",
                  "president_nso":"president_nso_id",
                  "gensec_nso":"gensec_nso_id"
            },
            "ncc":{
                  "director":"director_id",
                  "doa":"doa_id",
                  "president_ncc":"president_ncc_id",
                  "gensec_ncc":"gensec_ncc_id"
            },
            "nss":{
                  "director":"director_id",
                  "doa":"doa_id",
                  "president_nss":"president_nss_id",
                  "gensec_nss":"gensec_nss_id"
            }
          },
          "security_complaint":{
                  "director":"director_id",
                  "security_officer":"security_officer_id"
          }
};

var input_users = [
//MESS COMPLAINT
{"unique_id":"cs5140297","name":"Vaibhav Bhagee","password":"d7218156761eff363a67505eca3fd90fde37ce08","department":"cse","contact_info":"9999988888","tags":["vindhyachal","nss"],"course_list":["cop290","col226"],"complaint_list":[]}
,
{"unique_id":"director_id","name":"Director","password":"b85b179882f31c43324ef124feaa3dd4ddae2915","department":"cse","contact_info":"9999988889","tags":[],"course_list":[],"complaint_list":[]}
,
{"unique_id":"dosa_id","name":"Dosa","password":"4a39f96e78f1dff9877dafff9e35e4e8bce520df","department":"ee","contact_info":"9999988890","tags":[],"course_list":[],"complaint_list":[]}
,
{"unique_id":"asso_dean_hm_id","name":"Asso Dean HM","password":"ce0dae246586bc69f8a98a6592184ceceda49185","department":"ee","contact_info":"9999988891","tags":[],"course_list":[],"complaint_list":[]}
,
{"unique_id":"warden_kumaon_id","name":"Dosa","password":"d51d283f6542727e2904e68c828b091f8460c5dd","department":"ee","contact_info":"9999988892","tags":[],"course_list":[],"complaint_list":[]}
,
{"unique_id":"house_secy_kumaon_id","name":"House Secy Kumaon","password":"48abcff594f9be1ff4b1d5a983f84d072798a8b6","department":"ee","contact_info":"9999988893","tags":["kumaon","nss"],"course_list":["ell231"],"complaint_list":[]}
 ,   //Kumaon
{"unique_id":"mess_secy_kumaon_id","name":"Mess Secy Kumaon","password":"2563f6aed4c92a732169c7e1195d46b589565c6d","department":"ee","contact_info":"9999988894","tags":["kumaon","nso"],"course_list":["cop290"],"complaint_list":[]}
  ,  //Vindy
{"unique_id":"mess_secy_vindhyachal_id","name":"Mess Secy Vindhyachal","password":"867b72ec6dd6c43e60422d54c7cedd7411e1c2bf","department":"cse","contact_info":"9999988895","tags":["vindhyachal","nso"],"course_list":["cop290"],"complaint_list":[]}
//MAINTENANCE COMPLAINT
,    //Kumaon
{"unique_id":"maintenance_secy_kumaon_id","name":"Maintenance Secy Kumaon","password":"1c870916e3e44e3f496ffbcc8bc88f2973a74667","department":"cse","contact_info":"9999988896","tags":["kumaon","ncc"],"course_list":["cop290","col202"],"complaint_list":[]}
,    //Vindy
{"unique_id":"maintenance_secy_vindhyachal_id","name":"Maintenance Secy Vindhyachal","password":"e53633a42b35eff05d273f06a40fa8a72b0d1425","department":"cse","contact_info":"9999988897","tags":["vindhyachal","nss"],"course_list":["cop290","ell231"],"complaint_list":[]}
,//WELFARE COMPLAINT
{"unique_id":"president_sac_id","name":"President SAC","password":"5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8","department":"cse","contact_info":"9999988898","tags":[],"course_list":[],"complaint_list":[]}
,
{"unique_id":"sac_gensec_id","name":"SAC GenSec","password":"5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8","department":"ee","contact_info":"9999988899","tags":["kumaon","nso"],"course_list":["cop290"],"complaint_list":[]}
,
{"unique_id":"bsw_gensec_id","name":"BSW GenSec","password":"5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8","department":"ee","contact_info":"9999988900","tags":["vindhyachal","ncc"],"course_list":["ell231"],"complaint_list":[]}
,//INFRASTRUCTURE COMPLAINT
{"unique_id":"bhm_gensec_id","name":"BHM GenSec","password":"5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8","department":"ee","contact_info":"9999988901","tags":["vindhyachal","nss"],"course_list":["col202"],"complaint_list":[]}
,//COURSE COMPLAINT
{"unique_id":"doa_id","name":"Doa","password":"4a39f96e78f1dff9877dafff9e35e4e8bce520df","department":"ee","contact_info":"9999988902","tags":[],"course_list":[],"complaint_list":[]}
,//CSE
{"unique_id":"hod_cse_id","name":"HOD CSE","password":"4a39f96e78f1dff9877dafff9e35e4e8bce520df","department":"cse","contact_info":"9999988903","tags":[],"course_list":[],"complaint_list":[]}
,
{"unique_id":"course_coordinator_cop290_id","name":"Course Coordinator COP 290","password":"4a39f96e78f1dff9877dafff9e35e4e8bce520df","department":"cse","contact_info":"9999988904","tags":[],"course_list":["cop290"],"complaint_list":[]}
,
{"unique_id":"course_coordinator_col202_id","name":"Course Coordinator COL 202","password":"4a39f96e78f1dff9877dafff9e35e4e8bce520df","department":"cse","contact_info":"9999988905","tags":[],"course_list":["col202"],"complaint_list":[]}
,//EE
{"unique_id":"hod_ee_id","name":"HOD EE","password":"4a39f96e78f1dff9877dafff9e35e4e8bce520df","department":"ee","contact_info":"9999988906","tags":[],"course_list":[],"complaint_list":[]}
,
{"unique_id":"course_coordinator_ell231_id","name":"Course Coordinator ELL 231","password":"4a39f96e78f1dff9877dafff9e35e4e8bce520df","department":"ee","contact_info":"9999988907","tags":[],"course_list":["ell231"],"complaint_list":[]}
,//NSO
{"unique_id":"president_nso_id","name":"President NSO","password":"4a39f96e78f1dff9877dafff9e35e4e8bce520df","department":"ee","contact_info":"9999988908","tags":[],"course_list":[],"complaint_list":[]}
,
{"unique_id":"gensec_nso_id","name":"GenSec NSO","password":"4a39f96e78f1dff9877dafff9e35e4e8bce520df","department":"ee","contact_info":"9999988909","tags":["kumaon","nso"],"course_list":["cop290"],"complaint_list":[]}
,//NSS
{"unique_id":"president_nss_id","name":"President NSS","password":"4a39f96e78f1dff9877dafff9e35e4e8bce520df","department":"ee","contact_info":"9999988910","tags":[],"course_list":[],"complaint_list":[]}
,
{"unique_id":"gensec_nss_id","name":"GenSec NSS","password":"4a39f96e78f1dff9877dafff9e35e4e8bce520df","department":"ee","contact_info":"9999988909","tags":["kumaon","nss"],"course_list":["cop290"],"complaint_list":[]}
,//NCC
{"unique_id":"president_ncc_id","name":"President NCC","password":"4a39f96e78f1dff9877dafff9e35e4e8bce520df","department":"ee","contact_info":"9999988911","tags":[],"course_list":[],"complaint_list":[]}
,
{"unique_id":"gensec_ncc_id","name":"GenSec NCC","password":"4a39f96e78f1dff9877dafff9e35e4e8bce520df","department":"ee","contact_info":"9999988912","tags":["vindhyachal","ncc"],"course_list":["col202"],"complaint_list":[]}
,//SECURITY
{"unique_id":"security_officer_id","name":"Security Officer","password":"4a39f96e78f1dff9877dafff9e35e4e8bce520df","department":"cse","contact_info":"9999988913","tags":[],"course_list":[],"complaint_list":[]}
]

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
    res.send({ message: 'Welcome to the Complaints Management System!' });
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
        users.insert(input_users,function(error,result1)
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
  *  API to empty user collection
  */

  apiRoutes.get('/empty_users', function(req, res) {
    users.remove({},function(err,result)
    {
      if (err)
        throw err;

        res.send({success:true,message:"User Collection Emptied successfully"});
    });
  });

  /**
  *  API to empty complaints collection
  */

  apiRoutes.get('/empty_complaints', function(req, res) {
    complaints.remove({},function(err,result)
    {
      if (err)
        throw err;

        res.send({success:true,message:"Complaints Collection Emptied successfully"});
    });
  });

  /**
  *  API to empty notifications collection
  */

  apiRoutes.get('/empty_notifications', function(req, res) {
    notifications.remove({},function(err,result)
    {
      if (err)
        throw err;

        res.send({success:true,message:"Notifications Collection Emptied successfully"});
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

//Temporary API to get all notifications
  apiRoutes.get('/all_notifications', function(req, res) {
    notifications.find().toArray(function(err,result)
    {
      if (err)
        throw err;

      if (result.length == 0)
        res.send("Empty collection");
      else
        res.send(result);
    });
  });

//Temporary API to get complaints
  apiRoutes.get('/all_complaints', function(req, res) {
    complaints.find().toArray(function(err,result)
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

      (complaints.find({"complaint_id":{$in: req.body.complaint_list}}).toArray(function(err,result)
      {
        if (err)
          throw err;

          res.send({success:true,complaints:result});
      }));
  });

  /**
  *  API to get list of notifications
  */

  apiRoutes.post('/notifications', function(req, res) {

      (notifications.find({"complaint_id":{$in: req.body.complaint_list}}).toArray(function(err,result)
      {
        if (err)
          throw err;

          res.send({success:true,notifications:result});
      }));
  });

  /**
  *  API to post a new Thread to a complaint
  */

  apiRoutes.post('/new_thread', function(req, res) {

      (complaints.find({"complaint_id":req.body.complaint_id}).toArray(function(err,result) // Fetch the relavant complaint
      {
        if (err)
          throw err;

        if (result.length == 0)
          res.send({success:false,message:"Incorrect complaint ID"});
        else
        {
          // Create the thread object
          var thread_obj = {
            thread_id:req.body.complaint_id+"_th"+result[0]["threads"].length,
            complaint_id:req.body.complaint_id,
            title:req.body.title,
            description:req.body.description,
            last_updated:new Date(),
            comments:[]
          }

          result[0]["threads"].push(thread_obj);

          // Add the thread to the complaint
          (complaints.update({"complaint_id":req.body.complaint_id},{$set:{"threads":result[0]["threads"]}},function(err,result1)
          {
            if (err)
              throw err;

            var notif = {
              complaint_id:req.body.complaint_id,
              timestamp: new Date(),
              content: req.decoded.name + " posted a new thread under complaint id "+req.body.complaint_id
            }

            // Generate the required notification
            notifications.insert(notif,function(err,result2)
            {
              if (err)
                throw err;

                res.send({success:true,message:"Thread Added Successfully",complaint:result[0],notification:notif});
            });    

          }));          
        }
      }));
  });

  /**
  *  API to post a new Comment to a thread of a complaint
  */

  apiRoutes.post('/new_comment', function(req, res) {

      (complaints.find({"complaint_id":req.body.complaint_id}).toArray(function(err,result) // Fetch the required complaint
      {
        if (err)
          throw err;

        if (result.length == 0)
          res.send({success:false,message:"Incorrect complaint ID"});
        else
        {
          var comment_obj = {
            posted_by:req.decoded.unique_id,
            description:req.body.description,
            timestamp:new Date(),
          }

          for (var i = 0; i < result[0]["threads"].length; i++ )
          {
            if(result[0]["threads"][i]["thread_id"] === req.body.thread_id)
              result[0]["threads"][i]["comments"].push(comment_obj);
            // break;
          }

          (complaints.update({"complaint_id":req.body.complaint_id},{$set:{"threads":result[0]["threads"]}},function(err,result1)
          {
            if (err)
              throw err;

            var notif = {
              complaint_id:req.body.complaint_id,
              timestamp: new Date(),
              content: req.decoded.name + " posted a new comment on the thread "+req.body.thread_id+" under complaint id "+req.body.complaint_id
            }

            notifications.insert(notif,function(err,result2)
            {
              if (err)
                throw err;

                res.send({success:true,message:"Thread Added Successfully",complaint:result[0],notification:notif});
            });    

          }));          
        }
      }));
  });

  /**
  *  API to mark a complaint as resolved
  */

  apiRoutes.post('/mark_resolved', function(req, res) {

      (complaints.find({"complaint_id":req.body.complaint_id}).toArray(function(err,result) // Fetch the required complaint
      {
        if (err)
          throw err;

        if (result.length == 0)
          res.send({success:false,message:"Incorrect complaint ID"});
        else
        {
          var status = "unresolved";

          if (result[0]["lodged_by"] === req.decoded.unique_id)
            status = "resolved";
          else if (result[0]["current_level"] === req.decoded.unique_id)
            status = "under_resolution";

          result[0]["current_status"] = status;

          (complaints.update({"complaint_id":req.body.complaint_id},{$set:{"current_status":status}},function(err,result1)
          {
            if (err)
              throw err;

            var notif = {
              complaint_id:req.body.complaint_id,
              timestamp: new Date(),
              content: req.decoded.name + " marked the complaint "+req.body.complaint_id+" as "+status
            }

            notifications.insert(notif,function(err,result2)
            {
              if (err)
                throw err;

                res.send({success:true,message:"Complaint Status Changed Successfully",complaint:result[0],notification:notif});
            });    

          }));          
        }
      }));
  });

  /**
  *  API to relodge a complaint with the same authority
  */

  apiRoutes.post('/relodge_same_authority', function(req, res) {

      (complaints.find({"complaint_id":req.body.complaint_id}).toArray(function(err,result) // Fetch the required complaint
      {
        if (err)
          throw err;

        if (result.length == 0)
          res.send({success:false,message:"Incorrect complaint ID"});
        else
        {
          var status = "unresolved";

          result[0]["current_status"] = status;

          (complaints.update({"complaint_id":req.body.complaint_id},{$set:{"current_status":status}},function(err,result1)
          {
            if (err)
              throw err;

            var notif = {
              complaint_id:req.body.complaint_id,
              timestamp: new Date(),
              content: req.decoded.name + " relodged the complaint with "+result[0]["current_level"]
            }

            notifications.insert(notif,function(err,result2)
            {
              if (err)
                throw err;

                res.send({success:true,message:"Complaint Relodged Successfully",complaint:result[0],notification:notif});
            });    

          }));          
        }
      }));
  });

  /**
  *  API to vote
  */

  apiRoutes.post('/vote', function(req, res) {

      (complaints.find({"complaint_id":req.body.complaint_id}).toArray(function(err,result) // Fetch the required complaint
      {
        if (err)
          throw err;

        if (result.length == 0)
          res.send({success:false,message:"Incorrect complaint ID"});
        else
        {
          var flag = false;
          for( var i = 0; i < result[0]["votes"]["voted"].length; i++)
          {
            if (result[0]["votes"]["voted"][i] === req.decoded.unique_id)
            {
              res.send({success:false,message:"Already Voted"});
              flag = true;
            }
          }
          
          if(!flag)
          {
            if (req.body.type === "upvote")
              result[0]["votes"]["upvotes"]+=1;
            else
              result[0]["votes"]["downvotes"]+=1;

            result[0]["votes"]["voted"].push(req.decoded.unique_id);

            (complaints.update({"complaint_id":req.body.complaint_id},{$set:result[0]},function(err,result1)
            {
              if (err)
                throw err;

              var notif = {
                complaint_id:req.body.complaint_id,
                timestamp: new Date(),
                content: req.decoded.name + " "+req.body.type+"d the complaint "+req.body.complaint_id
              }

              notifications.insert(notif,function(err,result2)
              {
                if (err)
                  throw err;

                  res.send({success:true,message:"Voted Successfully",complaint:result[0],notification:notif});
              });    

            }));
          }          
        }
      }));
  });

  // TODO: relodge with higher authority

  /**
  *  API to make a new complaint
  */

  apiRoutes.post('/new_complaint', function(req, res) {

    var hierarchy;

    var lodged_by = req.decoded.unique_id;

    var complaint_id = "c"+crypto.createHash('sha1').update((new Date())+"").digest('hex');
    
    if (req.body.type === "mess_complaint" || req.body.type === "maintenance_complaint" )
    {  
      hierarchy = hierarchy_json[req.body.type][(req.decoded.tags[0]).toLowerCase()];

      if (req.body.is_community === true)
      {
        // var complaint_id = "c"+crypto.createHash('sha1').update(new Date()+"").digest('hex');
        var current_level = hierarchy[Object.keys(hierarchy)[Object.keys(hierarchy).length-1]];
        var timestamp = {lodging:new Date(),update:new Date()};

        users.update({"tags":{$in: [req.decoded.tags[0]]}},{ $addToSet: { complaint_list: complaint_id } },{ multi: true },function(err,result)
        {
          if (err)
            throw err;

            users.update({"unique_id":{$in: [current_level]}},{ $addToSet: { complaint_list: complaint_id } },function(err,result2)
            {
              if (err)
                throw err;

              var complaint = {
                complaint_id: complaint_id,
                lodged_by: lodged_by,
                title: req.body.title,
                description: req.body.description,
                timestamp: timestamp,
                is_community: true,
                type: req.body.type,
                authority_hierarchy: hierarchy,
                current_level: current_level,
                current_level_index: Object.keys(hierarchy).length-1,
                current_status: "unresolved",
                votes: {
                  upvotes:0,
                  downvotes:0,
                  voted:[]
                },
                threads: []
              }

              complaints.insert(complaint,function(err,result1){

                if (err)
                  throw err;
                res.send({success:true,complaint:complaint});

              });

            });            
        });
      }
      else
      {
        // var complaint_id = "c"+crypto.createHash('sha1').update(new Date()).digest('hex');
        var current_level = hierarchy[Object.keys(hierarchy)[Object.keys(hierarchy).length-1]];
        var timestamp = {lodging:new Date(),update:new Date()};

        users.update({"unique_id":{$in: [current_level,lodged_by]}},{ $addToSet: { complaint_list: complaint_id } },function(err,result2)
        {
          if (err)
            throw err;

          var complaint = {
            complaint_id: complaint_id,
            lodged_by: lodged_by,
            title: req.body.title,
            description: req.body.description,
            timestamp: timestamp,
            is_community: false,
            type: req.body.type,
            authority_hierarchy: hierarchy,
            current_level: current_level,
            current_level_index: Object.keys(hierarchy).length-1,
            current_status: "unresolved",
            threads: []
          }

          complaints.insert(complaint,function(err,result1){

            if (err)
              throw err;
            res.send({success:true,complaint:complaint});

          });

        });

      }
    }
    else if (req.body.type === "nnn_complaint" )
    {  
      hierarchy = hierarchy_json[req.body.type][req.decoded.tags[1]];

      if (req.body.is_community === true)
      {
        // var complaint_id = "c"+crypto.createHash('sha1').update(new Date()).digest('hex');
        var current_level = hierarchy[Object.keys(hierarchy)[Object.keys(hierarchy).length-1]];
        var timestamp = {lodging:new Date(),update:new Date()};

        users.update({"tags":{$in: [req.decoded.tags[1]]}},{ $addToSet: { complaint_list: complaint_id } },{ multi: true },function(err,result)
        {
          if (err)
            throw err;

            users.update({"unique_id":{$in: [current_level]}},{ $addToSet: { complaint_list: complaint_id } },function(err,result2)
            {
              if (err)
                throw err;

              var complaint = {
                complaint_id: complaint_id,
                lodged_by: lodged_by,
                title: req.body.title,
                description: req.body.description,
                timestamp: timestamp,
                is_community: true,
                type: req.body.type,
                authority_hierarchy: hierarchy,
                current_level: current_level,
                current_level_index: Object.keys(hierarchy).length-1,
                current_status: "unresolved",
                votes: {
                  upvotes:0,
                  downvotes:0,
                  voted:[]
                },
                threads: []
              }

              complaints.insert(complaint,function(err,result1){

                if (err)
                  throw err;
                res.send({success:true,complaint:complaint});

              });

            });            
        });
      }
      else
      {
        // var complaint_id = "c"+crypto.createHash('sha1').update(new Date()).digest('hex');
        var current_level = hierarchy[Object.keys(hierarchy)[Object.keys(hierarchy).length-1]];
        var timestamp = {lodging:new Date(),update:new Date()};

        users.update({"unique_id":{$in: [current_level,lodged_by]}},{ $addToSet: { complaint_list: complaint_id } },function(err,result2)
        {
          if (err)
            throw err;

          var complaint = {
            complaint_id: complaint_id,
            lodged_by: lodged_by,
            title: req.body.title,
            description: req.body.description,
            timestamp: timestamp,
            is_community: false,
            type: req.body.type,
            authority_hierarchy: hierarchy,
            current_level: current_level,
            current_level_index: Object.keys(hierarchy).length-1,
            current_status: "unresolved",
            threads: []
          }

          complaints.insert(complaint,function(err,result1){

            if (err)
              throw err;
            res.send({success:true,complaint:complaint});

          });

        });

      }
    }
    else if (req.body.type === "course_complaint")
    {
      hierarchy = hierarchy_json[req.body.type][req.body.course_id];

      if (req.body.is_community === true)
      {
        // var complaint_id = "c"+crypto.createHash('sha1').update(new Date()).digest('hex');
        var current_level = hierarchy[Object.keys(hierarchy)[Object.keys(hierarchy).length-1]];
        var timestamp = {lodging:new Date(),update:new Date()};

        users.update({"course_list":{$in: [req.body.course_id]}},{ $addToSet: { complaint_list: complaint_id } },{ multi: true },function(err,result)
        {
          if (err)
            throw err;

            users.update({"unique_id":{$in: [current_level]}},{ $addToSet: { complaint_list: complaint_id } },function(err,result2)
            {
              if (err)
                throw err;

              var complaint = {
                complaint_id: complaint_id,
                lodged_by: lodged_by,
                title: req.body.title,
                description: req.body.description,
                timestamp: timestamp,
                is_community: true,
                type: req.body.type,
                authority_hierarchy: hierarchy,
                current_level: current_level,
                current_level_index: Object.keys(hierarchy).length-1,
                current_status: "unresolved",
                votes: {
                  upvotes:0,
                  downvotes:0,
                  voted:[]
                },
                threads: []
              }

              complaints.insert(complaint,function(err,result1){

                if (err)
                  throw err;
                res.send({success:true,complaint:complaint});

              });

            });            
        });
      }
      else
      {
        // var complaint_id = "c"+crypto.createHash('sha1').update(new Date()).digest('hex');
        var current_level = hierarchy[Object.keys(hierarchy)[Object.keys(hierarchy).length-1]];
        var timestamp = {lodging:new Date(),update:new Date()};

        users.update({"unique_id":{$in: [current_level,lodged_by]}},{ $addToSet: { complaint_list: complaint_id } },function(err,result2)
        {
          if (err)
            throw err;

          var complaint = {
            complaint_id: complaint_id,
            lodged_by: lodged_by,
            title: req.body.title,
            description: req.body.description,
            timestamp: timestamp,
            is_community: false,
            type: req.body.type,
            authority_hierarchy: hierarchy,
            current_level: current_level,
            current_level_index: Object.keys(hierarchy).length-1,
            current_status: "unresolved",
            threads: []
          }

          complaints.insert(complaint,function(err,result1){

            if (err)
              throw err;
            res.send({success:true,complaint:complaint});

          });

        });

      }
    }
    else
    {
      hierarchy = hierarchy_json[req.body.type];

      if (req.body.is_community === true)
      {
        // var complaint_id = "c"+crypto.createHash('sha1').update(new Date()).digest('hex');
        var current_level = hierarchy[Object.keys(hierarchy)[Object.keys(hierarchy).length-1]];
        var timestamp = {lodging:new Date(),update:new Date()};

        users.update({},{ $addToSet: { complaint_list: complaint_id } },{ multi: true },function(err,result)
        {
          if (err)
            throw err;

            users.update({"unique_id":{$in: [current_level]}},{ $addToSet: { complaint_list: complaint_id } },function(err,result2)
            {
              if (err)
                throw err;

              var complaint = {
                complaint_id: complaint_id,
                lodged_by: lodged_by,
                title: req.body.title,
                description: req.body.description,
                timestamp: timestamp,
                is_community: true,
                type: req.body.type,
                authority_hierarchy: hierarchy,
                current_level: current_level,
                current_level_index: Object.keys(hierarchy).length-1,
                current_status: "unresolved",
                votes: {
                  upvotes:0,
                  downvotes:0,
                  voted:[]
                },
                threads: []
              }

              complaints.insert(complaint,function(err,result1){

                if (err)
                  throw err;
                res.send({success:true,complaint:complaint});

              });

            });            
        });
      }
      else
      {
        // var complaint_id = "c"+crypto.createHash('sha1').update(new Date()).digest('hex');
        var current_level = hierarchy[Object.keys(hierarchy)[Object.keys(hierarchy).length-1]];
        var timestamp = {lodging:new Date(),update:new Date()};

        users.update({"unique_id":{$in: [current_level,lodged_by]}},{ $addToSet: { complaint_list: complaint_id } },function(err,result2)
        {
          if (err)
            throw err;

          var complaint = {
            complaint_id: complaint_id,
            lodged_by: lodged_by,
            title: req.body.title,
            description: req.body.description,
            timestamp: timestamp,
            is_community: false,
            type: req.body.type,
            authority_hierarchy: hierarchy,
            current_level: current_level,
            current_level_index: Object.keys(hierarchy).length-1,
            current_status: "unresolved",
            threads: []
          }

          complaints.insert(complaint,function(err,result1){

            if (err)
              throw err;
            res.send({success:true,complaint:complaint});

          });

        });

      }
    }
  });

  /**
  *  API to relodge a complaint with the next Higher authority
  */

  apiRoutes.post('/relodge_same_authority', function(req, res) {

      (complaints.find({"complaint_id":req.body.complaint_id}).toArray(function(err,result) // Fetch the required complaint
      {
        if (err)
          throw err;

        if (result.length == 0)
          res.send({success:false,message:"Incorrect complaint ID"});
        else
        {
          var status = "unresolved";

          result[0]["current_status"] = status;

          if (result[0]["current_level_index"] === 0)
          {
            res.send({success:false,message:"Already at the highest level"})
          }
          else
          {

            result[0]["current_level"] = result[0]["hierarchy"][Object.keys(result[0]["hierarchy"])[result[0]["current_level_index"]-1]];            
            result[0]["current_level_index"] = result[0]["current_level_index"]-1;

          (complaints.update({"complaint_id":req.body.complaint_id},{$set:result[0]},function(err,result1)
          {
            if (err)
              throw err;

            var notif = {
              complaint_id:req.body.complaint_id,
              timestamp: new Date(),
              content: req.decoded.name + " relodged the complaint with "+result[0]["current_level"]
            }

            notifications.insert(notif,function(err,result2)
            {
              if (err)
                throw err;

                users.update({"unique_id":{$in: [current_level]}},{ $addToSet: { complaint_list: complaint_id } },function(err,result2)
                {
                  if (err)
                    throw err;
                  res.send({success:true,message:"Complaint Relodged Successfully",complaint:result[0],notification:notif});
                });
            });    

          })); 
          }         
        }
      }));
  });

  app.use('/api', apiRoutes);

});