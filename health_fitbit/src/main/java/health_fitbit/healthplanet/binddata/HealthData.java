package health_fitbit.healthplanet.binddata;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HealthData {
	//身体データのリスト
	@JsonProperty("data")
	private List<health_fitbit.healthplanet.binddata.Data> dataList;
}
