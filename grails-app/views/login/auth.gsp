<head>
  <meta name='layout' content='main' />
  <title><g:message code="springSecurity.login.title" /></title>
  <r:require modules="login"/>
</head>

<body>
  <div id='login'>
    <ul>
      <li>To submit talks for the event, please log in.</li>
      <li>If you don't already have an account, you will be asked to create one.</li>
      <li>If you <strong>submitted last year</strong>, you can link to your previous account by logging in with the OpenID credentials you used then.</li>
    </ul>
    <div class='inner'>
      <g:if test='${flash.message}'>
      <div class='login_message'>${flash.message}</div>
      </g:if>
      <ul class="socialLogins">
        <li class="twitter"><oauth:connect provider="twitter" id="twitter-connect-link">Twitter</oauth:connect></li>
        <li class="google"><oauth:connect provider="google" id="google-connect-link">Google</oauth:connect></li>
      </ul>
      <p class='fheader'>-- or log in as the administrator --</p>
      <form action='${postUrl}' method='POST' id='loginForm' class='cssform' autocomplete='off'>
        <div>
          <label for='password'><g:message code="springSecurity.login.password.label" /></label>
          <input type='hidden' class='text_' name='j_username' value='admin' />
          <input type='password' class='text_' name='j_password' id='password' />
          <input type='submit' value='${message(code: "springSecurity.login.button")}' />
        </div>
      </form>
    </div>
  </div>
<r:script>
$(document).ready(function(){
  $("input[name='j_password']").focus();
});
</r:script>
</body>
