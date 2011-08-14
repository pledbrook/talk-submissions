<g:if test="${!currentUser?.profile}"><sec:username/></g:if><g:else><g:link controller="profile" action="show" id="${currentUser.profile.id}">${currentUser.profile.name}</g:link></g:else> (<g:link controller="logout">log out</g:link>)

