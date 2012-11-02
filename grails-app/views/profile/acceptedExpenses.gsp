<!doctype html>
<html>
  <head>
    <meta name="layout" content="main">
    <title>Required speaker expenses</title>
  </head>
  <body>
    <div class="nav" role="navigation">
      <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
      </ul>
    </div>
    <div id="list-submission" class="content scaffold-list" role="main">
      <h1>Required speaker expenses</h1>
      <g:if test="${flash.message}">
      <div class="message" role="status">${flash.message}</div>
      </g:if>
      <table>
        <thead>
          <tr>
            <th>Speaker Name</td>
            <th>Requires Travel</th>
            <th>Requires Accommodation</th>
            <th>Travelling From</th>
          </tr>
        </thead>
        <tbody>
        <g:each in="${profiles}" status="i" var="profile">
          <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
          
            <td><g:link controller="profile" action="show" id="${profile.id}">${fieldValue(bean: profile, field: "name")}</g:link></td>
            <td><g:if test="${profile.needTravel}">✓</g:if></td>
            <td><g:if test="${profile.needAccommodation}">✓</g:if></td>
            <td>${profile.travelFrom?.encodeAsHTML()}</td>
          
          </tr>
        </g:each>
        </tbody>
      </table>
    </div>
  </body>
</html>
