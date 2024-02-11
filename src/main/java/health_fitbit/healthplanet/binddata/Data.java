package health_fitbit.healthplanet.binddata;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * ヘルスプラネットから取得したデータを格納するクラス
 */
@lombok.Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {
	@JsonFormat(pattern = "yyyyMMddHHmm")
	private LocalDateTime date;
	//取得した身体データ
	private double keydata;
	//取得するデータのタグ
	//6021 : 体重 (kg)
	//6022 : 体脂肪率 (%)
	private int tag;

	//fitbit登録用の日付
	private String stringdate;
	private String time;
}
