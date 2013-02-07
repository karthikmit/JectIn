<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inject innovation into web</title>
        <link href="//netdna.bootstrapcdn.com/twitter-bootstrap/2.2.1/css/bootstrap-combined.min.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="resources/JectInStyle.css">
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
        <script src="//code.jquery.com/ui/1.10.0/jquery-ui.js"></script>
		<script src="//crypto-js.googlecode.com/svn/tags/3.1/build/rollups/aes.js"></script>
		
		<script>
			var isValidUrl = function(url) {
				url = url.trim();
				if(url.length == 0) {
					return false;
				}
				
				return true;
			};
			
			urlValidate = function (url) {
				
				var isValid = isValidUrl(url);
				if(false == isValid) {
					var errorPart = document.getElementById("errordiv");
					errorPart.innerHTML = "<p style=\"color:red\">Enter a Valid URL!</p>";
				} else if(url.length < 21) {
					var errorPart = document.getElementById("errordiv");
					errorPart.innerHTML = "<p style=\"color:red\">Too little to minify, sorry :( </p>";
					isValid = false;
				}
				return isValid;
			}
			$(document).ready(function() {
				
				var count = 0;

				$("#aboutsafestoredesc").hide();
				$("#howtosafestoredesc").hide();

				var encrypt = function(plaintext, masterkey) {
				
					var encrypted = CryptoJS.AES.encrypt(plaintext, masterkey);	
					return encrypted;				
				};
				
				$("#addNewValueToEncrypt").live("click", function(){
					count++;
					var newValueId = "value" + count;
					var newKeyId = "key" + count;
					var valuePartHtml = '<input class="input-xlarge" id="' + newValueId + 
										'" type="text" placeholder="Enter the value to encrypt ..."></input>';
					var keyPartHtml = '<input class="input-xlarge" id="' + newKeyId + 
										'" type="text" placeholder="Enter the key or description of the value ..."></input>';
					$("#valuesDiv").append('<div class="input-append">' + keyPartHtml + valuePartHtml + '</div>'); 				
				});
				$("#encrypt").live("click", function() {
					var masterkey = $("#masterkey").val();
					masterkey = masterkey.trim();
					var error = "";
					if(masterkey.length == 0) {
						error = "Please do enter a strong master key";
					}
					else if(masterkey.length < 9) {
						error = "Please do enter a key of length, at least 9 characters";
					}
					
					if(error.length > 0) {
						alert(error);
						return;					
					}
					for(var i = 0; i <= count; i++) {
						var keySelector = "#" + "key" + i;
						var valueSelector = "#" + "value" + i;	
						var valueToEncrypt = $(valueSelector).val();
						$(valueSelector).val(encrypt(valueToEncrypt, masterkey));	
						$("#encrypt").attr("disabled", "disabled");
						$("#savetoserver").removeAttr("disabled");			
					}
 				
				});

				$("#savetoserver").live("click",function() {
					var keyValMap = {};
					for(var i = 0; i <= count; i++) {
						var keySelector = "#" + "key" + i;
						var valueSelector = "#" + "value" + i;	
						var value = $(valueSelector).val();
						var key = $(keySelector).val();
						keyValMap[key] = value;
					}		
					var jsonString = JSON.stringify(keyValMap);
                    var sessionGuid = $("sessionguid").val();
                    alert(sessionGuid);
					$.post("safestore/store",{jsonMap : jsonString, sessionId : sessionGuid},  function(data) {
						if(data == "SUCCESS") {
                            $("#jectincontent").html("Successfully Stored");
                        }
                        else {
                            $("#jectincontent").html("Please do sign-in to use this feature");
                        }
					});
				});
				
				
				$("#shortenUrl").live("click", function() {
					
					var url = $("#appendedInputButton").val();
					$("appendedInputButton").val("");
					if(urlValidate(url) == true) {
						$("#errordiv").html("");
						$("#hello").html("");
						$("#hello").html('<br><br><img src="resources/loading.gif"> </img>')
						$.post("in",{FullUrl : url},  function(data) {
							var htmlResult = '<br><br> <div class="alert alert-success"> <h4>New URL generated, successfully :) </h4> </div> <br>' + data;
							$("#hello").html(htmlResult);
						});
					}
					else {
						$("#hello").html("");
					}
				});
			
				$("#homepage").click(function() {
					$("#safestorepage").removeClass("navpillselected");
					$("#minifypage").removeClass("navpillselected");
					
					$.get("users/login",function(data) {
			
						$("#jectincontent").html(data);	
					});
				});
			
				$("#echopage").click(function() {
					
					$.get("echo/DiyonKutty",function(data) {
			
						$("#jectincontent").html(data);	
					});
				});
				
				$("#minifypage").click(function() {
					$("#minifypage").addClass("navpillselected");
					$("#safestorepage").removeClass("navpillselected");
					
					$.get("in/view",function(data) {
			
						$("#jectincontent").html(data);	
					});
				});
				
				$("#contactpage").click(function() {
					
				});
				
				$("#safestorepage").click(function() {
					$("#safestorepage").addClass("navpillselected");
					$("#minifypage").removeClass("navpillselected");
					
					$.get("safestore/view", function(data) {
						
						$("#jectincontent").html(data);
					});
				});
				
				$("#signupbutton").live("click", function() {
					
					$.get("users/signup", function(data) {
						$("#jectincontent").html(data);
					});
				});
				
				$("#loginsubmit").live("click", function() {
					var username = $("#username").val();
					var password = $("#password").val();
					
					$.post("users/login", {UserName: username, PassWord: password}, function(dataJson) {

                        var dataObj = jQuery.parseJSON(dataJson);
                        var friendlyname = dataObj.fname;
                        var sessionId = dataObj.sessionguid;

						if(friendlyname == "FAILPWD") {
							$("#loginerror").html("<p style=\"color:red\">Password doesn't match</p>");
						}
						else if(friendlyname == "FAILUSER") {
							$("#loginerror").html("<p style=\"color:red\">User doesn't exist</p>");
						}
						else {
							$("#logInNotify").html("Hello " + friendlyname);
							$("#jectincontent").html("Successfully logged in");
                            $("#sessionguid").val(sessionId);
						}
					})
				});
				
				$("#signupsubmit").live("click", function() {
					var username = $("#usernamesignup").val();
					var password = $("#passwordsignup").val();
					var friendlyname = $("#friendlyname").val();
					
					$.post("users/signup", {UserName: username, PassWord: password, Name: friendlyname}, function(dataJson) {
                        var dataMap = jQuery.parseJSON(dataJson);
                        var respCode = dataMap.respCode;

                        if(respCode == "SUCCESS") {
							$("#logInNotify").html("Hello " + friendlyname);

                            $.get("users/activate", function(data) {
                                $("#jectincontent").html(data);
                            });
						}
						else {
							$("#signuperror").html("<p style=\"color:red\">User already exists</p>");
						}
							
					})
				});

                $("#activateButton").live("click", function() {
                    var activCode = $("#activationText").val();
                    $.post("users/activate", {ActivationCode:activCode}, function(data) {
                        $("#jectincontent").html(data);
                    });
                });

				$("#addSafeKey").live("click", function() {
					alert("Hello");
				});
				
				$("#aboutsafestore").live("click", function(event) {
					event.preventDefault();
					$("#aboutsafestoredesc").toggle();				
				});
				$("#howtosafestore").live("click", function(event) {
					event.preventDefault();
					$("#howtosafestoredesc").toggle();				
				});
			});
			
		</script>
    </head>
    <body >
    	<div id="wrap">
    
	    	<div class="container-fluid">
	  			<div class="row-fluid">
					<div class="span2">
						<div class="logo" style="margin: 15px 0; padding: 15px;">
							<img src="resources/inject.png" height=100% width=100%></img>
						</div>
						
						<p style="color:blue">Cool Apps, injected into Web</p> <br> <br>
						
						<button id="homepage" class="btn btn-success" type="button">Sign In</button>
					</div>
	    			<div class="span10" id="bodydiv">
	    				<input id="sessionguid" type="hidden" name="sessionid" value="">
	    				<div id="logInNotify">
	    					
	    				</div>
	    				<ul class="nav nav-pills">
	    					<li><a href="#" id="safestorepage"><h5>Safe Store</h5></a> </li>
							<li><a href="#" id="minifypage"><h5>URL Minify</h5></a> </li>
						</ul>
						
						<div class="jectin-content" id="jectincontent">
							<%@ include file="shortenurl.jsp" %>
						</div>
	    			</div>
	 			</div>
			</div>
			<div id="push"></div>
		</div>
		<div id="footer"> 
			<div class="pull-left">
				<a href="mailto:karthik@ject.in"> <h5 style="margin: 5px 0px 0px 50px">Contact Us</h5> </a>
			</div>
		</div> 
    </body>
</html>
