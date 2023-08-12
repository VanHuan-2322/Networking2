import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Client {
    public static void main(String args[]) throws IOException {
        Scanner sc = new Scanner(System.in);
        // Khởi tạo một đối tượng Scanner để đọc đầu vào từ người dùng
        DatagramSocket ds = new DatagramSocket();
        // Tạo đối tượng DatagramSocket để gửi và nhận dữ liệu qua giao thức UDP
        InetAddress ip = InetAddress.getLocalHost();
        // Lấy địa chỉ IP localhost
        byte[] buf;

        while (true) {
            System.out.print("Nhập phép tính theo định dạng:");
            System.out.println("'số_hạng_1 toán_tử số_hạng_2'");
            String inp = sc.nextLine();
            // Đọc phép tính từ người dùng

            buf = inp.getBytes();
            // Chuyển đổi chuỗi phép tính thành mảng byte để gửi đi

            DatagramPacket DpSend = new DatagramPacket(buf, buf.length, ip, 12344);
            // Tạo DatagramPacket để gửi dữ liệu đến máy chủ (port 12344)

            ds.send(DpSend);
            // Gửi gói dữ liệu đi

            if (inp.equals("bye"))
                break;
            // Nếu người dùng nhập "bye" thì thoát khỏi vòng lặp

            buf = new byte[65535];
            // Reset lại kích thước mảng byte để nhận dữ liệu mới
            DatagramPacket DpReceive = new DatagramPacket(buf, buf.length);
            // Tạo DatagramPacket để nhận dữ liệu từ máy chủ
            ds.receive(DpReceive);
            // Nhận gói dữ liệu từ máy chủ

            String result = new String(DpReceive.getData(), DpReceive.getOffset(), DpReceive.getLength());
            System.out.println("Kết quả = " + result);
            // Hiển thị kết quả tính toán từ máy chủ
        }

        ds.close();
        // Đóng kết nối sau khi kết thúc vòng lặp
    }
}

