import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SecsRunnable implements Runnable{

    private Connection conn;

    private String sql = "SELECT * FROM `sample` ORDER BY sample_time DESC LIMIT 1";

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
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String d = sdf.format(date);

                    double temp = resultSet.getDouble("sample_data");
                    int color = temp >= 18 && temp <= 22 ? 32 : 33;

                    System.out.println("\033[1;" + color + "m" + "[实时温度]：" + temp + " ℃，当前时间：" + d + "\033[0m");
                }

                stmt.close();
            } catch (Exception e){
                e.printStackTrace();
            }

            try {
                Thread.sleep(1000*10); // 隔10s打印一次
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
