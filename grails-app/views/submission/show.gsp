
<%@ page import="cacoethes.Submission" %>
<!doctype html>
<html>
  <head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'submission.label', default: 'Submission')}" />
    <title><g:message code="default.show.label" args="[entityName]" /></title>
  </head>
  <body>
    <a href="#show-submission" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div class="nav" role="navigation">
      <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
      </ul>
    </div>
    <div id="show-submission" class="content scaffold-show" role="main">
      <h1><g:message code="default.show.label" args="[entityName]" /></h1>
      <g:if test="${flash.message}">
      <div class="message" role="status">${flash.message}</div>
      </g:if>
      <ol class="property-list submission">
      
        <g:if test="${submissionInstance?.title}">
        <li class="fieldcontain">
          <span id="title-label" class="property-label"><g:message code="submission.title.label" default="Title" /></span>
          
          <span class="property-value" aria-labelledby="title-label"><g:fieldValue bean="${submissionInstance}" field="title"/></span>
          
        </li>
        </g:if>
      
        <g:if test="${submissionInstance?.summary}">
        <li class="fieldcontain">
          <span id="summary-label" class="property-label"><g:message code="submission.summary.label" default="Summary" /></span>
          
          <span class="property-value" aria-labelledby="summary-label"><markdown:renderHtml><g:fieldValue bean="${submissionInstance}" field="summary"/></markdown:renderHtml></span>
          
        </li>
        </g:if>
      
        <sec:ifAllGranted roles="ROLE_ADMIN">
        <g:if test="${submissionInstance?.user}">
        <li class="fieldcontain">
          <span id="user-label" class="property-label"><g:message code="submission.user.label" default="User" /></span>
          
          <g:set var="currProfile" value="${submissionInstance?.user?.profile}"/>
          <span class="property-value" aria-labelledby="user-label">
            <g:link controller="profile" action="show" id="${currProfile?.id}">${currProfile?.name}</g:link>
          </span>
          
        </li>
        </g:if>
        </sec:ifAllGranted>
      
        <li class="fieldcontain">
          <span id="accepted-label" class="property-label"><g:message code="submission.accepted.label" default="Accepted" /></span>
          
          <span class="property-value" aria-labelledby="accepted-label">
            <g:if test="${submissionInstance.accepted == null}"><em>Pending</em></g:if>
            <g:else>${submissionInstance.accepted ? 'Yes' : 'No'}</g:else>
          </span>
          
        </li>
      
        <li class="fieldcontain">
          <span id="schedule-label" class="property-label"><g:message code="submission.schedule.label" default="Schedule" /></span>
          
          <span class="property-value" aria-labelledby="schedule-label"><g:formatDate date="${submissionInstance?.schedule}" /></span>
          
        </li>
      
      </ol>
      <g:form>
        <fieldset class="buttons">
          <g:hiddenField name="id" value="${submissionInstance?.id}" />
          <g:link class="edit" action="edit" id="${submissionInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
          <g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
        </fieldset>
      </g:form>
    </div>
  </body>
</html>
