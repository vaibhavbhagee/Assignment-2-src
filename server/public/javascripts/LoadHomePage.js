var login = new LoginObj();

var acctPage = new AccountPageOne(); 
var HPage = new HomePage();

function LoadHomePage()
{
	// login.Initialize( "LoginObj1", 25 , 30 , "HomeDiv" , 50 , 40 , 1.0);
	acctPage.Initialize("acctpage1", 0 , 0 , "HomeDiv" , 100 , 100 , 1, {});
}