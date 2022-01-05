package study.collector.dto.bookmarkdto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class BookMarkResponse {
    private Long tableId;
    private String title;
    private List<BookMarkDto> bookmark;

    public BookMarkResponse(Long tableId, String title, List<BookMarkDto> bookmarks) {
        this.tableId = tableId;
        this.title = title;
        this.bookmark = bookmarks;
    }
}

