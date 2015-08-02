<ol class="property-list ${domainClassDesc.propertyName}">
  <g:each in="${domainClassDesc.persistentProperties.sort(comparator)}" var="p">
    <g:if test="${!excludes.contains(p.name)}">
    <li class="fieldcontain">
      <span id="${p.name}-label" class="property-label"><g:message code="${domainClassDesc.propertyName}.${p.name}.label" default="${p.naturalName}" /></span>
      <div class="property-value" aria-labelledby="${p.name}-label">
        <% if (markdownProps.contains(p.name)) { %>
        <%-- Don't split the next line up! Otherwise rogues whitespace will be treated as code block --%>
        <app:mdToHtml><f:display bean="${bean}" property="${p.name}" /></app:mdToHtml>
        <% } else { %>
        <f:display bean="${bean}" property="${p.name}" />
        <% } %>
      </div>
    </li>
    </g:if>
  </g:each>
</ol>
