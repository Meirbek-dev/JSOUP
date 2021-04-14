package kz.bmk.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

// СОЗДАТЕЛЬ КОТИРОВОК
public class RatesReader {

    private static final String BASE_URL = "https://www.imdb.com/chart/toptv/?ref_=nv_tvv_250"; // Адрес с котировками

    // Парсинг котировок из формата html web-страницы банка, при ошибке доступа возвращаем null
    public static String getRatesData() {
        StringBuilder data = new StringBuilder();
        try {
            Document doc = Jsoup.connect(BASE_URL).timeout(5000).get(); // Создание документа JSOUP из html
            data.append("IMDb Top 250:\n"); // Считываем заголовок страницы
            data.append(String.format("%12s\n", "Сериалы").trim());
            data.append("\n");
            Elements e = doc.select("div.lister"); // Ищем в документе "<div class="exchange"> с данными о валютах
            Elements tables = e.select("table.chart"); // Ищем таблицы с котировками
            Element table = tables.get(0); // Берем 1 таблицу
            Elements tbodies = table.select("tbody.lister-list"); // Ищем таблицы с котировками
            Element tbody = tbodies.get(0); // Берем 1 таблицу

            // Цикл по строкам таблицы
            for (Element row : tbody.select("tr")) {
                // Цикл по столбцам таблицы
                Elements tdTitle = row.select("td");
                Element td = tdTitle.get(1); // Берем 1 таблицу
                data.append(String.format("%12s ", td.text()));
               /* for (Element col : row.select("td")) { //
                    data.append(String.format("%12s ", col.text())); // Считываем данные с ячейки таблицы
                }*/
                data.append("\n"); // Добавляем переход на следующую строку;
            }
        } catch (Exception ignored) {
            return null; // При ошибке доступа возвращаем null
        }
        return data.toString().trim(); // Возвращаем результат
    }

}