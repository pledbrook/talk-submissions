package cacoethes.auth

import cacoethes.Profile

class User {

    String username
    String password
    boolean enabled = true
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    static hasOne = [profile: Profile]
    static hasMany = [oauthIds: OauthId, openIds: OpenId]

    static constraints = {
        username blank: false, unique: true
        password blank: false
        profile nullable: true
    }

    static mapping = {
        table '`user`'
        password column: '`password`'
    }

    Set<Role> getAuthorities() {
        UserRole.findAllByUser(this).collect { it.role } as Set
    }
}
