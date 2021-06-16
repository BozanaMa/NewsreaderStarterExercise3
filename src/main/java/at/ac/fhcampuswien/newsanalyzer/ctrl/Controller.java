package at.ac.fhcampuswien.newsanalyzer.ctrl;

import at.ac.fhcampuswien.newsapi.NewsApi;
import at.ac.fhcampuswien.newsapi.NewsApiBuilder;
import at.ac.fhcampuswien.newsapi.beans.Article;
import at.ac.fhcampuswien.newsapi.beans.NewsResponse;
import at.ac.fhcampuswien.newsapi.enums.Category;
import at.ac.fhcampuswien.newsapi.enums.Country;
import at.ac.fhcampuswien.newsapi.enums.Endpoint;
import java.util.*;
import java.util.stream.Collectors;

public class Controller {


	//TODO add your api key
	public static final String APIKEY = "6ac54e82b3a84ec8a8bb1657b7d14bb4";

	public void process(String query, Category category) {
		System.out.println("Start Process");

		//TODO load the news based on the parameters
		NewsApi newsApi;
		if (category != null) {
			newsApi = new NewsApiBuilder()
					.setApiKey(APIKEY)
					.setSourceCountry(Country.at)
					.setQ(query)
					.setEndPoint(Endpoint.TOPHEADLINES)
					.setSourceCategory(category)
					.createNewsApi();
		} else {
			newsApi = new NewsApiBuilder()
					.setApiKey(APIKEY)
					.setSourceCountry(Country.at)
					.setQ(query)
					.setEndPoint(Endpoint.TOPHEADLINES)
					.createNewsApi();
		}

		//TODO implement Error handling
		NewsResponse newsResponse = null;
		try {
			newsResponse = newsApi.getNews();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		//TODO implement methods for analysis
		if (newsResponse != null) {
			List<Article> articles = newsResponse.getArticles();

			if (articles.isEmpty()) {
				System.out.println("No Articles to analyze!");
			} else {

				System.out.println("How many Articles: " + (long) articles.size());

				String provider = articles.stream()
						.collect(Collectors.groupingBy(article -> article.getSource().getName(), Collectors.counting()))
						.entrySet().stream().max(Comparator.comparingInt(a -> Math.toIntExact(a.getValue()))).get().getKey();

				if (provider != null)
					System.out.println("Most published Articles: " + provider);


				String author = articles.stream()
						.filter(article -> Objects.nonNull(article.getAuthor()))
						.min(Comparator.comparingInt(article -> article.getAuthor().length()))
						.get().getAuthor();

				if ( author != null ){
					System.out.println("Shortest Author: " + author);
				}

				List<String> titleList = articles.stream().map(Article::getTitle)
						.filter(Objects::nonNull)
						.sorted()
						.sorted(((o1, o2) -> o2.length() - o1.length()))
						.collect(Collectors.toList());

				System.out.print("Longest Titel: ");

				titleList.forEach(System.out::println);
			}
		}
		System.out.println("Process Finished");
	}
	public Object getData() {

		return null;
	}
}