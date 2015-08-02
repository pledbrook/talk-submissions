
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
        <app:hasRole name="ROLE_ADMIN">
        <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
        </app:hasRole>
      </ul>
    </div>
    <div id="show-profile" class="content scaffold-show" role="main">
      <h1><g:message code="default.show.label" args="[entityName]" /></h1>
      <g:if test="${flash.message}">
      <div class="message" role="status">${flash.message}</div>
      </g:if>
      <app:displayFields bean="${profileInstance}" excludes="["needTravel", "needAccommodation", "user"]"
                         markdownProps="["bio", "notes"]" />
      <g:form resource="${profileInstance}" method="DELETE">
        <fieldset class="buttons">
          <g:link class="edit" action="edit" resource="${profileInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
          <app:hasRole name="ROLE_ADMIN">
          <input class="delete" type="submit" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
          </app:hasRole>
        </fieldset>
      </g:form>
    </div>
  </body>
</html>
