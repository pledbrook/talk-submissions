<head>
  <meta name='layout' content='main' />
  <title>Talk Submission Authentication</title>
  <asset:stylesheet src="auth.css"/>
</head>

<body>
  <div id='login'>
    <ul>
      <li>To submit talks for the event, please log in via your Twitter or Google account. Note that you should always
      log in with the same account each time as they will be treated as different users.</li>
    </ul>
    <div class='inner'>
      <g:if test='${flash.message}'>
      <div class='login_message'>${flash.message}</div>
      </g:if>
      <ul class="socialLogins">
        <li class="twitter"><a href="${twitterUrl}" id="twitter-connect-link">Twitter</a></li>
        <li class="google"><a href="${googleUrl}" id="google-connect-link">Google</a></li>
      </ul>
      <g:if test="${params.showFormLogin}">
      <p class='fheader'>-- or log in with a username and password --</p>
      <form action='${postUrl}' method='POST' id='loginForm' class='cssform' autocomplete='off'>
<%--      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/> --%>
        <div>
          <label for='username'>Username</label>
          <input type='text' class='text_' name='username' value="${username}"/>
        </div>
        <div>
          <label for='password'>Password</label>
          <input type='password' class='text_' name='password' id='password' />
        </div>
        <div>
          <input type='submit' value='Log in' />
        </div>
      </form>
      </g:if>
    </div>
  </div>
<g:javascript>
$(document).ready(function(){
  $("input[name='username']").focus();
});
</g:javascript>
</body>
