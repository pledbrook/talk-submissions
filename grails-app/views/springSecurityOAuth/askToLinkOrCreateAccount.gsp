<head>
    <meta name='layout' content='main'/>
    <title>Create or Link Account</title>
    <style type="text/css">
    fieldset {
        border: 1px solid green;
        padding: 1em;
        font: 80%/1 sans-serif;
    }

    fieldset legend {
        padding: 0.2em 0.5em;
        border: 1px solid green;
        color: green;
        font-weight: bold;
        font-size: 90%;
        text-align: right;
    }

    fieldset label {
        float: left;
        width: 25%;
        margin-top: 5px;
        margin-right: 0.5em;
        padding-top: 0.2em;
        text-align: right;
        font-weight: bold;
    }

    fieldset input[type="submit"] {
        float: right;
        background: #F0F0F0;
        cursor: pointer;
    }

    fieldset br {
        margin-top: 10px;
    }
    </style>

<r:script>

function loginWithGoogle() {
    $('#openIdLogin input[type="text"]').val("https://www.google.com/accounts/o8/id");
    $('#openIdLogin input[type="submit"]').attr('disabled', 'disabled');
    $('#openIdLoginForm').submit();
}
</r:script>
</head>

<body>

<div class='body' style="padding: 15px;">
    <g:if test='${flash.error}'>
        <div class="errors">${flash.error}</div>
    </g:if>

    <h4><g:message code="springSecurity.oauth.registration.link.not.exists" default="No user was found with this account." args="[session.springSecurityOAuthToken.providerName]"/></h4>
    <br/>

    <g:hasErrors bean="${createAccountCommand}">
    <div class="errors">
        <g:renderErrors bean="${createAccountCommand}" as="list"/>
    </div>
    </g:hasErrors>

    <g:form action="createAccount" method="post" autocomplete="off">
        <fieldset>
            <legend><g:message code="springSecurity.oauth.registration.create.legend" default="Create a new account"/></legend>
            <div class="fieldcontain ${hasErrors(bean: createAccountCommand, field: 'username', 'error')} ">
                <label for='username'><g:message code="OAuthCreateAccountCommand.username.label" default="Username"/>:</label>
                <g:textField name='username' value='${createAccountCommand?.username}'/>
            </div>
            <div class="fieldcontain ${hasErrors(bean: createAccountCommand, field: 'password1', 'error')} ">
                <label for='password1'><g:message code="OAuthCreateAccountCommand.password1.label" default="Password"/>:</label>
                <g:passwordField name='password1' value='${createAccountCommand?.password1}'/>
            </div>
            <div class="fieldcontain ${hasErrors(bean: createAccountCommand, field: 'password2', 'error')} ">
                <label for='password2'><g:message code="OAuthCreateAccountCommand.password2.label" default="Password re-type"/>:</label>
                <g:passwordField name='password2' value='${createAccountCommand?.password2}'/>
            </div>
            <g:submitButton name="${message(code: 'springSecurity.oauth.registration.create.button', default: 'Create')}"/>
        </fieldset>
    </g:form>

    <br/>

    <g:hasErrors bean="${linkAccountCommand}">
    <div class="errors">
        <g:renderErrors bean="${linkAccountCommand}" as="list"/>
    </div>
    </g:hasErrors>

    <g:form action="linkAccount" method="post" autocomplete="off">
        <fieldset>
            <legend><g:message code="springSecurity.oauth.registration.login.legend" default="Link to an existing account"/></legend>
            <div class="fieldcontain ${hasErrors(bean: linkAccountCommand, field: 'username', 'error')} ">
                <label for='username'><g:message code="OAuthLinkAccountCommand.username.label" default="Username"/>:</label>
                <g:textField name='username' value='${linkAccountCommand?.username}'/>
            </div>
            <div class="fieldcontain ${hasErrors(bean: linkAccountCommand, field: 'password', 'error')} ">
                <label for='password'><g:message code="OAuthLinkAccountCommand.password.label" default="Password"/>:</label>
                <g:passwordField name='password' value='${linkAccountCommand?.password}'/>
            </div>
            <g:submitButton name="${message(code: 'springSecurity.oauth.registration.login.button', default: 'Login')}"/>
        </fieldset>
    </g:form>

    <form action='${openIdPostUrl}' method='POST' autocomplete='off' name='openIdLoginForm' id="openIdLoginForm">
        <fieldset id="openIdLogin">
            <legend><g:message code="springSecurity.oauth.link.openid.legend" default="Link to an existing OpenID account"/></legend>
            <div class="fieldcontain ${hasErrors(bean: linkAccountCommand, field: openidIdentifier, 'error')} ">
                <label for='${openidIdentifier}'><g:message code="OAuthLinkAccountCommand.openidUrl.label" default="OpenID"/>:</label>
                <g:textField name="${openidIdentifier}" value="${linkAccountCommand?.getProperty(openidIdentifier)}"/>
            </div>
            <div>
                <g:submitButton name="${message(code: 'springSecurity.oauth.registration.login.button', default: 'Login')}"/>
                <div style="margin-left: 25%;">or sign in with: <a href="#" onclick="loginWithGoogle(); false;">Google</a></div>
            </div>
        </fieldset>
    </form>

    <br/>

    <g:link controller="login" action="auth"><g:message code="springSecurity.oauth.registration.back" default="Back to login page"/></g:link>
</div>

</body>
