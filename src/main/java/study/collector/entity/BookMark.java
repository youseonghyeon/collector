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
    private String name;
    private String url;
    private String imgUrl;
    private String category;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public BookMark(String name, String url, String img, String category, User user) {
        this.name = name;
        this.url = url;
        this.imgUrl = img;
        this.category = category;
        if (user != null) {
            assignUser(user);
        }
    }

    public void assignUser(User user) {
        this.user = user;
        user.getBookMarks().add(this);
    }

    public void changeBookMark(String name, String url, String imgUrl, String category) {
        this.name = name;
        this.url = url;
        this.imgUrl = imgUrl;
        this.category = category;
    }


}
