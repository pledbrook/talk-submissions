
<%@ page import="cacoethes.Profile" %>
<!doctype html>
<html>
  <head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'profile.label', default: 'Profile')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
  </head>
  <body>
    <a href="#show-profile" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div class="nav" role="navigation">
      <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <sec:ifAllGranted roles="ROLE_ADMIN">
        <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
        </sec:ifAllGranted>
      </ul>
    </div>
    <div id="show-profile" class="content scaffold-show" role="main">
      <h1><g:message code="default.show.label" args="[entityName]" /></h1>
      <g:if test="${flash.message}">
      <div class="message" role="status">${flash.message}</div>
      </g:if>
      <ol class="property-list profile">
      
        <g:if test="${profileInstance?.name}">
        <li class="fieldcontain">
          <span id="name-label" class="property-label"><g:message code="profile.name.label" default="Name" /></span>
          
            <span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${profileInstance}" field="name"/></span>
          
        </li>
        </g:if>
      
        <g:if test="${profileInstance?.email}">
        <li class="fieldcontain">
          <span id="email-label" class="property-label"><g:message code="profile.email.label" default="Email" /></span>
          
            <span class="property-value" aria-labelledby="email-label"><g:fieldValue bean="${profileInstance}" field="email"/></span>
          
        </li>
        </g:if>
      
        <g:if test="${profileInstance?.bio}">
        <li class="fieldcontain">
          <span id="bio-label" class="property-label"><g:message code="profile.bio.label" default="Bio" /></span>
          
            <span class="property-value" aria-labelledby="bio-label"><g:fieldValue bean="${profileInstance}" field="bio"/></span>
          
        </li>
        </g:if>
      
        <sec:ifAllGranted roles="ROLE_ADMIN">
        <g:if test="${profileInstance?.user}">
        <li class="fieldcontain">
          <span id="user-label" class="property-label"><g:message code="profile.user.label" default="User" /></span>
          
            <span class="property-value" aria-labelledby="user-label"><g:link controller="user" action="show" id="${profileInstance?.user?.id}">${profileInstance?.user?.encodeAsHTML()}</g:link></span>
          
        </li>
        </g:if>
        </sec:ifAllGranted>
      
      </ol>
      <g:form>
        <fieldset class="buttons">
          <g:hiddenField name="id" value="${profileInstance?.id}" />
          <g:link class="edit" action="edit" id="${profileInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
          <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
        </fieldset>
      </g:form>
    </div>
  </body>
</html>
