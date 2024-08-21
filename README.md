# Patika.dev & FT Teknoloji Fullstack Bootcamp Bitirme Projesi

### Proje Konusu:
Online uçak ve otobüs bileti satışı yapılmak istenmektedir. Uygulamanın gereksinimleri
aşağıdaki gibidir.

### Gereksinimler
- Kullanıcılar sisteme kayıt ve login olabilmelidir.
- Kullanıcı kayıt işleminden sonra mail gönderilmelidir.
- Kullanıcılara role atanıp, silinebilir.
- Admin kullanıcısı yeni sefer ekleyebilir, iptal edebilir, toplam bilet satışını, bu satıştan elde edilen toplam ücreti görebilir. (Admin kullanıcıları uygulamadaki bilgileri
görebilir.)
- Kullanıcılar şehir bilgisi, taşıt türü(uçak & otobüs) veya tarih bilgisi ile tüm seferleri
arayabilmelidir.
- Bireysel kullanıcı aynı sefer için en fazla 5 bilet alabilir.
- Bireysel kullanıcı tek bir siparişte en fazla 2 erkek yolcu için bilet alabilir.
- Kurumsal kullanıcı aynı sefer için en fazla 40 bilet alabilir.
- Satın alma işlemi başarılı ise bilet bilgileri kullanıcının telefonuna sms ve varsa
email gönderilmeli.
- Kullancılar sadece kendi bilgilerine ulaşabilir.

### Teknik Gereksinimler
- SMS, mail ve push Notification gönderme işlemleri için sadece Database kayıt
etme işlemi yapılması yeterlidir.(İsteyenler ücretsiz uygulamalar ile gerekli
entegrasyonu yapabilir) Fakat bu işlemler tek bir Servis(uygulama) üzerinden ve
strateji pattern ile yapılmalıdır
- Kullanıcı şifresi SHA-512 algoritmasıyla hashlenerek kaydedilmelidir.
- Microservice mimarisine uygun geliştirilmelidir.
- Performans problemleri oluşmaması için geliştirmeler eklenmelidir.Sistem Kabulleri
- Kullanıcılar bireysel ve kurumsal olabilir.
- SMS, Mail ve Push Notification gönderim işlemleri Asenkron olmalıdır.
- Uçak yolcu kapasitesi: 189
- Otobüs yolcu kapasitesi: 45
- Ödeme şekli sadece Kredi kartı ve Havale / EFT olabilir.
- Ödeme Servisi işlemleri Senkron olmalıdır.

### Kullanılacak Teknolojiler;
- Min Java 11
- Spring Boot
- JUnit5
- RabbitMQ
- PostgreSql / MySql / MongoDb (ihtiyaca göre kullanabilirsiniz)
- Redis
- JWT Token

### Proje Değerlendirmesi;
- Backend projesinin belirtilen kurallara göre doğru çalışır olmalı.(25 Puan)
- Unit Test oranının paket ve class bazıda en az %90 olmalıdır.(Controller ve
Service katmanları için geçerlidir.) (15 Puan)
- Mikroservis mimarisi, pratikleri ve teknolojileri doğru yansıtıyor olmalı(15 Puan)
- NoSQL veya RDBMS teknolojilerinin kullanımı.(20 Puan)
- Loglama ve Exception Handling mekanizmasının kurulması.(10 Puan)
- Dökümantasyon (Readme, Postman Collection, Diagram, Swagger)
hazırlanması.(5 Puan)
- Kodun anlaşılır olması.(package, class yapısı doğru olması ve isimlendirmelerin
anlamlı olması vb)(10 Puan)

