package study.collector.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookMark {

    @Id
    @GeneratedValue
    @Column(name = "bookmark_id")
    private Long id;
    private String url;
    private String img;
    private String category;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public BookMark(String url, String img, String category, User user) {
        this.url = url;
        this.img = img;
        this.category = category;
        if (user != null) {
            assignUser(user);
        }
    }

    public void assignUser(User user) {
        this.user = user;
        user.getBookMarks().add(this);
    }

}
