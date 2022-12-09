// Encoding cp1251 because my Windows console show ??? for russian chars

// Задано уравнение вида q + w = e, q, w, e >= 0. Некоторые цифры могут быть заменены
// знаком вопроса, например 2? + ?5 = 69. Требуется восстановить выражение до верного
// равенства. Предложить хотя бы одно решение или сообщить, что его нет.

// Чубченко Светлана

import java.util.Scanner;

public class main {
    // объявляем глобальные переменные для простоты
    static int fl = 1, len, a_len, b_len, c_len;
    static char[] str = new char[201]; // исходная формула
    static char[] a = new char[201]; // первое число
    static char[] b = new char[201]; // второе число
    static char[] c = new char[201]; // результат

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int i = 0;
        System.out.print("Введите уравнение с вопросами (например 2? + ?5 = 69): ");
        String s = scanner.nextLine();
        // удалим пробелы в уравнении
        s = s.replaceAll(" ", "");
        // переведем строку в символьный массив для простоты парсинга
        str = s.toCharArray();

        len = s.length();
        // ищем + и задаем расположение числа 1
        while (str[i] != '+')
            i++;
        a_len = i;

        // ищем = и задаем расположение числа 2 и результата
        while (str[i] != '=')
            i++;
        b_len = i - 1 - a_len;

        c_len = len - 2 - a_len - b_len;

        // инициализируем байтовые массивы нулями
        for (i = 0; i < 200; i++) {
            a[i] = b[i] = c[i] = '0';
        }

        // выделяем сами числа 1, 2 и результат
        for (i = a_len - 1; i >= 0; i--)
            a[a_len - 1 - i] = str[i];
        for (i = b_len + a_len; i > a_len; i--)
            b[b_len + a_len - i] = str[i];
        for (i = len - 1; i > b_len + a_len + 1; i--)
            c[len - 1 - i] = str[i];

        // ищем то, что под знаками вопроса
        rec(0, 0);

