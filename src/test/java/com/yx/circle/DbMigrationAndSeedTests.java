package com.yx.circle;

import com.yx.circle.mapper.CircleCategoryMapper;
import com.yx.circle.mapper.CircleMapper;
import com.yx.circle.mapper.CircleMemberMapper;
import com.yx.circle.mapper.PostMapper;
import com.yx.circle.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DbMigrationAndSeedTests {

    private static final int EXPECTED_ROWS = 100;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CircleCategoryMapper circleCategoryMapper;

    @Autowired
    private CircleMapper circleMapper;

    @Autowired
    private CircleMemberMapper circleMemberMapper;

    @Autowired
    private PostMapper postMapper;

    @Test
    void flywayAppliedMigrations() {
        Integer migrations = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM flyway_schema_history WHERE success = 1",
                Integer.class);
        assertEquals(2, migrations);
    }

    @Test
    void seedDataHasExpectedRowCounts() {
        assertEquals(EXPECTED_ROWS, userMapper.selectCount(null));
        assertEquals(EXPECTED_ROWS, circleCategoryMapper.selectCount(null));
        assertEquals(EXPECTED_ROWS, circleMapper.selectCount(null));
        assertEquals(EXPECTED_ROWS, circleMemberMapper.selectCount(null));
        assertEquals(EXPECTED_ROWS, postMapper.selectCount(null));
    }

    @Test
    void jdbcSelectOneWorks() {
        Integer one = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
        assertEquals(1, one);
    }
}
