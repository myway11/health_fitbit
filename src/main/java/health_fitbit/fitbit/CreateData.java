package health_fitbit.fitbit;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;

//fitbitAPIリファレンス
//https://dev.fitbit.com/build/reference/web-api/body/
public class CreateData {
	//クライアントID
	String clientId;
	//リフレッシュトークン
	String refresh;
	//fitbitアクセストークン
	String accessToken;

	/**
	 * プロパティファイルを読み込みFitBitのリフレッシュトークンからアクセストークンを取得
	 * @param client
	 */
	public CreateData(WebClient client) {
		//プロパティファイル読み込み
		Properties properties = new Properties();
		try {
			FileInputStream fileInputStream = new FileInputStream("token.properties");
			properties.load(fileInputStream);
			//リフレッシュトークン取得
			refresh = properties.getProperty("fitBitRefreshToken");
			//クライアントID取得
			clientId = properties.getProperty("fitBitClient_id");
		} catch (IOException e) {
			e.printStackTrace();
		}
		//リフレッシュトークン
		JsonNode token = client.post().uri("https://api.fitbit.com/oauth2/token")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.bodyValue(
						"grant_type=refresh_token&client_id=" + clientId + "&refresh_token=" + refresh)
				.retrieve().bodyToMono(JsonNode.class).block();
		//レスポンスのJSONから新しいリフレッシュトークンをプロパティファイル書き込み
		properties.setProperty("fitBitRefreshToken", token.get("refresh_token").toString().replace("\"", ""));
		try {
			properties.store(new FileOutputStream("token.properties"), null);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		//アクセストークンを取得
		accessToken = token.get("access_token").toString().replace("\"", "");
	}

	/**
	 * fitbit体重データ登録
	 * @param client WebClient
	 * @param weight 体重
	 * @param date 日付
	 * @param time 時間
	 */
	public String createWeight(WebClient client, double weight, String date, String time) {
		//URLの生成
		String url = UriComponentsBuilder.fromUriString("https://api.fitbit.com/1/user/-/body/log/weight.json")
				//体重
				.queryParam("weight", weight)
				//測定日
				.queryParam("date", date)
				//測定時間
				.queryParam("time", time)
				.build().toString();
		//リクエストを生成しPOSTメソッドで送信
		String createData = client.post().uri(url)
				.header("authorization", "Bearer " + accessToken)
				.header("accept", "application/json")
				.bodyValue("")
				.retrieve().bodyToMono(String.class).block();
		return createData;
	}

	/**
	 * fitbit体脂肪データ登録
	 * @param client WebClient
	 * @param fat 体脂肪
	 * @param date 日付
	 * @param time 時間
	 */
	public String createFat(WebClient client, double fat, String date, String time) {
		String url = UriComponentsBuilder.fromUriString("https://api.fitbit.com/1/user/-/body/log/fat.json")
				.queryParam("fat", fat)
				.queryParam("date", date)
				.queryParam("time", time)
				.build().toString();
		String createDate = client.post().uri(url)
				.header("authorization", "Bearer " + accessToken)
				.header("accept", "application/json")
				.bodyValue("")
				.retrieve().bodyToMono(String.class).block();
		return createDate;
	}
}