        // выводим результат
        if (fl == 1)
            System.out.print("Ошибка");
        else {
            for (i = a_len - 1; i >= 0; i--)
                System.out.printf("%c", a[i]);
            System.out.print("+");
            for (i = b_len - 1; i >= 0; i--)
                System.out.printf("%c", b[i]);
            System.out.print("=");
            for (i = c_len - 1; i >= 0; i--)
                System.out.printf("%c", c[i]);
        }
    }

    public static void rec(int a1, int b1) {
        if (fl == 0)
            return;
        if (a1 >= a_len && a1 >= b_len && a1 >= c_len) {
            if (b1 == 0)
                fl = 0;
            return;
        }
        if (a[a1] == '?' && b[a1] == '?' && c[a1] == '?') {
            a[a1] = '0';
            b[a1] = '0';
            if (b1 == 0)
                c[a1] = '0';
            else
                c[a1] = '1';
            rec(a1 + 1, 0);
            if (fl == 1) {
                a[a1] = '5';
                b[a1] = '5';
                if (b1 == 0)
                    c[a1] = '0';
                else
                    c[a1] = '1';
                rec(a1 + 1, 1);
            }
        }
        if (a[a1] == '?' && b[a1] == '?' && c[a1] != '?') {
            if (c[a1] == '9') {
                if (b1 == 1) {
                    a[a1] = '5';
                    b[a1] = '3';
                    rec(a1 + 1, 0);
                    if (fl == 1) {
                        a[a1] = '9';
                        b[a1] = '9';
                        rec(a1 + 1, 1);
                    }
                } else {
                    a[a1] = '5';
                    b[a1] = '3';
                    rec(a1 + 1, 0);
                }
            } else {
                if (c[a1] == '0' && b1 == 1) {
                    a[a1] = '8';
                    b[a1] = '1';
                    rec(a1 + 1, 1);
                } else {
                    a[a1] = '0';
                    if (b1 == 0)
                        b[a1] = c[a1];
                    else
                        b[a1] = (char)(c[a1] + 1);
                    rec(a1 + 1, 0);
                    if (fl == 1) {
                        a[a1] = '9';
                        if (b1 == 0)
                            b[a1] = (char)(c[a1] + 1);
                        else
                            b[a1] = c[a1];
                        rec(a1 + 1, 1);
                    }
                }
            }
        }
        if (a[a1] != '?' && b[a1] == '?' && c[a1] != '?') {
            if (a[a1] > c[a1]) {
                if (b1 == 1)
                    b[a1] = (char)('0' + 9 + (c[a1] - a[a1]));
                else
                    b[a1] = (char)('0' + 10 + (c[a1] - a[a1]));
                rec(a1 + 1, 1);
            }
            if (a[a1] < c[a1]) {
                if (b1 == 1)
                    b[a1] = (char)('0' + (c[a1] - a[a1]) - 1);
                else
                    b[a1] = (char)('0' + (c[a1] - a[a1]));
                rec(a1 + 1, 0);
            }
            if (a[a1] == c[a1]) {
                if (b1 == 0) {
                    b[a1] = '0';
                    rec(a1 + 1, 0);
                } else {
                    b[a1] = '9';
                    rec(a1 + 1, 1);
                }
            }
        }
        if (a[a1] == '?' && b[a1] != '?' && c[a1] != '?') {
            if (b[a1] > c[a1]) {
                if (b1 == 1)
                    a[a1] = (char)('0' + 9 + (c[a1] - b[a1]));
                else
                    a[a1] = (char)('0' + 10 + (c[a1] - b[a1]));
                rec(a1 + 1, 1);
            }
            if (b[a1] < c[a1]) {
                if (b1 == 1)
                    a[a1] = (char)('0' + (c[a1] - b[a1]) - 1);
                else
                    a[a1] = (char)('0' + (c[a1] - b[a1]));
                rec(a1 + 1, 0);
            }
            if (b[a1] == c[a1]) {
                if (b1 == 0) {
                    a[a1] = '0';
                    rec(a1 + 1, 0);
                } else {
                    a[a1] = '9';
                    rec(a1 + 1, 1);
                }
            }
        }
        if (a[a1] != '?' && b[a1] != '?' && c[a1] != '?') {
            if (b1 == 1) {
                if (((int) (a[a1] - '0' + b[a1] - '0') + 1) % 10 == (int) (c[a1] - '0')) {
                    if ((int) (a[a1] - '0' + b[a1] - '0') + 1 > 9)
                        rec(a1 + 1, 1);
                    else
                        rec(a1 + 1, 0);
                }
            } else {
                if ((int) (a[a1] - '0' + b[a1] - '0') % 10 == (int) (c[a1] - '0')) {
                    if ((int) (a[a1] - '0' + b[a1] - '0') > 9)
                        rec(a1 + 1, 1);
                    else
                        rec(a1 + 1, 0);
                }
            }
        }
        if (a[a1] != '?' && b[a1] == '?' && c[a1] == '?') {
            if (a[a1] == '0') {
                if (b1 == 0) {
                    b[a1] = '0';
                    c[a1] = '0';
                    rec(a1 + 1, 0);
                } else {
                    b[a1] = '0';
                    c[a1] = '1';
                    rec(a1 + 1, 0);
                    if (fl == 1) {
                        b[a1] = '9';
                        c[a1] = '0';
                        rec(a1 + 1, 1);
                    }
                }
            } else {
                if (a[a1] == '9') {
                    if (b1 == 1) {
                        b[a1] = '0';
                        c[a1] = '0';
                        rec(a1 + 1, 1);
                    } else {
                        b[a1] = '1';
                        c[a1] = '0';
                        rec(a1 + 1, 1);
                        if (fl == 1) {
                            b[a1] = '0';
                            c[a1] = '0';
                            rec(a1 + 1, 0);
                        }
                    }
                } else {
                    b[a1] = '0';
                    if (b1 == 0)
                        c[a1] = a[a1];
                    else
                        c[a1] = (char)(a[a1] + 1);
                    rec(a1 + 1, 0);
                    if (fl == 1) {
                        b[a1] = '9';
                        if (b1 == 0)
                            c[a1] = (char)(a[a1] - 1);
                        else
                            c[a1] = a[a1];
                        rec(a1 + 1, 1);
                    }
                }
            }
        }
        if (a[a1] == '?' && b[a1] != '?' && c[a1] == '?') {
            if (b[a1] == '0') {
                if (b1 == 0) {
                    a[a1] = '0';
                    c[a1] = '0';
                    rec(a1 + 1, 0);
                } else {
                    a[a1] = '0';
                    c[a1] = '1';
                    rec(a1 + 1, 0);
                    if (fl == 1) {
                        a[a1] = '9';
                        c[a1] = '0';
                        rec(a1 + 1, 1);
                    }
                }
            } else {
                if (b[a1] == '9') {
                    if (b1 == 1) {
                        a[a1] = '0';
                        c[a1] = '0';
                        rec(a1 + 1, 1);
                    } else {
                        a[a1] = '1';
                        c[a1] = '0';
                        rec(a1 + 1, 1);
                        if (fl == 1) {
                            a[a1] = '0';
                            c[a1] = '0';
                            rec(a1 + 1, 0);
                        }
                    }
                } else {
                    a[a1] = '0';
                    if (b1 == 0)
                        c[a1] = b[a1];
                    else
                        c[a1] = (char)(b[a1] + 1);
                    rec(a1 + 1, 0);
                    if (fl == 1) {
                        a[a1] = '9';
                        if (b1 == 0)
                            c[a1] = (char)(b[a1] - 1);
                        else
                            c[a1] = b[a1];
                        rec(a1 + 1, 1);
                    }
                }
            }
        }
        if (a[a1] != '?' && b[a1] != '?' && c[a1] == '?') {
            if ((int) (a[a1] - '0' + b[a1] - '0') + b1 > 9) {
                c[a1] = (char)('0' + ((int) (a[a1] - '0' + b[a1] - '0') + b1) % 10);
                rec(a1 + 1, 1);
            } else {
                c[a1] = (char)('0' + (int) (a[a1] - '0' + b[a1] - '0') + b1);
                rec(a1 + 1, 0);
            }
        }

    }

}