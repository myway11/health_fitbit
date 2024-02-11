package health_fitbit.fitbit;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

//fitbitAPIリファレンス
//https://dev.fitbit.com/build/reference/web-api/body/
public class CreateData {
	//fitbitアクセストークン(発行から8時間で切れる)
	String accessToken = "";

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
				.header("authorization", accessToken)
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
				.header("authorization", accessToken)
				.header("accept", "application/json")
				.bodyValue("")
				.retrieve().bodyToMono(String.class).block();
		return createDate;
	}
}
