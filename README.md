# icf_spribgboot

## Feladat
A feladat célja egy egyszerű, beléptetéssel és jogosultságkezeléssel ellátott weboldal létrehozása.

A felhasználó a neve és jelszava megadásával tud belépni az oldalra. A felhasználó adatait
adatbázisban tároljuk. Amennyiben a beléptetés során a felhasználó háromszor rossz jelszót ad meg,
a negyedik alkalommal a név és jelszó mező mellett egy CAPTCHA kép feladványra is válaszolnia
kell.

A belépett felhasználó egy központi adminisztrációs oldalra kerül, melynek menüjében a
felhasználó csak azokat az aloldalakat láthatja, melyekhez jogosultsággal rendelkezik.
A menüben a kilépés lehetőségre kattintva a felhasználó ki tud jelentkezni a rendszerből.

## Szerepkörök
Minden felhasználó rendelkezik egy vagy több szerepkörrel. Amennyiben egy felhasználó több
szerepkörrel is rendelkezik, a jogosultságok unióját kell képezni, azaz a felhasználó megkapja
valamennyi hozzárendelt szerepkör jogosultságát.
A rendszer három szerepkört definiál:
* Adminisztrátor -- Látja az adminisztrátorok aloldalát, és az összes többi aloldalt is.
* Tartalomszerkesztő -- Látja a tartalomszerkesztők aloldalát.
* Bejelentkezett felhasználó -- Látja a bejelentkezett felhasználók aloldalát

## Felhasználók
A rendszerben az alábbi felhasználókat kell létrehozni:
| Felhasználónév | Szerepkörök |
| :--- | :--- |
| Admin | Adminisztrátor |
| User 1 | Tartalomszerkesztő + Bejelentkezett felhasználó |
| User 2 | Tartalomszerkesztő |
| User 3 | Bejelentkezett felhasználó |

A feladatnak nem volt része de megvalósítottam a security modul tesztelését is.<br>
Unit tesztet kerestem ([forrás](https://betterprogramming.pub/spring-security-basic-login-form-7c8f6e6e9f56)), de a fejlesztés során kiderült, hogy ez valójában integrációs teszt. Ez látszik is abból, hogy a tesztek futása előtt a rendszer elindul.
