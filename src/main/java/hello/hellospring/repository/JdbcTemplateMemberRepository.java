package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

//jdbcTemplate를 이용한 리포지토리 순수 jdbc보다 더 간단해짐 (순수jdbc의 반복코드를 대부분 제거해주지만 sql은 직접 작성해야됨)
//만들어놓은 통합테스트로 테스트하면 db접근기술을 바꿔도 간단하게 테스트 가능 (이래서 테스트를 잘 작성하는게 중요 그럼 오류찾기도 쉬움)

public class JdbcTemplateMemberRepository implements MemberRepository{
    
    private final JdbcTemplate jdbcTemplate;
    //JdbcTemplate은 직접 di가 안됨 그래서 SpringConfig.java에서 했듯이 application.properties 에서 설정된 db접속정보인 DataSource를 주입해줌
    @Autowired
    public JdbcTemplateMemberRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    @Override
    public Member save(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id"); //이렇게 사전 세팅으로 테이블명, primarykey를 넣어주면 insert문 알아서 만듬

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters)); // db에 insert수행
        member.setId(key.longValue()); //insert수행한 결과물에서 id 뽑아서 member에 넣어줌
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        List<Member> result = jdbcTemplate.query("select * from member where id =?", memberRowMapper(), id); //결과를 memberRowMapper를 통해 Member변수에 매핑해줌, ?에있는건 맨뒤 매개변수인 id를 의미
        return result.stream().findAny(); //결과를 Optional로 바꿔서 반환
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query("select * from member where name =?", memberRowMapper(), name);
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper()); //이건 전체 반환이니 List로 반환
    }

    private RowMapper<Member> memberRowMapper()
    {
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            return member;
        };
    }
}
