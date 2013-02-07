<fieldset>

  <div class="control-group">
    <!-- Username -->
    Are you a new User, then 
    <button id="signupbutton" class="btn btn-info" type="button">Sign Up</button> <br><br>
    <label class="control-label"  for="username">Username</label>
    <div class="controls">
      <input type="text" id="username" name="username" placeholder="Enter your E-Mail id here" 
      	class="input-xlarge"></input>
    </div>
  </div>

  <div class="control-group">
    <!-- Password-->
    <label class="control-label" for="password">Password</label>
    <div class="controls">
      <input type="password" id="password" name="password" placeholder="Enter your password here" 
      	class="input-xlarge"></input>
    </div>
  </div>


  <div class="control-group">
    <!-- Button -->
    <div class="controls">
      <button id="loginsubmit" class="btn btn-success" type="button">Login</button>
    </div>
  </div>
  
  <div id="loginerror">
  </div>
</fieldset>