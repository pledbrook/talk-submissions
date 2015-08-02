<%@ page import="cacoethes.Profile" %>

<div class="fieldcontain ${hasErrors(bean: profileInstance, field: 'name', 'error')} required">
  <label for="name">
    <g:message code="profile.name.label" default="Name" />
    <span class="required-indicator">*</span>
  </label>
  <g:textField name="name" required="" value="${profileInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: profileInstance, field: 'email', 'error')} required">
  <label for="email">
    <g:message code="profile.email.label" default="Email" />
    <span class="required-indicator">*</span>
  </label>
  <g:textField name="email" required="" value="${profileInstance?.email}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: profileInstance, field: 'twitterId', 'error')} required">
  <label for="twitterId">
    <g:message code="profile.twitterId.label" default="Twitter" />
  </label>
  <g:textField name="twitterId" value="${profileInstance?.twitterId ?: twitterId}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: profileInstance, field: 'organization', 'error')} required">
  <label for="organization">
    <g:message code="profile.organization.label" default="Organization" />
  </label>
  <g:textField name="organization" value="${profileInstance?.organization}"/>
</div>

<div class="fieldinfo">A short bio of yourself, up to 2000 characters. If previously presented at Skills Matter/Droidcon, please include link. Markdown syntax supported.</div>
<div class="fieldcontain ${hasErrors(bean: profileInstance, field: 'bio', 'error')} ">
  <label for="bio">
    <g:message code="profile.bio.label" default="Bio" />
    <span class="required-indicator">*</span>
  </label>
  <g:textArea required="" name="bio" cols="40" rows="5" maxlength="2000" value="${profileInstance?.bio}"/>
</div>

<app:hasRole name="ROLE_ADMIN">
<div class="fieldcontain ${hasErrors(bean: profileInstance, field: 'needTravel', 'error')} ">
  <label for="needTravel">
    <g:message code="profile.travel.label" default="Need travel expenses" />
    
  </label>
  <g:checkBox name="needTravel" value="${profileInstance?.needTravel}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: profileInstance, field: 'needAccommodation', 'error')} ">
  <label for="needAccommodation">
    <g:message code="profile.accommodation.label" default="Need accommodation expenses" />
    
  </label>
  <g:checkBox name="needAccommodation" value="${profileInstance?.needAccommodation}"/>
</div>
</app:hasRole>

<div class="fieldinfo">(city, state and/or country)</div>
<div class="fieldcontain ${hasErrors(bean: profileInstance, field: 'travelFrom', 'error')} ">
  <label for="travelFrom">
    <g:message code="profile.travelfrom.label" default="Location" />
    <span class="required-indicator">*</span>
  </label>
  <input type="text" name="travelFrom" required="" value="${profileInstance?.travelFrom?.encodeAsHTML()}"/>
</div>

<div class="fieldinfo">Any additional notes such as names of co-speakers, special requirements, comments for the review committee, etc. Up to 2000 characters. Markdown syntax supported.</div>
<div class="fieldcontain ${hasErrors(bean: profileInstance, field: 'notes', 'error')} ">
  <label for="notes">
    <g:message code="profile.notes.label" default="Additional notes" />
  </label>
  <g:textArea name="notes" cols="40" rows="5" maxlength="2000" value="${profileInstance?.notes}"/>
</div>

<app:hasRole name="ROLE_ADMIN">
<div class="fieldcontain ${hasErrors(bean: profileInstance, field: 'user', 'error')} required">
  <label for="user">
    <g:message code="profile.user.label" default="User" />
    <span class="required-indicator">*</span>
  </label>
  <g:select id="user" name="user.id" from="${cacoethes.auth.User.list()}" optionKey="id" required="" value="${profileInstance?.user?.id}" class="many-to-one"/>
</div>
</app:hasRole>
