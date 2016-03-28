function AccountPageOne()
{
	this.Index = "";
	this.Left = "";
	this.Top = "";
	this.Parent = "";
	this.Width = "";
	this.Height = "";
	this.Opacity = "";
	this.AssociatedData = {};


	var Object = this;

	this.edit = function()
	{
		document.getElementById( "WelcomeName_Div"+Object.Index+"" ).innerHTML = "Welcome: Dr. "+Object.AssociatedData.First_name+" "+Object.AssociatedData.Second_name;
	}

	// Initialization

	this.Initialize = function(Index, Left , Top , Parent , Width , Height , Opacity, AssociatedData)
	{
		this.Index = Index;
		this.Left = Left;
		this.Top = Top;
		this.Parent = Parent;
		this.Width = Width;
		this.Height = Height;
		this.Opacity = Opacity;
		this.AssociatedData = AssociatedData;

		this.Render();

	}

	this.Render = function()
	{

	// Begin User Interface

		Object.AccountPageDiv = 	
					"<div id='AccountPage"+Object.Index+"' style='position: absolute; top: "+Object.Top+"%; left: "+Object.Left+"%; height: "+Object.Height+"%; width: "+Object.Width+"%; opacity: "+Object.Opacity+"; border: 0px solid #000000; border-radius: 0px 0px 0px 0px; overflow: hidden;'></div>";
	
		$( "#"+Object.Parent+"" ).append(Object.AccountPageDiv);
		// $( "#"+"AccountPage"+Object.Index+"" ).css({"background": "url('./bkgacct1.jpg')"});

		Object.FunctionDiv = 	
					"<div id='Function_Div"+Object.Index+"' style='position: absolute; top: 40px; left: 0px; bottom: 0px; width: 25%; opacity: "+Object.Opacity+"; border: 2px solid rgb(0,0,255);background-color:#AFC9FA; border-radius: 0px 0px 0px 0px; overflow: hidden;'></div>";

		$( "#AccountPage"+Object.Index+"" ).append(Object.FunctionDiv);

		Object.WelcomeDiv =
					"<div id='Welcome_Div"+Object.Index+"' style='position: absolute; top: 0px; left: 0px; height: 40px; width: 100%; opacity: "+Object.Opacity+"; border: 2px solid #0B197D;background-color:#0B197D; border-radius: 0px 0px 0px 0px; overflow: hidden;'></div>"; 

		$( "#AccountPage"+Object.Index+"" ).append(Object.WelcomeDiv);

		Object.WorkAreaDiv = 	
					"<div id='WorkArea_Div"+Object.Index+"' style='position: absolute; top: 43px; left: 25%; bottom: 0px; width: 75%; opacity: "+Object.Opacity+"; border: 2px solid rgb(0,0,255);background-color:transparent; border-radius: 0px 0px 0px 0px; overflow: scroll;'></div>";

		$( "#AccountPage"+Object.Index+"" ).append(Object.WorkAreaDiv);

		Object.WelcomeNameDiv =
					"<div id='WelcomeName_Div"+Object.Index+"' style='position: absolute; top: 0px; left: 0px; height: 40px; width: 45%; opacity: "+Object.Opacity+"; border: 0px solid #0B197D;background-color:transparent; border-radius: 0px 0px 0px 0px; overflow: hidden; text-align: left; line-height: 40px; color: #FFFFFF; padding-left:10px;'>Welcome: "+Object.AssociatedData.First_name+" "+Object.AssociatedData.Second_name+"</div>"; 

		$( "#Welcome_Div"+Object.Index+"" ).append(Object.WelcomeNameDiv);

		Object.LogoutButton = "<input type='button' id='LogoutButton' value='Logout' />";
		$( "#Welcome_Div"+Object.Index+"" ).append(Object.LogoutButton);
		$( "#LogoutButton" ).css( {"position":"absolute","top":"5%","right":"5px", "width":"10%" , "height":"90%", "font-size":"1.2em", "font-weight": "semibold","color":"#FFFFFF","background-color":"transparent","border":"0px solid rgb(88,151,19)","border-radius":"10px","padding":"0px", "padding-left":"0px", "padding-right":"0px", "box-shadow":"0px 0px 0px #888888", "text-align":"center"});

		// Object.ChangePasswdButton = "<input type='button' id='ChangePasswdButton' value='Change Password' />";
		// $( "#Welcome_Div"+Object.Index+"" ).append(Object.ChangePasswdButton);
		// $( "#ChangePasswdButton" ).css( {"position":"absolute","top":"5%","right":"13%", "width":"12%" , "height":"90%", "font-size":"1.2em", "font-weight": "semibold","color":"#FFFFFF","background-color":"transparent","border":"0px solid rgb(88,151,19)","border-radius":"10px","padding":"0px", "padding-left":"0px", "padding-right":"0px", "box-shadow":"0px 0px 0px #888888", "text-align":"center"});
		
		Object.ViewProfileButton = "<input type='button' id='ViewProfileButton' value='View Profile' />";
		$( "#Function_Div"+Object.Index+"" ).append(Object.ViewProfileButton);
		$( "#ViewProfileButton" ).css( {"position":"absolute","top":"10%","left":"20%", "width":"60%" , "height":"20%", "font-size":"1.2em", "font-weight": "semibold","color":"#FFFFFF","background-color":"#276FF5","border":"0px solid rgb(88,151,19)","border-radius":"10px","padding":"0px", "padding-left":"0px", "padding-right":"0px", "box-shadow":"2px 2px 5px #888888", "text-align":"center"});

		Object.StartCaseButton = "<input type='button' id='StartCaseButton' value='Start Case' />";
		$( "#Function_Div"+Object.Index+"" ).append(Object.StartCaseButton);
		$( "#StartCaseButton" ).css( {"position":"absolute","top":"40%","left":"20%", "width":"60%" , "height":"20%", "font-size":"1.2em", "font-weight": "semibold","color":"#FFFFFF","background-color":"#276FF5","border":"0px solid rgb(88,151,19)","border-radius":"10px","padding":"0px", "padding-left":"0px", "padding-right":"0px", "box-shadow":"2px 2px 5px #888888", "text-align":"center"});

		Object.PreviousCasesButton = "<input type='button' id='PreviousCasesButton' value='Previous Cases' />";
		$( "#Function_Div"+Object.Index+"" ).append(Object.PreviousCasesButton);
		$( "#PreviousCasesButton" ).css( {"position":"absolute","top":"70%","left":"20%", "width":"60%" , "height":"20%", "font-size":"1.2em", "font-weight": "semibold","color":"#FFFFFF","background-color":"#276FF5","border":"0px solid rgb(88,151,19)","border-radius":"10px","padding":"0px", "padding-left":"0px", "padding-right":"0px", "box-shadow":"2px 2px 5px #888888", "text-align":"center"});

		// Object.ProfileObj.Initialize("ProfileObl1", 5 , 10 , "WorkArea_Div"+Object.Index , 80 , 80 , 1, Object.AssociatedData, Object);
		
	// Begin Event Handlers
	
		$( "#PreviousCasesButton" ).on('mouseover',function(){ $( this ).css( {"background-color": "#0B197D","border":"1px solid rgb(145,141,2)", "color": "#FFFFFF","box-shadow":"0px 0px 10px #333333"}); });
		$( "#PreviousCasesButton" ).on('mouseout',function(){ $( this ).css( {"background-color": "#276FF5","border":"0px solid rgb(145,141,2)", "color": "#FFFFFF","box-shadow":"0px 0px 10px #999999"}); });

		$( "#ViewProfileButton" ).on('mouseover',function(){ $( this ).css( {"background-color": "#0B197D","border":"1px solid rgb(145,141,2)", "color": "#FFFFFF","box-shadow":"0px 0px 10px #333333"}); });
		$( "#ViewProfileButton" ).on('mouseout',function(){ $( this ).css( {"background-color": "#276FF5","border":"0px solid rgb(145,141,2)", "color": "#FFFFFF","box-shadow":"0px 0px 10px #999999"}); });

		$( "#StartCaseButton" ).on('mouseover',function(){ $( this ).css( {"background-color": "#0B197D","border":"1px solid rgb(145,141,2)", "color": "#FFFFFF","box-shadow":"0px 0px 10px #333333"}); });
		$( "#StartCaseButton" ).on('mouseout',function(){ $( this ).css( {"background-color": "#276FF5","border":"0px solid rgb(145,141,2)", "color": "#FFFFFF","box-shadow":"0px 0px 10px #999999"}); });

		$( "#LogoutButton" ).on('mouseover',function(){ $( this ).css( {"background-color": "#276FF5","border":"1px solid rgb(10,10,200)", "color": "#0B197D","box-shadow":"0px 0px 10px #333333"}); });
		$( "#LogoutButton" ).on('mouseout',function(){ $( this ).css( {"background-color": "transparent","border":"0px solid rgb(145,141,2)", "color": "#FFFFFF","box-shadow":"0px 0px 0px #999999"}); });

		// $( "#ChangePasswdButton" ).on('mouseover',function(){ $( this ).css( {"background-color": "#276FF5","border":"1px solid rgb(10,10,200)", "color": "#0B197D","box-shadow":"0px 0px 10px #333333"}); });
		// $( "#ChangePasswdButton" ).on('mouseout',function(){ $( this ).css( {"background-color": "transparent","border":"0px solid rgb(145,141,2)", "color": "#FFFFFF","box-shadow":"0px 0px 0px #999999"}); });

		// $( "#ChangePasswdButton").on('click', function()
		// {
		// 	document.getElementById("WorkArea_Div"+Object.Index).innerHTML = "";
		// 	Object.ChangePass.Initialize("ChangePass", 10 , 10 , "WorkArea_Div"+Object.Index , 80 , 60 , 1.0, Object.AssociatedData, Object);
		// });

		// $( "#StartCaseButton" ).on('click',function()  //edit
		// { 
		// 	document.getElementById("WorkArea_Div"+Object.Index).innerHTML = "";
		// 	// Object.DiagnosisObj.Initialize("MainDO", 10 , 10 , "WorkArea_Div"+Object.Index , 80 , 130 , 1.0, Object.AssociatedData,{},true, Object);
		// 	// tut = new Tutorial();
		// 	// document.getElementById("tutdiv").innerHTML = "";
		// 	// flag = true;
		// 	Object.tutObj = new Tutorial();
		// 	tutorialStack = [];
		// 	Object.tutObj.Initialize( "TutorialObj2",25,30,"WorkArea_Div"+Object.Index,50,40,1.0,true);
		// 	// flag = true;
		// });

		// $( "#PreviousCasesButton" ).on('click',function()  //edit
		// { 
		// 	document.getElementById("WorkArea_Div"+Object.Index).innerHTML = "";
		// 	socket.emit('findCases',Object.AssociatedData);
		// 	socket.on('findCasesOutput',function(data)
		// 	{
		// 		$("#MainCases_Div"+Object.PrevCases.Index).remove();
		// 		console.log("inside pre cases op "+data);
		// 		Object.PrevCases.Initialize("MainPC", 10 , 10 , "WorkArea_Div"+Object.Index , 80 , 130 , 1.0, data[0], data[1]);
		// 		// [{"DPName" : "DocYo1", "PatientName" : "Patient1"}, {"DPName" : "DocYo2", "PatientName" : "Patient2"}]
		// 	});
	
		// });

		// $( "#ViewProfileButton" ).on('click',function()  //edit
		// { 
		// 	document.getElementById("WorkArea_Div"+Object.Index).innerHTML = "";
		// 	Object.ProfileObj.Initialize("ProfileObl1", 5 , 10 , "WorkArea_Div"+Object.Index , 80 , 80 , 1, Object.AssociatedData, Object);
		// 	console.log(Object.AssociatedData);

		// });

		// $( "#LogoutButton" ).on('click',function()  //edit
		// { 
		// 	document.getElementById("AccountPage"+Object.Index).remove();
		// 	// flag = false;
		// 	// Object.ProfileObj.Initialize("ProfileObl1", 5 , 10 , "WorkArea_Div"+Object.Index , 80 , 80 , 1, Object.AssociatedData, Object);
		// 	// console.log(Object.AssociatedData);
		// 	HPage.Initialize( "HPageMain", 55 , 15 , "HomeDiv" , 30 , 70 , 1.0 );

		// });

		// Object.edit = function()
		// {
		// 	$( "#WelcomeName_Div"+Object.Index+"" ).val("Welcome: Dr. "+Object.AssociatedData.First_name+" "+Object.AssociatedData.Second_name);
		// }

	}


}