package study.collector.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.collector.entity.BookMarkTable;

import java.util.List;

public interface BookMarkTableRepository extends JpaRepository<BookMarkTable, Long> {
    List<BookMarkTable> findByUserId(Long userId);
}
