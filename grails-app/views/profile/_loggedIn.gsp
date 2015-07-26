<app:loggedIn>
    <g:if test="${currentUser.profile}">
    <g:link controller="profile" action="show" id="${currentUser.profile.id}">${currentUser.profile.name}</g:link>
    (<g:link uri="/logout">log out</g:link>)
    </g:if>
    <g:else>
    <g:link uri="/logout">log out</g:link>
    </g:else>
</app:loggedIn>

