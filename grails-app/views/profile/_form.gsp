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

<div class="fieldcontain ${hasErrors(bean: profileInstance, field: 'bio', 'error')} ">
  <label for="bio">
    <g:message code="profile.bio.label" default="Bio" />
    
  </label>
  <g:textArea name="bio" cols="40" rows="5" maxlength="2000" value="${profileInstance?.bio}"/>
</div>

<sec:ifAllGranted roles="ROLE_ADMIN">
<div class="fieldcontain ${hasErrors(bean: profileInstance, field: 'user', 'error')} required">
  <label for="user">
    <g:message code="profile.user.label" default="User" />
    <span class="required-indicator">*</span>
  </label>
  <g:select id="user" name="user.id" from="${cacoethes.auth.User.list()}" optionKey="id" required="" value="${profileInstance?.user?.id}" class="many-to-one"/>
</div>
</sec:ifAllGranted>
