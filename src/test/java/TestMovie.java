import com.burgleaf.wechatser.service.impl.ReplyMessageServiceImpl;

/**
 * Created by wangbin on 14-3-2.
 */
public class TestMovie {

    public static void main(String[] args) {
        ReplyMessageServiceImpl ReplyMessageServiceImpl = new  ReplyMessageServiceImpl();


        System.out.println(ReplyMessageServiceImpl.queryDoubanMovie("天下无贼"));
    }
}
