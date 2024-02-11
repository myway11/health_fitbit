health_fitbit
-
体組成計[Health Planet](https://www.healthplanet.jp)のデータを[fitbit](https://www.fitbit.com/)に移行させる。

使い方
-
health_fitbitにはHealth PlanetとfitbitのAPIを使用します。
各サイトより登録・設定を行ったのち、必要なデータをプログラムに書き込みメインメソッドを実行します。

## 記入箇所
```java
public class HealthPlanet {
	String baseUri = "https://www.healthplanet.jp";
	//アクセストークン
	String access_token = "";
```
```java
public class CreateData {
	//fitbitアクセストークン(発行から8時間で切れる)
	//Bearerからコピー
	String accessToken = "";

```
```java
//ヘルスプラネットから指定の日付間のデータを取得(最大3か月)
//書式：yyyyMMddHHmmss
	String from = "";
	String to = "";
```
