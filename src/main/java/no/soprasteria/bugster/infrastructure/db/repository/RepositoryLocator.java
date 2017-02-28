package no.soprasteria.bugster.infrastructure.db.repository;

public class RepositoryLocator {

    private RepositoryLocator() {
    }

    public static <T extends Repository> T instantiate(Class<T> c) {
        try {
            return c.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new IllegalArgumentException("Failed to instantiate repository.");
        } catch (RuntimeException e) {
            throw new IllegalStateException("");
        }
    }
}
