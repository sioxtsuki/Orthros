package com.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import com.utility.Constants;


public class TcpClient// implements Runnable
{
    //接続先ホスト名(今回はローカルホスト)
    //ここにサーバのIPアドレスを指定します．普通はハードコーディングせずに外部入力から設定します．
    //例)　192.xxx.yyy.z --> "192.xxx.yyy.z"
    public String host;
    public void setHost(String host) {
		this.host = host;
	}
    //接続先ポート番号(サーバー側で設定したものと同じもの)
    //ポート番号をクライアントとサーバ間で一致させてないと通信できませんよ！
    public int port;
    public void setPort(int port) {
		this.port = port;
	}

    //+------------------------------------------
	//| Runメソッドの実装
    //+------------------------------------------
    public Constants.PROCESS_TYPE run(String command, ArrayList<String> beans)
    {
    	//ArrayList<RateBeans> rates = new ArrayList<RateBeans>();
		Socket socket = null;//ソケット

		Constants.PROCESS_TYPE  pt_type = Constants.PROCESS_TYPE .PT_SUCCESS;
		int count = 0;

		try {
			ArrayList<String> list = new ArrayList<String>();

			socket = new Socket(this.host , this.port); //接続

			OutputStream os = socket.getOutputStream();

			InputStream sok_in = socket.getInputStream();
			InputStreamReader sok_isr = new InputStreamReader(sok_in);
			BufferedReader sok_br = new BufferedReader(sok_isr);

			//String send = "RATECHK MASTER=mt4awk113|";	//キー1行入力
			os.write(command.getBytes());//送信

			while(true)
			{
				String recive = sok_br.readLine();

				if (recive == null) break;

				if (count == 0) // 最初のデータの場合
				{
					if (recive.equals("Success") == false) // エラーの場合は処理中断
					{
						pt_type = Constants.PROCESS_TYPE.PT_ERROR;
						break;
					}
					count++;
					continue;
				}

				String values[] = recive.split("\\|");

				beans.add(recive);
				/*
				// ２件目以降のデータの場合
				String values[] = recive.split("\\|");

				if (values.length >= 4)
				{
					// ブランクが存在するため、リストに洗い替え
					list.clear();
					for (String val : values)
					{
						if (val.toString().trim().isEmpty() == true) continue;
						list.add(val);
					}

					// オブジェクトへ洗い替え
					OrderBeans bean = new OrderBeans();
					bean.setServer(list.get(0).toString());
					bean.setSymbol(list.get(1).toString());
					bean.setCmd(list.get(2).toString());
					bean.setLot(Double.parseDouble(list.get(3).toString()));

					beans.add(bean);
				}*/

				count++;
			}
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
			pt_type = Constants.PROCESS_TYPE.PT_NETWORK_ERROR;
		}

		finally
		{
			try
			{
				if (socket != null)
					socket.close();
			}
			catch (IOException e)
			{
				// TODO 自動生成された catch ブロック
				System.out.println(e.toString());
				pt_type = Constants.PROCESS_TYPE.PT_EXCEPTION_ERROR;
			}
		}

    	return pt_type;
    }
}