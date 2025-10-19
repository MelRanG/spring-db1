package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class UncheckedTest {

    @Test
    void callCatch(){
        Service service = new Service();
        service.callCatch();
    }

    @Test
    void uncheckedThrows() {
        Service service = new Service();
        Assertions.assertThatThrownBy(service::callThrow)
                        .isInstanceOf(MyUncheckedException.class);

    }

    /**
     * RuntimeException 상속받은 예외는 언체크 예외가 된다.
      */
    static class MyUncheckedException extends RuntimeException{
        public MyUncheckedException(String message){
            super(message); //부모 생성자 중 매개변수 생성자를 받는 메소드 호출. 지금은 예외 출력이 그 생성자임
        }
    }

    static class Repository{
        public void call(){
            throw new MyUncheckedException("ex");
        }
    }

    /**
     * UnChecked 예외는
     * 예외를 잡아서 처리하거나 던지지 않아도 된다.
     */
    static class Service{
        Repository repository = new Repository();
        /**
         * 예외 잡아서 처리
         */
        public void callCatch(){
            try {
                repository.call();
            } catch (MyUncheckedException e) {
                log.info("예외 처리",e);
            }
        }
        /**
         * 예외 밖으로 던짐
         */
        public void callThrow(){
            repository.call();

        }
    }
}
