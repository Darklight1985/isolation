package ru.kolesnev.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import ru.kolesnev.domain.Isolation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class IsolationRepository implements PanacheRepository<Isolation> {

    private final EntityManager em;

    public IsolationRepository(EntityManager em) {
        this.em = em;
    }

    public boolean existsByMark(String mark) {
        return count("mark", mark) > 0;
    }

    public Optional<Isolation> findById(UUID id) {
        return find("id", id).singleResultOptional();
    }

    public void save(Isolation isolation) {
        persist(isolation);
    }

    public void deleteById(UUID id) {
        delete("id", id);
    }

    public List<Isolation> findListAll() {
        return listAll().stream().toList();
    }

    public Optional<Isolation> existsByMark(String mark, UUID isolationId) {
        return Optional.ofNullable((Isolation) em.createNativeQuery("""
                         select iso from isolattion iso
                        where where iso.mark = :mark
                                                    and iso.id <> :isolationId""", Isolation.class)
                .setParameter("isolationId", isolationId)
                .setParameter("mark", mark)
                .getSingleResult());
    }
}
