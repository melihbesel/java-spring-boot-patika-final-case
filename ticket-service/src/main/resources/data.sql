
-- cities
INSERT INTO cities (name, province_id)
SELECT 'Adana', 1
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Adana' AND province_id=1);

INSERT INTO cities (name, province_id)
SELECT 'Adıyaman', 2
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Adıyaman' AND province_id=2);

INSERT INTO cities (name, province_id)
SELECT 'Afyonkarahisar', 3
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Afyonkarahisar' AND province_id=3);

INSERT INTO cities (name, province_id)
SELECT 'Ağrı', 4
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Ağrı' AND province_id=4);

INSERT INTO cities (name, province_id)
SELECT 'Amasya', 5
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Amasya' AND province_id=5);

INSERT INTO cities (name, province_id)
SELECT 'Ankara', 6
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Ankara' AND province_id=6);

INSERT INTO cities (name, province_id)
SELECT 'Antalya', 7
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Antalya' AND province_id=7);

INSERT INTO cities (name, province_id)
SELECT 'Artvin', 8
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Artvin' AND province_id=8);

INSERT INTO cities (name, province_id)
SELECT 'Aydın', 9
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Aydın' AND province_id=9);

INSERT INTO cities (name, province_id)
SELECT 'Balıkesir', 10
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Balıkesir' AND province_id=10);

INSERT INTO cities (name, province_id)
SELECT 'Bilecik', 11
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Bilecik' AND province_id=11);

INSERT INTO cities (name, province_id)
SELECT 'Bingöl', 12
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Bingöl' AND province_id=12);

INSERT INTO cities (name, province_id)
SELECT 'Bitlis', 13
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Bitlis' AND province_id=13);

INSERT INTO cities (name, province_id)
SELECT 'Bolu', 14
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Bolu' AND province_id=14);

INSERT INTO cities (name, province_id)
SELECT 'Burdur', 15
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Burdur' AND province_id=15);

INSERT INTO cities (name, province_id)
SELECT 'Bursa', 16
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Bursa' AND province_id=16);

INSERT INTO cities (name, province_id)
SELECT 'Çanakkale', 17
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Çanakkale' AND province_id=17);

INSERT INTO cities (name, province_id)
SELECT 'Çankırı', 18
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Çankırı' AND province_id=18);

INSERT INTO cities (name, province_id)
SELECT 'Çorum', 19
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Çorum' AND province_id=19);

INSERT INTO cities (name, province_id)
SELECT 'Denizli', 20
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Denizli' AND province_id=20);

INSERT INTO cities (name, province_id)
SELECT 'Diyarbakır', 21
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Diyarbakır' AND province_id=21);

INSERT INTO cities (name, province_id)
SELECT 'Edirne', 22
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Edirne' AND province_id=22);

INSERT INTO cities (name, province_id)
SELECT 'Elazığ', 23
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Elazığ' AND province_id=23);

INSERT INTO cities (name, province_id)
SELECT 'Erzincan', 24
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Erzincan' AND province_id=24);

INSERT INTO cities (name, province_id)
SELECT 'Erzurum', 25
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Erzurum' AND province_id=25);

INSERT INTO cities (name, province_id)
SELECT 'Eskişehir', 26
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Eskişehir' AND province_id=26);

INSERT INTO cities (name, province_id)
SELECT 'Gaziantep', 27
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Gaziantep' AND province_id=27);

INSERT INTO cities (name, province_id)
SELECT 'Giresun', 28
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Giresun' AND province_id=28);

INSERT INTO cities (name, province_id)
SELECT 'Gümüşhane', 29
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Gümüşhane' AND province_id=29);

INSERT INTO cities (name, province_id)
SELECT 'Hakkari', 30
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Hakkari' AND province_id=30);

INSERT INTO cities (name, province_id)
SELECT 'Hatay', 31
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Hatay' AND province_id=31);

INSERT INTO cities (name, province_id)
SELECT 'Isparta', 32
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Isparta' AND province_id=32);

INSERT INTO cities (name, province_id)
SELECT 'Mersin', 33
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Mersin' AND province_id=33);

INSERT INTO cities (name, province_id)
SELECT 'İstanbul', 34
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='İstanbul' AND province_id=34);

INSERT INTO cities (name, province_id)
SELECT 'İzmir', 35
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='İzmir' AND province_id=35);

INSERT INTO cities (name, province_id)
SELECT 'Kars', 36
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Kars' AND province_id=36);

INSERT INTO cities (name, province_id)
SELECT 'Kastamonu', 37
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Kastamonu' AND province_id=37);

INSERT INTO cities (name, province_id)
SELECT 'Kayseri', 38
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Kayseri' AND province_id=38);

INSERT INTO cities (name, province_id)
SELECT 'Kırklareli', 39
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Kırklareli' AND province_id=39);

INSERT INTO cities (name, province_id)
SELECT 'Kırşehir', 40
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Kırşehir' AND province_id=40);

