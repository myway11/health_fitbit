package health_fitbit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import health_fitbit.fitbit.CreateData;
import health_fitbit.healthplanet.HealthPlanet;
import health_fitbit.healthplanet.binddata.Data;
import reactor.core.publisher.Mono;

public class Main {

	public static void main(String[] args) {
		WebClient client = WebClient.builder()
				.filter(logRequest())
				.filter(logResponse())
				.build();

		CreateData createData = new CreateData(client);

		//ヘルスプラネットから指定の日付間のデータを取得(現在～3か月前)
		//期間取得
		DateTimeFormatter time = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime before = now.minusMonths(3);
		String from = before.format(time);
		String to = now.format(time);
		//ヘルスプラネットからデータ取得
		List<Data> dataList = new HealthPlanet().getHealthData(client, from, to).getDataList();
		System.out.println(dataList);
		//体重・体脂肪のデータのリスト
		List<Data> weightList = new ArrayList<Data>();
		List<Data> fatList = new ArrayList<Data>();
		//データの日付を指定の書式に変換するフォーマッター
		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

		System.out.println("ヘルスプラネット" + dataList);
		//取得したデータを体重・体脂肪に仕分け
		dataList.forEach(data -> {
			if (data.getTag() == 6021) {
				//日付データをfitbit登録時の書式に変換し格納
				data.setStringdate(data.getDate().format(DateTimeFormatter.ISO_DATE));
				data.setTime(data.getDate().format(timeFormat));
				weightList.add(data);
			} else {
				data.setStringdate(data.getDate().format(DateTimeFormatter.ISO_DATE));
				data.setTime(data.getDate().format(timeFormat));
				fatList.add(data);
			}
		});
		//体重データをfitbitに登録
		weightList.forEach(weight -> {
			String resultData = createData.createWeight(client, weight.getKeydata(), weight.getStringdate(),
					weight.getTime());
			System.out.println(resultData);
		});
		//体脂肪データをfitbitに登録
		fatList.forEach(fat -> {
			String resultData = createData.createFat(client, fat.getKeydata(), fat.getStringdate(),
					fat.getTime());
			System.out.println(resultData);
		});
	}

	private static ExchangeFilterFunction logRequest() {
		return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
			System.out.println("Request: " + clientRequest.method() + " " + clientRequest.url());
			clientRequest.headers()
					.forEach((name, values) -> values.forEach(value -> System.out.println(name + ": " + value)));
			return Mono.just(clientRequest);
		});
	}

	private static ExchangeFilterFunction logResponse() {
		return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
			System.out.println("Response: " + clientResponse.statusCode());
			clientResponse.headers().asHttpHeaders()
					.forEach((name, values) -> values.forEach(value -> System.out.println(name + ": " + value)));
			return Mono.just(clientResponse);
		});
	}
}
