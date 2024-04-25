sbmlシミュレーターの獲得方法
```
git clone git@github.com:ImadaKakeru/sbmlSimulator.git
```

mapk生化学ネットワークにおける各分子の濃度を数値積分するプログラムです。
数値積分の手法はオイラー法、ルンゲクッタ法の２種類で実装しています。


このプログラムは、eclipse上で動作が確認されています。
eclipse上で起動してください。

#オイラー法
$\frac{dx}{dt} = f(x,t)$
$x^{n+1} = x^n + \left( \frac{dx}{dt} \right)^n \Delta t$

#ルンゲ・クッタ法
$x^{n+1} = x^n + \frac{\Delta t}{6}k_1 + \frac{\Delta t}{3}k_2 + \frac{\Delta t}{3}k_3 + \frac{\Delta t}{6}k_4$
k_1 = f(x_n)
k_2 = f(x_n + \frac{\Delta t}{2} k_1)
k_3 = f(x_n + \frac{\Delta t}{2} k_2)
k_4 = f(x_n + \Delta t k_3)
