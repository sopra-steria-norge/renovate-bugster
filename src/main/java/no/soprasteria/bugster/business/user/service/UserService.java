package no.soprasteria.bugster.business.user.service;

import no.soprasteria.bugster.infrastructure.db.repository.UserRepository;

public class UserService {

    private UserRepository repository;

    UserService(UserRepository repository) {
        this.repository = repository;
    }
}
