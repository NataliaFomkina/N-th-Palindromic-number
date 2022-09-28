import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Math.pow;

public class Main {
    public static void main(String[] args) {
        System.out.println("Введите n: ");
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        System.out.println(n + "-ое по счету число-палиндром: " + findPalindrom(n));
    }

    /**
     * Метод, находящий n-ое число-палиндром в последовательности всех чисел-палиндромов, расположенных по возрастанию.
     * @param n - число n
     */
    public static int findPalindrom(int n){
        List<Integer> palindromArray = new ArrayList<>();

        //Список palindromArray хранит количество палиндромов разной длины, причем индекс списка показывает сколько цифр в палиндроме
        //например palindromArray.get(3)=100 означает, что имеется 100 палиндромов длиной в 3 цифры.
        //Чтобы получить длину "половины" палиндрома, разделим количество цифр в палиндроме на 2. Java при целочисленном делении округляет в меньшую сторону,
        //поэтому такое деление будет одинаково работать и для нечетного количества цифр, и для четного.
        //Если количество цифр в палиндроме нечетное, то нужно учитывать, что есть "центральная" цифра, которая увеличивает количество
        //палиндромов в 10 раз, т.к может принимать значения [0...9]
        //Старшая цифра любой левой "половины" палиндрома может быть [1...9] - то есть всего 9 вариантов
        //Остальные цифры левой "половины" панидрома могут быть [0...9] - то есть всего 10 вариантов, поэтому
        //Кол-во палиндромов четной длины расчитывается по формуле 9 * pow(10, (длина_половины_палиндрома - 1))
        //Кол-во палиндромов нечетной длины (кроме длины 1) расчитывается по формуле 9 * pow(10, (длина_половины_палиндрома))

        //инициализируем первый элемент списка нулем, т.к он не используется (не существует палиндромов нулевой длины)
        //инициализируем второй элемент списка числом 10, т.к всего 10 палиндромов длины 1: 0, 1, 2, 3, 4, 5, 6, 7, 8, 9.
        //следующие элементы списка инициализируем согласно вышеприведенным формулам.
        palindromArray.add(0);
        palindromArray.add(10);
        //
        int i = palindromArray.get(1);//переменная-счетчик, суммирующая кол-во палиндромов. Инициализируем количеством палиндромов длины 1 (т.е 10)
        int j = 1;//переменная, хранящая индекс списка (показывает длину палиндрома). Инициализируем 1, т.к логика цикла while начинает выполняться с длины 2.
        int result;//искомый палиндром
        if (n <= 10){
            result = n - 1; //первые 10 чисел длины 1 являются палиндромами (включая 0).
        }else {
            while (i < n) {
                j++;//увеличили длину палиндрома
                int numb; //количество палиндромов длины j
                if(j % 2 == 0){ //если длина палиндрома четная
                    numb = 9 * (int)pow(10, j/2 - 1);
                }else{//если длина палиндрома нечетная
                    numb = 9 * (int)pow(10, j/2);
                }
                i += numb;// увеличиваем счетчик количества палиндромов
                palindromArray.add(numb);//добавляем количество палиндромов в список
            }
            //находим, какой по счету палиндром нам нужен на соответствующем уровне j. Для этого находим суммарное количество всех
            //палиндромов до уровня j-1, и из n вычитаем это количество.
            int num_j_1 = 0;//суммарное количество всех палиндромов до уровня j-1
            for (int index = 0; index <= j-1; index++){
                num_j_1 += palindromArray.get(index);
            }
            int num_j = n - num_j_1;//искомый номер палиндрома на соответствующем уровне j
            String palindrom_string;//палиндром в виде строки
            if (j % 2 == 0){ //если длина палиндрома четная, то
                int left_half_of_palindrom = (int)pow(10, j/2 -1) + num_j - 1;//левая половина палиндрома. Последним действием отнимаем 1, т.к последняя цифра половины палиндрома начинается с нуля, а num_j (номер палиндрома на соответствующем уровне) начинается с 1.
                palindrom_string = left_half_of_palindrom + reverseString(Integer.toString(left_half_of_palindrom));//искомый полиндром в виде строки.
            }else{//если длина палиндрома нечетная, то
                int left_half_of_palindrom_plus_middle = (int)pow(10, j/2) + num_j - 1;//левая половина палиндрома плюс центральная цифра. Так же последним действием отнимаем 1, т.к центральная цифра начинается с нуля
                palindrom_string = left_half_of_palindrom_plus_middle + reverseString(Integer.toString(left_half_of_palindrom_plus_middle/10));//искомый палиндром в виде строки
            }
            result = Integer.parseInt(palindrom_string);//искомый палиндром в виде числа
        }
        return result;
    }
    public static String reverseString(String str){//вспомогательный метод для переворачивания строки
        return new StringBuilder(str).reverse().toString();
    }
}
