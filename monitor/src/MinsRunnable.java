import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MinsRunnable implements Runnable{

    private Connection conn;

    private String sql = "SELECT AVG(a.sample_data) AS avg , COUNT(DISTINCT b.sample_time) AS num\n" +
            "FROM \n" +
            "sample a,\n" +
            "sample b\n" +
            "WHERE \n" +
            "DATE_SUB(NOW(),INTERVAL 1 MINUTE) < a.sample_time \n" +
            "AND \n" +
            "DATE_SUB(NOW(),INTERVAL 1 MINUTE) < b.sample_time \n" +
            "AND \n" +
            "b.sample_data BETWEEN 18 AND 22;";

    public void setConn(Connection conn){
        this.conn = conn;
    }

    @Override
    public void run() {
        while(true){
            try{
                Statement stmt = conn.createStatement();

                // 执行SQL
                ResultSet resultSet = stmt.executeQuery(sql);

                if(resultSet.next()){
                    // 获取平均温度
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String d = sdf.format(date);

                    System.out.println("\033[1;" + 34 + "m" + "[1分钟内平均温度]："
                            + resultSet.getDouble("avg") + " ℃，当前时间：" + d + "\033[0m ");

                    // 是否报警
                    if(resultSet.getInt("num") == 0){
                        // 输出彩色字符 白色30 红色31 绿色32 黄色33 蓝色34 紫红色35 青蓝色36
                        System.out.println("\033[1;" + 31 + "m" + "===高温警报！！！已经连续1分钟超出阈值[18℃, 22℃]===" + "\033[0m ");
                    }
                }

                stmt.clearBatch();
                stmt.close();
            } catch (Exception e){
                e.printStackTrace();
            }

            try {
                Thread.sleep(1000*60); // 隔1分钟
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}