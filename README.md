health_fitbit
-
体組成計[Health Planet](https://www.healthplanet.jp)のデータを[fitbit](https://www.fitbit.com/)に移行させる。
移行させるデータは３か月前～現在の測定データ。
１時間当たり１５０件のリクエストで制限がかかります。

使い方
-
health_fitbitにはHealth PlanetとfitbitのAPIを使用します。
各サイトより登録・設定を行ったのち、必要なデータをプログラムに書き込みメインメソッドを実行します。

## 記入箇所
### token.properties
```java
#フィットビットクライアントID
fitBitClient_id=
#フィットビットリフレッシュトークン
fitBitRefreshToken=
#ヘルスプラネットアクセストークン
healthPlanetToken=
```
