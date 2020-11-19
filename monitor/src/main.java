import java.sql.*;

public class main {
    public static void main(String[] args) {
        System.out.println("    __  ___            _ __            \n" +
                "   /  |/  /___  ____  (_) /_____  _____\n" +
                "  / /|_/ / __ \\/ __ \\/ / __/ __ \\/ ___/\n" +
                " / /  / / /_/ / / / / / /_/ /_/ / /    \n" +
                "/_/  /_/\\____/_/ /_/_/\\__/\\____/_/     ");

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
                System.out.println("\033[1;" + 33 + "m" + "张钊铭 20185584 的模拟传感器开始运行，欢迎访问我的blog 515code.com :)" + "\033[0m \n");
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        // 多线程
        // 每10s打印一次实时温度
        SecsRunnable secsRunnable = new SecsRunnable();
        secsRunnable.setConn(conn);
        new Thread(secsRunnable).start();

        // 每1分钟打印一次平均温度
        MinsRunnable minsRunnable = new MinsRunnable();
        minsRunnable.setConn(conn);
        new Thread(minsRunnable).start();
    }
}
