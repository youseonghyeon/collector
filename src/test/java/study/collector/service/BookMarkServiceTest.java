package study.collector.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import study.collector.repository.BookMarkRepository;
import study.collector.repository.BookMarkTableRepository;
import study.collector.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@RequiredArgsConstructor
class BookMarkServiceTest {

    private final BookMarkService bookMarkService;
    private final BookMarkTableRepository tableRepository;
    private final BookMarkRepository bookMarkRepository;
    private final UserRepository userRepository;
    private final JPAQueryFactory queryFactory;


    @Test
    void search() {
    }

    @Test
    void createTable() {
    }

    @Test
    void removeTable() {
    }

    @Test
    void create() {
    }

    @Test
    void remove() {
    }

    @Test
    void change() {
    }

    @Test
    void findAndConvBookMark() {
    }
}
