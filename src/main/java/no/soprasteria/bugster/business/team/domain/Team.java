package no.soprasteria.bugster.business.team.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class Team {

    private long id;
    private String name;

    public Team(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return id == team.id &&
                Objects.equal(name, team.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name);
    }
}
