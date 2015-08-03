<!doctype html>
<html>
  <head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'submission.label', default: 'Submission')}" />
    <app:hasRole name="ROLE_ADMIN">
      <g:set var="pageTitle" value="All Talk Submissions"/>
    </app:hasRole>
    <app:notHasRole name="ROLE_ADMIN">
      <g:set var="pageTitle" value="Your Talk Submissions"/>
    </app:notHasRole>
    <title>${pageTitle}</title>
  </head>
  <body>
    <a href="#list-submission" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div class="nav" role="navigation">
      <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
        <app:hasRole name="ROLE_ADMIN">
          <li><g:link action="schedule">Schedule</g:link></li>
          <li><g:link controller="profile" action="acceptedExpenses" params="[forYear: 2012]">Expenses</g:link></li>
          <li><g:link controller="profile" action="acceptedEmails" params="[forYear: 2012]">Accepted Email Addresses</g:link></li>
        </app:hasRole>
      </ul>
    </div>
    <div id="list-submission" class="content scaffold-list" role="main">
      <h1>${pageTitle}</h1>
      <g:if test="${flash.message}">
      <div class="message" role="status">${flash.message}</div>
      </g:if>
      <p><em>Submission deadline:&nbsp;&nbsp; <strong><g:formatDate date="${deadline}" format="dd MMM yyyy"/></strong>&nbsp; 00:00 UTC&nbsp;&nbsp;&nbsp; (<g:countdown id="conf-countdown" date="${deadline}"/>)</em></p>
      <app:loggedIn>
      <p>We're putting on two days of expert lead talks by the champions of the Groovy & Grails world
      such as core team members Graeme Rocher and Guillaume Laforge. The eXchange presents an unmissable
      opportunity to engage with the leading experts and fellow enthusiasts as well as a unique opportunity
      to learn the latest innovations and practices.</p>
      <p>We're looking for all types of talks for this conference, including beginner ones, advanced techniques,
      and real world usage.</p>
      <p>All talks are planned to be 45 minutes (aim to talk for 35 to 40 minutes and 5 to 10 minutes for questions ).</p>
      <p>Selection will be conducted by Skills Matter & the Programme Committee on the basis of making this
      a varied and valuable event. All speakers will receive a ticket to the conference plus another one to
      give away to a guest.
      </app:loggedIn>
      <table>
        <thead>
          <tr>
          
            <g:sortableColumn property="title" title="${message(code: 'submission.title.label', default: 'Title')}" />
          
            <g:sortableColumn property="accepted" title="${message(code: 'submission.accepted.label', default: 'Accepted')}" />
          
            <g:sortableColumn property="schedule" title="${message(code: 'submission.schedule.label', default: 'Schedule')}" />
          
            <app:hasRole name="ROLE_ADMIN">
            <th><g:message code="submission.user.label" default="User" /></th>
            </app:hasRole>
          
          </tr>
        </thead>
        <tbody>
        <g:each in="${submissionInstanceList}" status="i" var="submissionInstance">
          <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
          
            <td><g:link action="show" id="${submissionInstance.id}">${fieldValue(bean: submissionInstance, field: "title")}</g:link></td>
          
            <td>
              <g:if test="${submissionInstance.accepted == null}"><em>Pending</em></g:if>
              <g:else>${submissionInstance.accepted ? 'Yes' : 'No'}</g:else>
            </td>
          
            <td>${submissionInstance.assignment?.slot} - ${submissionInstance.assignment?.track?.name?.encodeAsHTML()}</td>
          
            <app:hasRole name="ROLE_ADMIN">
            <g:set var="currProfile" value="${submissionInstance?.user?.profile}"/>
            <td><g:link controller="profile" action="show" id="${currProfile?.id}">${currProfile?.name}</g:link></td>
            </app:hasRole>
          
          </tr>
        </g:each>
        </tbody>
      </table>
      <div class="pagination">
        <g:paginate total="${submissionInstanceTotal}" />
      </div>
    </div>
  </body>
</html>
