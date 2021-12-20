package hello.hellospring.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity //jpa가 orm(object relational mapping)을 위해 관리하는 entity를 등록하는 어노테이션
public class Member {

    @Id //primary key 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // db가 알아서 pk를 생성하는 경우는 identity전략 사용 (우리는 db에서 지금 autoincrement로 알아서 생성되고 있음)
    private Long id;


    private String name;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
