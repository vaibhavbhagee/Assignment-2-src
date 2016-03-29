function ComplaintObject()
{
	this.Index = "";
	this.Left = "";
	this.Top = "";
	this.Parent = "";
	this.Width = "";
	this.Height = "";
	this.Opacity = "";
	this.ComplaintJSON = {};
	this.ParentObj = null;

	var Object = this;

	this.Initialize = function(Index, Left , Top , Parent , Width , Height , Opacity, ComplaintJSON, IsEditable, parent)
	{
		this.Index = Index;
		this.Left = Left;
		this.Top = Top;
		this.Parent = Parent;
		this.Width = Width;
		this.Height = Height;
		this.Opacity = Opacity;
		this.IsEditable = IsEditable;
		this.ComplaintJSON = ComplaintJSON;
		this.ParentObj = parent;

		this.Render();
	}

	this.Render = function()
	{
		Object.ComplaintDiv = 
			"<div id='Complaint_Div"+Object.Index+"' style='position: absolute; top: "+Object.Top+"%; left: "+Object.Left+"%; height: "+Object.Height+"%; width: "+Object.Width+"%; opacity: "+Object.Opacity+"; border: 0px solid #000000;background-color:#AFC9FA; border-radius: 50px 0px 50px 0px; overflow: hidden;box-shadow: 2px 2px 20px #333333;'></div>";

		$( "#"+Object.Parent+"" ).append(Object.ComplaintDiv);

		Object.TitleText = "<div id='titletext' style='color:blue;font-size:1.5em;font-family:Garamond;font-weight:bold;text-align:center;position: absolute; left:35%;top:2%;width:30%;height:7%;line-height:200%'>Complaint Details</div>";

		$( "#Complaint_Div"+Object.Index+"" ).append(Object.TitleText);

		Object.ComplaintTitleText = "<div id='ComplaintTitletext' style='color:blue;font-size:1.2em;font-family:Garamond;font-weight:bold;text-align:left;position: absolute; left:5%;top:11%;width:40%;height:7%;line-height:300%'>Title :</div>";

		$( "#Complaint_Div"+Object.Index+"" ).append(Object.ComplaintTitleText);

		Object.ComplaintDescriptionText = "<div id='ComplaintDescriptiontext' style='color:blue;font-size:1.2em;font-family:Garamond;font-weight:bold;text-align:left;position: absolute; left:5%;top:21%;width:40%;height:7%;line-height:300%'>Description :</div>";

		$( "#Complaint_Div"+Object.Index+"" ).append(Object.ComplaintDescriptionText);

		Object.LodgerNameText = "<div id='LodgerNametext' style='color:blue;font-size:1.2em;font-family:Garamond;font-weight:bold;text-align:left;position: absolute; left:5%;top:31%;width:40%;height:7%;line-height:300%'>Lodger By :</div>";

		$( "#Complaint_Div"+Object.Index+"" ).append(Object.LodgerNameText);

		Object.DomainText = "<div id='Domaintext' style='color:blue;font-size:1.2em;font-family:Garamond;font-weight:bold;text-align:left;position: absolute; left:5%;top:41%;width:40%;height:7%;line-height:300%'>Domain :</div>";

		$( "#Complaint_Div"+Object.Index+"" ).append(Object.DomainText);

		Object.TypeText = "<div id='Contact_Info_text' style='color:blue;font-size:1.2em;font-family:Garamond;font-weight:bold;text-align:left;position: absolute; left:5%;top:51%;width:40%;height:7%;line-height:300%'>Complaint Type :</div>";

		$( "#Complaint_Div"+Object.Index+"" ).append(Object.TypeText);

		Object.CurrentLevelText = "<div id='CurrentLeveltext' style='color:blue;font-size:1.2em;font-family:Garamond;font-weight:bold;text-align:left;position: absolute; left:5%;top:61%;width:40%;height:7%;line-height:300%'>Current Authority :</div>";

		$( "#Complaint_Div"+Object.Index+"" ).append(Object.CurrentLevelText);

		Object.CurrentStatusText = "<div id='CurrentStatustext' style='color:blue;font-size:1.2em;font-family:Garamond;font-weight:bold;text-align:left;position: absolute; left:5%;top:71%;width:40%;height:7%;line-height:300%'>Current Status :</div>";

		$( "#Complaint_Div"+Object.Index+"" ).append(Object.CurrentStatusText);

		Object.VotesText = "<div id='Votestext' style='color:blue;font-size:1.2em;font-family:Garamond;font-weight:bold;text-align:left;position: absolute; left:5%;top:81%;width:40%;height:7%;line-height:300%'>Votes :</div>";

		$( "#Complaint_Div"+Object.Index+"" ).append(Object.VotesText);

		//Input Text Boxes HERE

		Object.ComplaintTitleInput = "<input type='text' id='ComplaintTitleInput' spellcheck='false' placeholder='' required/>";

		$( "#Complaint_Div"+Object.Index+"" ).append(Object.ComplaintTitleInput);

		if (!this.IsEditable && this.DetailsJSON != {})
		{
			$( "#ComplaintTitleInput" ).attr("readonly", true);
			$( "#ComplaintTitleInput" ).attr("value", "");
		}

		$( "#ComplaintTitleInput" ).css( {"position":"absolute","top":"11%","left":"30%", "width":"65%" , "height":"7%", "font-size":"1em", "font-weight": "none","color":"#000000","background-color":"rgb(258,258,255)","border":"0px solid rgb(88,151,19)","border-radius":"60px","padding":"0px", "padding-left":"10px", "padding-right":"0px", "box-shadow":"0px 0px 15px #888888"});

		//

		Object.DescriptionInput = "<input type='text' id='DescriptionInput' spellcheck='false' placeholder='' required/>";

		$( "#Complaint_Div"+Object.Index+"" ).append(Object.DescriptionInput);

		if (!this.IsEditable && this.DetailsJSON != {})
		{
			$( "#DescriptionInput" ).attr("readonly", true);
			$( "#DescriptionInput" ).attr("value", "");
		}

		$( "#DescriptionInput" ).css( {"position":"absolute","top":"21%","left":"30%", "width":"65%" , "height":"7%", "font-size":"1em", "font-weight": "none","color":"#000000","background-color":"rgb(258,258,255)","border":"0px solid rgb(88,151,19)","border-radius":"60px","padding":"0px", "padding-left":"10px", "padding-right":"0px", "box-shadow":"0px 0px 15px #888888"});

		//

		Object.LodgerNameInput = "<input type='text' id='LodgerNameInput' spellcheck='false' placeholder='' required/>";

		$( "#Complaint_Div"+Object.Index+"" ).append(Object.LodgerNameInput);

		if (!this.IsEditable && this.DetailsJSON != {})
		{
			$( "#LodgerNameInput" ).attr("readonly", true);
			$( "#LodgerNameInput" ).attr("value", "");
		}

		$( "#LodgerNameInput" ).css( {"position":"absolute","top":"31%","left":"30%", "width":"65%" , "height":"7%", "font-size":"1em", "font-weight": "none","color":"#000000","background-color":"rgb(258,258,255)","border":"0px solid rgb(88,151,19)","border-radius":"60px","padding":"0px", "padding-left":"10px", "padding-right":"0px", "box-shadow":"0px 0px 15px #888888"});

		//

		Object.DomainInput = "<input type='text' id='DomainInput' spellcheck='false' placeholder='' required/>";

		$( "#Complaint_Div"+Object.Index+"" ).append(Object.DomainInput);

		if (!this.IsEditable && this.DetailsJSON != {})
		{
			$( "#DomainInput" ).attr("readonly", true);
			$( "#DomainInput" ).attr("value", "");
		}

		$( "#DomainInput" ).css( {"position":"absolute","top":"41%","left":"30%", "width":"65%" , "height":"7%", "font-size":"1em", "font-weight": "none","color":"#000000","background-color":"rgb(258,258,255)","border":"0px solid rgb(88,151,19)","border-radius":"60px","padding":"0px", "padding-left":"10px", "padding-right":"0px", "box-shadow":"0px 0px 15px #888888"});

		//

		Object.TypeInput = "<input type='text' id='TypeInput' spellcheck='false' placeholder='' required/>";

		$( "#Complaint_Div"+Object.Index+"" ).append(Object.TypeInput);

		if (!this.IsEditable && this.DetailsJSON != {})
		{
			$( "#TypeInput" ).attr("readonly", true);
			$( "#TypeInput" ).attr("value", "");
		}

		$( "#TypeInput" ).css( {"position":"absolute","top":"51%","left":"30%", "width":"65%" , "height":"7%", "font-size":"1em", "font-weight": "none","color":"#000000","background-color":"rgb(258,258,255)","border":"0px solid rgb(88,151,19)","border-radius":"60px","padding":"0px", "padding-left":"10px", "padding-right":"0px", "box-shadow":"0px 0px 15px #888888"});

		//

		Object.CurrentLevelInput = "<input type='text' id='CurrentLevelInput' spellcheck='false' placeholder='' required/>";

		$( "#Complaint_Div"+Object.Index+"" ).append(Object.CurrentLevelInput);

		if (!this.IsEditable && this.DetailsJSON != {})
		{
			$( "#CurrentLevelInput" ).attr("readonly", true);
			$( "#CurrentLevelInput" ).attr("value", "");
		}

		$( "#CurrentLevelInput" ).css( {"position":"absolute","top":"61%","left":"30%", "width":"65%" , "height":"7%", "font-size":"1em", "font-weight": "none","color":"#000000","background-color":"rgb(258,258,255)","border":"0px solid rgb(88,151,19)","border-radius":"60px","padding":"0px", "padding-left":"10px", "padding-right":"0px", "box-shadow":"0px 0px 15px #888888"});

		//

		Object.CurrentStatusInput = "<input type='text' id='CurrentStatusInput' spellcheck='false' placeholder='' required/>";

		$( "#Complaint_Div"+Object.Index+"" ).append(Object.CurrentStatusInput);

		if (!this.IsEditable && this.DetailsJSON != {})
		{
			$( "#CurrentStatusInput" ).attr("readonly", true);
			$( "#CurrentStatusInput" ).attr("value", "");
		}

		$( "#CurrentStatusInput" ).css( {"position":"absolute","top":"71%","left":"30%", "width":"65%" , "height":"7%", "font-size":"1em", "font-weight": "none","color":"#000000","background-color":"rgb(258,258,255)","border":"0px solid rgb(88,151,19)","border-radius":"60px","padding":"0px", "padding-left":"10px", "padding-right":"0px", "box-shadow":"0px 0px 15px #888888"});

		//

		Object.VotesInput = "<input type='text' id='VotesInput' spellcheck='false' placeholder='' required/>";

		$( "#Complaint_Div"+Object.Index+"" ).append(Object.VotesInput);

		if (!this.IsEditable && this.DetailsJSON != {})
		{
			$( "#VotesInput" ).attr("readonly", true);
			$( "#VotesInput" ).attr("value", "");
		}

		$( "#VotesInput" ).css( {"position":"absolute","top":"81%","left":"30%", "width":"65%" , "height":"7%", "font-size":"1em", "font-weight": "none","color":"#000000","background-color":"rgb(258,258,255)","border":"0px solid rgb(88,151,19)","border-radius":"60px","padding":"0px", "padding-left":"10px", "padding-right":"0px", "box-shadow":"0px 0px 15px #888888"});

		//

		Object.DeleteButton = "<input type='button' id='DeleteButton' value='Delete'/>";
		
			$( "#Complaint_Div"+Object.Index+"" ).append(Object.DeleteButton);
			$( "#DeleteButton" ).css( {"position":"absolute","bottom":"2%","left":"60%","height":"7%","width":"20%","background-color":"#276FF5","border":"0px solid rgb(88,151,19)","color":"#FFFFFF","font-size":"1em","border-radius":"5px", "box-shadow":"2px 2px 5px #000000"});			

		$( "#DeleteButton" ).on('mouseover',function(){ $( this ).css( {"background-color": "#0B197D","border":"1px solid rgb(145,141,2)", "color": "#FFFFFF","box-shadow":"0px 0px 10px #333333"}); });
		$( "#DeleteButton" ).on('mouseout',function(){ $( this ).css( {"background-color": "#276FF5","border":"0px solid rgb(145,141,2)", "color": "#FFFFFF","box-shadow":"0px 0px 10px #999999"}); });

		$( "#DeleteButton" ).on('click',function()
		{
			//Add delete functionality here
		});
	}
}