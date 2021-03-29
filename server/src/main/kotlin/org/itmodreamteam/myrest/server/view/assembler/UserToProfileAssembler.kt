package org.itmodreamteam.myrest.server.view.assembler

import org.itmodreamteam.myrest.server.model.user.User
import org.itmodreamteam.myrest.shared.user.Profile
import org.springframework.stereotype.Component

@Component
class UserToProfileAssembler : ModelViewAssembler<User, Profile> {
    override fun toView(model: User): Profile {
        return Profile(model.id, model.firstName, model.lastName, model.enabled, model.locked, model.role)
    }
}