package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


public class AuthorDaoImpl implements AuthorDao{

    private final JdbcTemplate jdbcTemplate;

    public AuthorDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Author getById(Long id) {
        return jdbcTemplate.queryForObject("select * from author where id = ?", getAuthorMapper(), id);
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        return jdbcTemplate.queryForObject("select * from author where first_name = ? and last_name = ?", getAuthorMapper(), firstName, lastName);
    }


    @Override
    public Author saveNewAuthor(Author author) {
        jdbcTemplate.update("insert into author (first_name, last_name) values(?,?)", author.getFirstName(), author.getLastName());
        Long id = jdbcTemplate.queryForObject("select last_insert_id()", Long.class);
        return getById(id);
    }

    @Override
    public Author updateAuthor(Author author) {
        jdbcTemplate.update("update author set first_name = ?, last_name = ? where id = ?", author.getFirstName(), author.getLastName(), author.getId());
        return getById(author.getId());
    }

    @Override
    public void deleteAuthorById(Long id) {
        jdbcTemplate.update("delete from author where id = ?", id);
    }

    private RowMapper<Author> getAuthorMapper(){
        return new AuthorMapper();
    }
}
