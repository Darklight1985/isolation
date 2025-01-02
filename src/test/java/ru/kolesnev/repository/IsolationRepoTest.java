package ru.kolesnev.repository;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import ru.kolesnev.domain.Isolation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@QuarkusTest
@Transactional
@DisplayName("Тесты для методов класса IsolationRepository")
public class IsolationRepoTest {

    @Inject
    IsolationRepository isolationRepository;

    private static final String MARK = "Mark";

    @BeforeEach
    void init() {
        Isolation isolation = Isolation.builder()
                .mark(MARK)
                .build();
        isolationRepository.save(isolation);
    }

    @AfterEach
    void end() {
        isolationRepository.deleteAll();
    }

    @Test
    @DisplayName("Если проверяем сколько сущностей в репозитории, то ответ получаем верный.")
    public void testIsolation() {
        Assertions.assertEquals(1, isolationRepository.count());
    }

    @Test
    @DisplayName("Если проверяем наличие изоляции по ее марке, то получаем в ответ true.")
    void existsByMarkTrue() {
        Assertions.assertTrue(isolationRepository.existsByMark(MARK));
    }

    @Test
    @DisplayName("Если запрашиваем сущность по ее идентификатору, то проверяем по марке, что полученная сущность идентична.")
    void existsByMarkTrue2() {
        List<Isolation> list = isolationRepository.findListAll();
        Assertions.assertEquals(1, list.size());
        Isolation isolation = list.get(0);
        UUID isolationId = isolation.getId();
        Optional<Isolation> isolationOptional = isolationRepository.findById(isolationId);
        Assertions.assertTrue(isolationOptional.isPresent());
        Assertions.assertEquals(MARK, isolationOptional.get().getMark());
    }

    @Test
    @DisplayName("Если удаляем сущность по ее идентификатору, то сущность единтсвенная сущность дейсствительно будет удалена.")
    void existsByMarkTru2e() {
        List<Isolation> list = isolationRepository.findListAll();
        Assertions.assertEquals(1, list.size());
        Isolation isolation = list.get(0);
        UUID isolationId = isolation.getId();
        isolationRepository.deleteById(isolationId);
        Assertions.assertEquals(0, isolationRepository.count());

    }

}
