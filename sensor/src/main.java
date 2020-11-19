import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class main {
    public static void main(String[] args) throws SQLException, InterruptedException {

        System.out.println(
                "  / ___/___  ____  _________  _____\n" +
                "  \\__ \\/ _ \\/ __ \\/ ___/ __ \\/ ___/\n" +
                " ___/ /  __/ / / (__  ) /_/ / /    \n" +
                "/____/\\___/_/ /_/____/\\____/_/     ");

        // 指定驱动类
        String driver = "com.mysql.cj.jdbc.Driver";

        // 指定数据库信息
        String url = "jdbc:mysql://localhost:3306/test?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String pass = "tj20185584";

        // 创建连接对象
        Connection conn = null;

        // 连接数据库
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, pass);
            if(!conn.isClosed()){
                System.out.println("\033[1;" + 33 + "m" + "张钊铭 20185584 的模拟传感器开始运行，欢迎访问我的blog 515code.com :)"
                        + "\033[0m \n");
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        while (true){
            // 模拟传感器
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String d = sdf.format(date);

            int temp = new Random().nextInt(10) + 15; // 随机温度 20±5

            // 插入语句
            String sql = "insert into sample values(\'" + d + "\'," + temp + ")";

            // 创建执行SQL语句对象
            Statement stmt = conn.createStatement();

            // 执行SQL
            int resultSet = stmt.executeUpdate(sql);

            if(resultSet == 1){
                System.out.println("[收集温度]当前温度：" + temp + " ℃，时间：" + d);
            }

            // 10s间隔
            Thread.sleep(10000);
        }
    }
}
