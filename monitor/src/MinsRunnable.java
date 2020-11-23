import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 线程 每分钟计算平均温度与自动报警检测
 * @author https://github.com/BA-NANA
 */
public class MinsRunnable implements Runnable{

    private Connection conn;

    private String AVG_SQL = "SELECT AVG(a.sample_data) AS avg " +
            "FROM sample a " +
            "WHERE DATE_SUB(NOW(),INTERVAL 1 MINUTE) <= a.sample_time;";

    private String WARNING_SQL = "SELECT COUNT(DISTINCT b.sample_time) AS num " +
            "FROM sample b " +
            "WHERE DATE_SUB(NOW(),INTERVAL 1 MINUTE) <= b.sample_time " +
            "AND b.sample_data BETWEEN 18 AND 22";

    public void setConn(Connection conn){
        this.conn = conn;
    }

    @Override
    public void run() {
        while(true){
            try{
                Statement stmt = conn.createStatement();

                // 执行SQL
                ResultSet avg = stmt.executeQuery(AVG_SQL);
                if(avg.next()){
                    // 获取平均温度
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String d = sdf.format(date);

                    System.out.println("\033[1;" + 34 + "m" + "[1分钟内平均温度]："
                            + avg.getDouble("avg") + " ℃，当前时间：" + d + "\033[0m ");
                    avg.close(); // 释放资源
                }

                ResultSet warn = stmt.executeQuery(WARNING_SQL);
                if(warn.next()){
                    // 是否报警
                    if(warn.getInt("num") == 0){
                        // 输出彩色字符 白色30 红色31 绿色32 黄色33 蓝色34 紫红色35 青蓝色36
                        System.out.println("\033[1;" + 31 + "m" + "===温度警报！！！已经连续1分钟超出阈值[18℃, 22℃]===" + "\033[0m ");
                    }
                    warn.close();
                }

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
