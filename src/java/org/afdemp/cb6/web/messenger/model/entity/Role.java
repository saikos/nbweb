package org.afdemp.cb6.web.messenger.model.entity;

import java.util.Objects;
import org.afdemp.cb6.web.messenger.MessengerException;

public final class Role {
    
    public static final Role USER  = new Role(1, "USER");
    public static final Role ADMIN = new Role(2, "ADMIN");
    
    private final int id;
    private final String name;
    
    private Role(int id, String name) {
        this.id   = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Role other = (Role) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    public static final Role getRoleFor(int id) throws MessengerException {
        if (id == USER.getId()) {
            return USER;
        }
        else if (id == ADMIN.getId()) {
            return ADMIN;
        }
        else {
            throw new MessengerException("Invalid role id: " + id);
        }
    }
}
