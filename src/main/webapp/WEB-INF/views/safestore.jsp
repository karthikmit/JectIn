<div id="safestoreframe">
	<h2 style="color:red">Itz not yet fully implemented</h2>
	<a id="aboutsafestore" href="#"><h3>About this app </h3></a>
	<div id="aboutsafestoredesc">
		
		<p> How many times, would we have felt the need for a safe information storage on line? </p>
		<p> This app is an attempt to attack this problem. Select a strong master key, which is used to encrypt your information at the client side itself. And this master key wouldn't be sent or stored at the server. So, whatever info, sent across the wire is encrypted using your master key. And server would encrypt further using your password or some other key to add up additional level of security. And this page is using https to prevent Man in the middle attack. </p>
	</div>
	<a id="howtosafestore" href="#"><h3> How to use this app </h3></a>
	<div id="howtosafestoredesc">
		<p> Key is to identify the value, it can be anything like, "facebook login", "Gmail-password", etc. and key 										wouldn't be encrypted. </p>
		<p>Value can be any important information you want to store corresponding to the key.
			In order to have greater security, if you could do little 											  										modifications, which you only, can interpret or decipher, rather than using a plain one. </p>
		<p>For instance, Minor modifications like adding your birthday at the end of value or changing numbers to 										readable text, etc could be used</p>
	</div>
	
	
		<br><br>
		<div id="valuesDiv">
			<div class="input-append">
				<input class="input-xlarge" id="key0" 
					type="text" placeholder="Enter the key or description of the value ..."></input>
				<input class="input-xlarge" id="value0" 
					type="text" placeholder="Enter the value to encrypt ..."></input>
				<button class="btn" type="button" id="addNewValueToEncrypt">+</button>
			</div>
		</div>
	<div class="input-append">

		<br><br>
		<input class="input-xlarge" id="masterkey" 
			type="password" title="Master Key must be strong one with at least 9 Characters with small, capital and numbers mixed" 					placeholder="Enter your Master key ..."></input>
		<button class="btn" type="button" id="encrypt">Encrypt</button>
		<button class="btn" type="button" id="savetoserver" disabled="disabled">Save</button>
	</div>
</div>