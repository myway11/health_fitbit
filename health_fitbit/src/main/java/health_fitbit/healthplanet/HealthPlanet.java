package health_fitbit.healthplanet;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import health_fitbit.healthplanet.binddata.HealthData;

/**
 * ヘルスプラネットからデータを取得するクラス
 * https://www.healthplanet.jp/apis/api.html
 */
public class HealthPlanet {
	String baseUri = "https://www.healthplanet.jp";
	//アクセストークン
	String access_token = "";

	/**
	 * 指定期間内のデータのリストをバインドしバインドクラスを返す
	 * @param client WebClient
	 * @param from 日付(yyyyMMddHHmmss)～
	 * @param to ~日付(yyyyMMddHHmmss)
	 * @return 期間内のデータを格納したクラス
	 */
	public HealthData getHealthData(WebClient client, String from, String to) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		String url = UriComponentsBuilder.fromUriString(baseUri + "/status/innerscan.json")
				.queryParam("access_token", access_token)
				.queryParam("tag", "6021,6022")
				.queryParam("data", 1)
				.queryParam("from", from)
				.queryParam("to", to)
				.build().toString();
		HealthData data = client.get().uri(url).retrieve().bodyToMono(HealthData.class).block();
		return data;
	}

}
