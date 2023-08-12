import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import your.package.name.dto.YourDTO; // YourDTO 클래스의 패키지와 이름을 정확하게 지정

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MyBatisCRUDTest {
    public static void main(String[] args) {
        String resource = "mybatis-config.xml"; // MyBatis 설정 파일 경로
        InputStream inputStream;
        SqlSessionFactory sqlSessionFactory;

        try {
            // MyBatis 설정 파일 로드
            inputStream = Resources.getResourceAsStream(resource);

            // SqlSessionFactory 생성
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

            // SqlSession 생성
            try (SqlSession session = sqlSessionFactory.openSession()) {
                // CREATE: 새 데이터 추가
                YourDTO newDTO = new YourDTO();
                newDTO.setId(101); // 적절한 값을 설정
                newDTO.setName("New Name"); // 적절한 값을 설정
                session.insert("insertYourDTO", newDTO); // "insertYourDTO"는 매퍼의 ID

                // READ: 모든 데이터 조회
                List<YourDTO> dtoList = session.selectList("selectAllYourDTOs"); // "selectAllYourDTOs"는 매퍼의 ID
                for (YourDTO dto : dtoList) {
                    System.out.println("ID: " + dto.getId() + ", Name: " + dto.getName());
                }

                // UPDATE: 데이터 업데이트
                YourDTO updatedDTO = new YourDTO();
                updatedDTO.setId(101); // 업데이트할 데이터의 ID
                updatedDTO.setName("Updated Name"); // 업데이트할 적절한 값
                session.update("updateYourDTO", updatedDTO); // "updateYourDTO"는 매퍼의 ID

                // DELETE: 데이터 삭제
                int deletedRows = session.delete("deleteYourDTO", 101); // "deleteYourDTO"는 매퍼의 ID
                System.out.println(deletedRows + " row(s) deleted.");

                // 변경된 데이터베이스 상태를 커밋
                session.commit();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
