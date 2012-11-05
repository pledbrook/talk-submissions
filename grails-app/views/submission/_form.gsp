<div class="fieldinfo">Headline/title of the talk.</div>
<div class="fieldcontain ${hasErrors(bean: submissionInstance, field: 'title', 'error')} required">
  <label for="title">
    <g:message code="submission.title.label" default="Title" />
    <span class="required-indicator">*</span>
  </label>
  <g:textField name="title" required="" value="${submissionInstance?.title}"/>
</div>

<div class="fieldinfo">Talk abstract, up to 1000 characters. Markdown syntax supported.</div>
<div class="fieldcontain ${hasErrors(bean: submissionInstance, field: 'summary', 'error')} required">
  <label for="summary">
    <g:message code="submission.summary.label" default="Summary" />
    <span class="required-indicator">*</span>
  </label>
  <g:textArea name="summary" cols="40" rows="5" maxlength="1000" required="" value="${submissionInstance?.summary}"/>
</div>

<sec:ifAllGranted roles="ROLE_ADMIN">
<div class="fieldcontain ${hasErrors(bean: submissionInstance, field: 'user', 'error')} ">
  <label for="user">
    <g:message code="submission.user.label" default="User" />
    
  </label>
  <g:set var="currProfile" value="${submissionInstance?.user?.profile}"/>
  <g:link controller="profile" action="show" id="${currProfile?.id}">${currProfile?.name}</g:link>
</div>

<div class="fieldcontain ${hasErrors(bean: submissionInstance, field: 'accepted', 'error')} ">
  <label for="accepted">
    <g:message code="submission.accepted.label" default="Accepted" />
  </label>
    
  <g:radioGroup name="accepted" labels="['Undecided', 'Yes', 'No']" values="['undecided', true, false]" value="${submissionInstance.accepted == null ? 'undecided' : submissionInstance.accepted}">
    <span>${it.label} ${it.radio}</span>
  </g:radioGroup>
</div>

<div class="fieldcontain ${hasErrors(bean: assignment, field: 'trackId', 'error')} ">
  <label for="trackId">
    <g:message code="submission.track.label" default="Track" />
    
  </label>
  <g:select name="trackId" optionKey="id" optionValue="name" value="${assignment?.trackId}" from="${allTracks}" noSelection="['': '']" />
</div>

<div class="fieldcontain ${hasErrors(bean: assignment, field: 'slotId', 'error')} ">
  <label for="slotId">
    <g:message code="submission.slot.label" default="Time Slot" />
    
  </label>
  <g:select name="slotId" optionKey="id" value="${assignment?.slotId}" from="${allSlots}" noSelection="['': '']" />
</div>
</sec:ifAllGranted>
      
