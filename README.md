HC-06 ile andorid haberleştirme işlemi gerçekleştirildi.

app üzerinde gönderilen veriler  
0 - 1000 arası gönderildiğinde bir kanal 
1000 - 2000 arası değer geldiğinde diğer kanal olacak şekilde istediğimiz kadar kalan kontrol edebiliyoruz. 

Örneğin 1000 - 2000 arası değer  gönderilsin ve gelen değer 1450 olsun. Burada gelen değeri kullanmak için 1450 - 1000 = 450 şeklinde kullanım senaryosu beliliyoruz. 
Bu sayede hem istediğimiz kanalı kontrol etmiş oluyoruz hemde pwm değerini bu değer oranında yükselitip alçaltabiliyoruz. Bu saydede gerçekten hatasız real'time haberleşme 
sağlamış oluyoruz.
