package cacoethes.auth

import grails.transaction.Transactional
import org.pac4j.core.profile.converter.Converters
import org.pac4j.oauth.profile.OAuth20Profile
import org.pac4j.oauth.profile.google2.Google2Profile
import org.pac4j.oauth.profile.twitter.TwitterProfile
import org.pac4j.springframework.security.authentication.ClientAuthenticationToken
import org.pac4j.springframework.security.authentication.CopyRolesUserDetailsService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException

/**
 * Created by pledbrook on 25/07/15.
 */
class CombinedUserDetailsService implements AuthenticationUserDetailsService<ClientAuthenticationToken> {
    static final String TWITTER_PROVIDER_ID = "twitter"
    static final String GOOGLE_PROVIDER_ID = "google"

    private static final Logger log = LoggerFactory.getLogger(this)

    @Autowired UserService userService

    AuthenticationUserDetailsService<ClientAuthenticationToken> delegateUserDetailsService =
            new CopyRolesUserDetailsService()

    @Override
    UserDetails loadUserDetails(ClientAuthenticationToken token) throws UsernameNotFoundException {
        log.info("Loading user details")

        final profile = token.userProfile
        switch (profile) {
            case TwitterProfile:
            case Google2Profile:
                println "Access token for ${profile.class.simpleName}: ${getAccessToken(profile)}"
                println "ID for ${profile.class.simpleName}: ${profileId(profile)}"
                log.debug "Attaching User instance to the '${profile.id}' ${profile.getClass().simpleName}"
                attachAuthProfileToUser(profile, fetchOrCreateDomainUser(profile))
                break

            default:
                log.debug "Form login for '${profile.id}'. Nothing to do."
        }

        return delegateUserDetailsService.loadUserDetails(token)
    }

    @Transactional
    private attachAuthProfileToUser(OAuth20Profile profile, User user) {
        // The next line is a massive hack to allow me to add a custom
        // attribute to any profile. It relies on Groovy's disdain for
        // scope specifiers as it punches a hole through the `protected`
        // properties and methods.
        profile.attributesDefinition.addAttribute(GormProfileCreator.PERSISTENCE_ID_KEY, Converters.longConverter)

        profile.addAttribute(GormProfileCreator.PERSISTENCE_ID_KEY, user.id)
        log.debug "Attached User instance with ID ${user.id} to the auth profile"

        for (r in user.authorities) {
            profile.addRole(r.authority)
        }

        return profile
    }

    @Transactional
    private User fetchOrCreateDomainUser(OAuth20Profile profile) {
        def user = findOauthUser(profile)?.user
        if (!user) {
            def username = userExists(profile) ? username(profile) + randomDigits() : username(profile)
            user = userService.createUser(username).save(flush: true)
            new OauthId(user: user, accessToken: profileId(profile), provider: providerId(profile)).save()
            UserRole.create user, Role.findByAuthority("ROLE_USER"), true
            log.debug("Created new domain user for '${username}' (${profile.getClass().simpleName}")
        }

        return user
    }

    @Transactional
    private OauthId findOauthUser(TwitterProfile profile) {
        return OauthId.where { accessToken == profileId(profile) && provider == TWITTER_PROVIDER_ID }.get()
    }

    private String profileId(TwitterProfile profile) { return profile.id.toString() }
    private String providerId(TwitterProfile profile) { return TWITTER_PROVIDER_ID }
    private String username(TwitterProfile profile) { return profile.username }

    @Transactional
    private OauthId findOauthUser(Google2Profile profile) {
        return OauthId.where { accessToken == profileId(profile) && provider == GOOGLE_PROVIDER_ID }.get()
    }

    private String profileId(Google2Profile profile) { return profile.id.toString() }
    private String providerId(Google2Profile profile) { return GOOGLE_PROVIDER_ID }
    private String username(Google2Profile profile) { return profile.email }

    private String getAccessToken(OAuth20Profile profile) { return profile.accessToken }

    private boolean userExists(OAuth20Profile profile) { return User.countByUsername(username(profile)) > 0 }
    private String randomDigits() { return (new Random().nextInt(899) + 100).toString() }
}
