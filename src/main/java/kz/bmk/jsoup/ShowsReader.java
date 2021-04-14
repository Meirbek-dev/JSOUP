package kz.bmk.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ShowsReader {

    private static final String BASE_URL = "https://www.imdb.com/chart/toptv/?ref_=nv_tvv_250";

    // Парсинг котировок из формата html web-страницы банка, при ошибке доступа возвращаем null
    public static String getShowsData() {
        StringBuilder data = new StringBuilder();
        try {
            Document doc = Jsoup.connect(BASE_URL).timeout(5000).get(); // Создание документа JSOUP из html
            data.append("IMDb Top 250:\n");
            data.append(String.format("%12s\n", "Сериалы").trim());
            data.append("\n");
            Elements e = doc.select("div.lister"); // Ищу в документе "<div class="lister"> с данными о валютах
            Elements tables = e.select("table.chart"); // Ищу таблицу, которая принадлежит классу "chart"
            Element table = tables.get(0); // Выибраю 1 таблицу
            Elements tbodies = table.select("tbody.lister-list"); // Ищу тело таблицы, которая принадлежит классу "lister-list"
            Element tbody = tbodies.get(0); // Выибраю 1 тело таблицы
            for (Element row : tbody.select("tr")) {
                // Цикл по столбцам таблицы
                Elements td = row.select("td");
                Element tdTitle = td.get(1); // Выбираю 2 столбец с названием шоу
                data.append(tdTitle.text()).append("\n");
            }
        } catch (Exception ignored) {
            return null; // При ошибке доступа возвращаем null
        }
        return data.toString().trim(); // Возвращаем результат
    }

}