INSERT INTO cities (name, province_id)
SELECT 'Kocaeli', 41
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Kocaeli' AND province_id=41);

INSERT INTO cities (name, province_id)
SELECT 'Konya', 42
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Konya' AND province_id=42);

INSERT INTO cities (name, province_id)
SELECT 'Kütahya', 43
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Kütahya' AND province_id=43);

INSERT INTO cities (name, province_id)
SELECT 'Malatya', 44
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Malatya' AND province_id=44);

INSERT INTO cities (name, province_id)
SELECT 'Manisa', 45
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Manisa' AND province_id=45);

INSERT INTO cities (name, province_id)
SELECT 'Kahramanmaraş', 46
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Kahramanmaraş' AND province_id=46);

INSERT INTO cities (name, province_id)
SELECT 'Mardin', 47
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Mardin' AND province_id=47);

INSERT INTO cities (name, province_id)
SELECT 'Muğla', 48
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Muğla' AND province_id=48);

INSERT INTO cities (name, province_id)
SELECT 'Muş', 49
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Muş' AND province_id=49);

INSERT INTO cities (name, province_id)
SELECT 'Nevşehir', 50
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Nevşehir' AND province_id=50);

INSERT INTO cities (name, province_id)
SELECT 'Niğde', 51
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Niğde' AND province_id=51);

INSERT INTO cities (name, province_id)
SELECT 'Ordu', 52
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Ordu' AND province_id=52);

INSERT INTO cities (name, province_id)
SELECT 'Rize', 53
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Rize' AND province_id=53);

INSERT INTO cities (name, province_id)
SELECT 'Sakarya', 54
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Sakarya' AND province_id=54);

INSERT INTO cities (name, province_id)
SELECT 'Samsun', 55
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Samsun' AND province_id=55);

INSERT INTO cities (name, province_id)
SELECT 'Siirt', 56
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Siirt' AND province_id=56);

INSERT INTO cities (name, province_id)
SELECT 'Sinop', 57
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Sinop' AND province_id=57);

INSERT INTO cities (name, province_id)
SELECT 'Sivas', 58
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Sivas' AND province_id=58);

INSERT INTO cities (name, province_id)
SELECT 'Tekirdağ', 59
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Tekirdağ' AND province_id=59);

INSERT INTO cities (name, province_id)
SELECT 'Tokat', 60
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Tokat' AND province_id=60);

INSERT INTO cities (name, province_id)
SELECT 'Trabzon', 61
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Trabzon' AND province_id=61);

INSERT INTO cities (name, province_id)
SELECT 'Tunceli', 62
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Tunceli' AND province_id=62);

INSERT INTO cities (name, province_id)
SELECT 'Şanlıurfa', 63
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Şanlıurfa' AND province_id=63);

INSERT INTO cities (name, province_id)
SELECT 'Uşak', 64
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Uşak' AND province_id=64);

INSERT INTO cities (name, province_id)
SELECT 'Van', 65
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Van' AND province_id=65);

INSERT INTO cities (name, province_id)
SELECT 'Yozgat', 66
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Yozgat' AND province_id=66);

INSERT INTO cities (name, province_id)
SELECT 'Zonguldak', 67
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Zonguldak' AND province_id=67);

INSERT INTO cities (name, province_id)
SELECT 'Aksaray', 68
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Aksaray' AND province_id=68);

INSERT INTO cities (name, province_id)
SELECT 'Bayburt', 69
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Bayburt' AND province_id=69);

INSERT INTO cities (name, province_id)
SELECT 'Karaman', 70
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Karaman' AND province_id=70);

INSERT INTO cities (name, province_id)
SELECT 'Kırıkkale', 71
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Kırıkkale' AND province_id=71);

INSERT INTO cities (name, province_id)
SELECT 'Batman', 72
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Batman' AND province_id=72);

INSERT INTO cities (name, province_id)
SELECT 'Şırnak', 73
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Şırnak' AND province_id=73);

INSERT INTO cities (name, province_id)
SELECT 'Bartın', 74
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Bartın' AND province_id=74);

INSERT INTO cities (name, province_id)
SELECT 'Ardahan', 75
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Ardahan' AND province_id=75);

INSERT INTO cities (name, province_id)
SELECT 'Iğdır', 76
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Iğdır' AND province_id=76);

INSERT INTO cities (name, province_id)
SELECT 'Yalova', 77
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Yalova' AND province_id=77);

INSERT INTO cities (name, province_id)
SELECT 'Karabük', 78
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Karabük' AND province_id=78);

INSERT INTO cities (name, province_id)
SELECT 'Kilis', 79
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Kilis' AND province_id=79);

INSERT INTO cities (name, province_id)
SELECT 'Osmaniye', 80
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Osmaniye' AND province_id=80);

INSERT INTO cities (name, province_id)
SELECT 'Düzce', 81
    WHERE NOT EXISTS (SELECT 1 FROM cities WHERE name='Düzce' AND province_id=81);
-- cities