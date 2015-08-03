<!doctype html>
<html>
  <head>
    <meta name="layout" content="main">
    <title>GGX ${year} Schedule</title>
    <style>
tr td:first-child {
    width: 10%;
}

tr td {
    width: ${Math.floor(90 / trackNames.size())}%;
}
    </style>
  </head>
  <body>
    <div class="nav" role="navigation">
      <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <app:hasRole name="ROLE_ADMIN">
          <li><g:link controller="profile" action="acceptedExpenses" params="[forYear: 2012]">Expenses</g:link></li>
          <li><g:link controller="profile" action="acceptedEmails" params="[forYear: 2012]">Accepted Email Addresses</g:link></li>
        </app:hasRole>
      </ul>
    </div>
    <div id="schedule" class="content" role="main">
      <h1>GGX ${year} Schedule</h1>
      <g:if test="${flash.message}">
      <div class="message" role="status">${flash.message}</div>
      </g:if>
      <g:each in="${schedule}" var="${dayEntry}">
      <h2>Day ${dayEntry.key}</h2>
      <table>
        <thead>
          <tr>
            <th></th>
            <g:each in="${trackNames}" var="${track}">
            <th>${track.encodeAsHTML()}</th>
            </g:each>
          </tr>
        </thead>
        <tbody>
        <g:each in="${dayEntry.value}" status="i" var="slotEntry">
          <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
          
            <td>Slot ${slotEntry.key}</td>
          
            <g:if test="${!slotEntry.value.every { it } }">
            <td colspan="${trackNames.size()}">
              <g:set var="currentTalk" value="${slotEntry.value.find { it }.talk }"/>
              <g:link controller="submission" action="show" id="${currentTalk?.id}">${currentTalk?.title}</g:link>
            </td>
            </g:if>
            <g:else> 
            <g:each in="${slotEntry.value}" var="assignment">
            <td><g:link controller="submission" action="show" id="${assignment?.talk?.id}">${assignment?.talk?.title}</g:link></td>
            </g:each>
            </g:else>
          </tr>
        </g:each>
        </tbody>
      </table>
      </g:each>
    </div>
  </body>
</html>

