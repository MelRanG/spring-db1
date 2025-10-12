package hello.jdbc.connection;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.assertj.core.api.Assertions.*;

@Slf4j
class DBConnectionUtilTest {

    @Test
    void connection()  {
        try {
            Connection connection = DBConnectionUtil.getConnection();
            assertThat(connection).isNotNull();
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }
}
