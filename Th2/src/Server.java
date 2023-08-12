import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.StringTokenizer;

public class Server {
    public static void main(String[] args) throws IOException {
        DatagramSocket ds = new DatagramSocket(12344);
        // Tạo một đối tượng DatagramSocket để nhận dữ liệu qua giao thức UDP (port 12344)
        byte[] buf;
        DatagramPacket DpReceive;
        DatagramPacket DpSend;

        while (true) {
            buf = new byte[65535];
            // Tạo mảng byte để lưu dữ liệu nhận được
            DpReceive = new DatagramPacket(buf, buf.length);
            // Tạo DatagramPacket để nhận dữ liệu từ Client
            ds.receive(DpReceive);
            // Nhận gói dữ liệu từ Client

            String inp = new String(DpReceive.getData(), DpReceive.getOffset(), DpReceive.getLength());
            // Chuyển đổi mảng byte thành chuỗi để lấy phép tính từ Client
            System.out.println("Phép tính nhận được: " + inp);

            if (inp.equals("bye")) {
                System.out.println("Client gửi lệnh 'bye'.....ĐANG THOÁT");
                break;
            }
            // Nếu Client gửi lệnh 'bye', thoát khỏi vòng lặp và dừng Server

            int result;
            StringTokenizer st = new StringTokenizer(inp);
            // Tách chuỗi phép tính thành số hạng 1, toán tử, và số hạng 2
            int oprnd1 = Integer.parseInt(st.nextToken());
            String operation = st.nextToken();
            int oprnd2 = Integer.parseInt(st.nextToken());

            if (operation.equals("+"))
                result = oprnd1 + oprnd2;
            else if (operation.equals("-"))
                result = oprnd1 - oprnd2;
            else if (operation.equals("*"))
                result = oprnd1 * oprnd2;
            else
                result = oprnd1 / oprnd2;

            System.out.println("Đang gửi kết quả...");
            String res = Integer.toString(result);
            buf = res.getBytes();
            // Chuyển đổi kết quả thành mảng byte để gửi về Client
            int port = DpReceive.getPort();
            // Lấy số cổng của Client
            DpSend = new DatagramPacket(buf, buf.length, InetAddress.getLocalHost(), port);
            // Tạo DatagramPacket để gửi kết quả về Client
            ds.send(DpSend);
            // Gửi kết quả về Client qua cổng đã lấy
        }

        ds.close();
        // Đóng kết nối khi thoát khỏi vòng lặp
    }
}
