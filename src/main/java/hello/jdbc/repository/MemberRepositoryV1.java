package hello.jdbc.repository;

import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;
//DATA Source 사용
@Slf4j
public class MemberRepositoryV1 {

    private final DataSource dataSource;

    public MemberRepositoryV1(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Member save(Member member) throws SQLException {
        String sql = "insert into member(member_id, money) values(?, ?)";
        //try with resource 구문으로 close까지 호출. 각각 예외가 터져도 순서대로 잘 닫힘
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);){

            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2, member.getMoney());
            pstmt.executeUpdate();
            return member;
        } catch (SQLException e) {
            log.error("dberror");
            throw e;
        }

    }

    public Member findById(String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";
        //try with resource 구문으로 close까지 호출. 각각 예외가 터져도 순서대로 잘 닫힘
        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);){

            pstmt.setString(1, memberId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Member member = new Member();
                    member.setMemberId(rs.getString("member_id"));
                    member.setMoney(rs.getInt("money"));
                    return member;
                } else {
                    throw new NoSuchElementException("member not found memberId=" + memberId);
                }
            }
        } catch (SQLException e) {
            log.error("dberror : ", e);
            throw e;
        }

    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
