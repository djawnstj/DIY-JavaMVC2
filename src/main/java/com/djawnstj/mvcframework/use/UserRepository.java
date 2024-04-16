package com.djawnstj.mvcframework.use;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class UserRepository {

    private final static ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();

    public static void save(final String id, final String pw) {
        users.put(id, new User(id, pw));
    }

    public static Collection<User> findAll() {
        return users.values();
    }
}
